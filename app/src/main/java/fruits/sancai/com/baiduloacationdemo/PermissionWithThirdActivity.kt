package fruits.sancai.com.baiduloacationdemo

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import kotlinx.android.synthetic.main.activity_permission_with_third.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class PermissionWithThirdActivity : AppCompatActivity(), LocationListener, EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    var permission: Array<String> = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.LOCATION_HARDWARE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    var mPermission = ArrayList<String>()

    private val mRequestCode = 100//权限请求码
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_with_third)
        mLocationClient = LocationClient(applicationContext)
        mLocationClient?.registerLocationListener(myListener)
        val option = LocationClientOption()
        option.setIsNeedAddress(true)
        mLocationClient?.locOption = option
        locationAndContactsTask()

    }

    override fun showLocationData(location: BDLocation) {

        val addr = location.addrStr    //获取详细地址信息
        val country = location.country    //获取国家
        val province = location.province    //获取省份
        val city = location.city    //获取城市
        val district = location.district    //获取区县
        val street = location.street    //获取街道信息

        kt_textview.text=addr
//
//        Log.i("xiangyao", country + province + city + district + street + "______" + addr)

    }

    var mLocationClient: LocationClient? = null
    private val myListener = MyLocationListener(this)
    private val LOCATION =
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )


    companion object {
        private const val RC_LOCATION_CONTACTS_PERM = 124
        private const val RC_CAMERA_PERM = 123
        private const val TAG = "xiangyao"
    }


    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    fun locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            mLocationClient?.start()
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location_contacts),
                RC_LOCATION_CONTACTS_PERM,
                *LOCATION
            )
        }
    }

    private fun hasLocationAndContactsPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this, *LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
        mLocationClient?.start()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (hasLocationAndContactsPermissions()) {
                mLocationClient?.start()
            } else {
                finish()
            }
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:$requestCode")
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:$requestCode")
    }
}
