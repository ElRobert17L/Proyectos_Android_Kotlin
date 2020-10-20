package mx.com.appcheckins

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Network(var activity: AppCompatActivity) {

    fun hayRed():Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val estaConectado:Boolean? = networkInfo?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        return networkInfo != null && estaConectado == true
    }

    fun httpRequest(context: Context, url: String, httpResponse: HttpResponse) {

        if (hayRed()){

            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.GET, url, {

                    response ->

                httpResponse.httpResponseSuccess(response)

            }, {
                    error ->

                Log.d("HTTP_REQUEST", error.message)

                Mensaje.mensajeError(context, Errores.HTTP_ERROR)

            })

            queue.add(solicitud)

        } else {
            Mensaje.mensajeError(context, Errores.NO_HAY_RED)
        }
    }
}