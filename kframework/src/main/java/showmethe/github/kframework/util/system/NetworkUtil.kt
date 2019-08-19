package showmethe.github.kframework.util.system

import android.content.Context
import android.net.ConnectivityManager

/**
 * showmethe.github.kframework.util
 *
 * 2019/1/11
 **/


/**
 * 网络连接状态
 *
 * @param context
 * @return
 */
fun checkConnection(context: Context): Boolean {
   return try {
      val cm = context
         .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val networkInfo = cm.activeNetworkInfo
      !(networkInfo == null || !networkInfo.isConnected)
   }catch (e:Exception){
      false
   }
}