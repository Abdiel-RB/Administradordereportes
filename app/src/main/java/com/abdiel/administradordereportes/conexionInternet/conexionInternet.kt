package com.abdiel.administradordereportes.conexionInternet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class conexionInternet(context: Context) {
    private val context = context

    fun isInternetConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}