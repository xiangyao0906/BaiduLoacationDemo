package fruits.sancai.com.baiduloacationdemo

import android.content.Context
import android.content.pm.PackageManager


object PermissionUtils {

    fun checkAllPermissionAllowed(context: Context, perName: Array<String>, pkName: String): Boolean {

        var packageManager = context.packageManager


        for ((_, v) in perName.withIndex()) {
            val allPersionAllowed = checkPermission(context, v, pkName)
            if (!allPersionAllowed) {
                return false
            }
        }

        return true

    }


    fun checkPermission(context: Context, perName: String, pkName: String): Boolean {

        val packageManager = context.packageManager

        return PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(perName, pkName)

    }


}