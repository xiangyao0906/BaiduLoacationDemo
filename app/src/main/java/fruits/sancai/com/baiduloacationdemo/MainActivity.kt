package fruits.sancai.com.baiduloacationdemo

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LocationListener {


    var permission: Array<String> = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var mPermission = ArrayList<String>()

    private val mRequestCode = 100//权限请求码


    fun initPermission() {
        mPermission.clear()
        for ((index, value) in arrayOf(permission).withIndex()) {

            if (ContextCompat.checkSelfPermission(this, value[index]) != PackageManager.PERMISSION_GRANTED) {

                mPermission.add(value[index])
            }
        }
        //申请权限
        if (mPermission.size > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permission, mRequestCode)
        } else {
            mLocationClient?.start()
        }

    }


    override fun showLocationData(location: BDLocation) {

        val addr = location.addrStr    //获取详细地址信息
        val country = location.country    //获取国家
        val province = location.province    //获取省份
        val city = location.city    //获取城市
        val district = location.district    //获取区县
        val street = location.street    //获取街道信息


        loacationMesaage.text = addr

        Log.i("xiangyao", country + province + city + district + street + "______" + addr)

    }

    var mLocationClient: LocationClient? = null
    private val myListener = MyLocationListener(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mLocationClient = LocationClient(applicationContext)
        mLocationClient?.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.setIsNeedAddress(true)
        mLocationClient?.locOption = option

        initPermission()

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (mRequestCode == requestCode) {

            var requestPerminssionCount = 0

            for ((i, _) in grantResults.withIndex()) {

                if (grantResults[i] == 0) {
                    requestPerminssionCount++
                }
            }

            if (requestPerminssionCount == grantResults.size) {
                mLocationClient?.start()
            } else {
                showPermissionDialog()
            }

        }

    }


    var mPermissionDialog: AlertDialog? = null
    var mPackageName = "fruits.sancai.com.baiduloacationdemo"
    private fun showPermissionDialog() {

        if (mPermissionDialog == null) {
            mPermissionDialog = AlertDialog.Builder(this)
                .setMessage("已禁用权限，请手动授予")
                .setPositiveButton("设置") { p0, p1 ->

                    cancelPermissionDialog()

                    val packageUri = Uri.parse("package:$mPackageName")

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageUri)

                    startActivityForResult(intent, mRequestCode)


                }.setNegativeButton("取消") { p0, p1 ->

                    cancelPermissionDialog()

                    finish()

                }
                .create()
        }
        mPermissionDialog?.show()
    }

    fun cancelPermissionDialog() {
        mPermissionDialog?.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == mRequestCode) {

            for ((index, value) in arrayOf(permission).withIndex()) {

                if (ContextCompat.checkSelfPermission(this, value[index]) != PackageManager.PERMISSION_GRANTED) {
                    finish()
                }
            }

        }
    }
}
