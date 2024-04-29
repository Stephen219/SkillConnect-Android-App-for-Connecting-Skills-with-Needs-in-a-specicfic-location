package com.example.mob_dev_portfolio



import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object checkNetwork{
    /**
     * Checks if the device has an active internet connection.
     *
     * This function uses the ConnectivityManager to determine if there's
     * a valid network connection on the device. It checks for various
     * network transport types such as Wi-Fi, cellular, Ethernet, and Bluetooth.
     *
     * @param context The application context.
     * @return boolean of true if am connected to the internet and false otherwise.
     */
    // from https://stackoverflow.com/questions/57284582/networkinfo-has-been-deprecated-by-api-29

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false


            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            // ideally we should check for the following as well
            // networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            // networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            // networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            //but i just need to check if the users network can connect to the internet
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }
    }

    fun recheckNetworkAfterDelay(context: Context, delayMillis: Long = 4000) {
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            if (!checkNetwork.isNetworkAvailable(context)) {
                Toast.makeText(context, "Still no internet connection. Please check your network.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Internet connection restored.", Toast.LENGTH_SHORT).show()
            }
        }

        handler.postDelayed(runnable, delayMillis)
    }

}
