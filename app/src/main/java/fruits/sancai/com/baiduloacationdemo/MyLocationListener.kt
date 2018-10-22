package fruits.sancai.com.baiduloacationdemo

import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation

class MyLocationListener(var listener: LocationListener) : BDAbstractLocationListener() {
    override fun onReceiveLocation(p0: BDLocation) {

        listener.showLocationData(p0)
    }
}