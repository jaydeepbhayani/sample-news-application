package anetos.software.byjuszyoin.core

import android.os.AsyncTask
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

/**
 *  [InternetCheck]
 *  @author
 *  created by Jaydeep Bhayani on 30/07/2020
 */

class InternetCheck(private val onInternetChecked: (Boolean) -> Unit) :
    AsyncTask<Void, Void, Boolean>() {
    init {
        execute()
    }

    override fun doInBackground(vararg voids: Void): Boolean {
        return try {
            val sock = Socket()
            sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            sock.close()
            true
        } catch (e: IOException) {
            false
        }

    }

    override fun onPostExecute(internet: Boolean) {
        try {
            onInternetChecked(internet)
        } catch (e: Exception) {

        }
    }
}