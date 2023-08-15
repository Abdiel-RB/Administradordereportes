package com.abdiel.administradordereportes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.TileMode

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.abdiel.administradordereportes.databinding.ActivityMapsRecorridoBinding
import com.abdiel.administradordereportes.modelos.encuestas
import com.abdiel.administradordereportes.modelos.encuestasCopia
import com.abdiel.administradordereportes.modelos.reportesCopia
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.bumptech.glide.request.transition.Transition
import com.google.android.gms.maps.model.BitmapDescriptor


class MapsActivityRecorrido : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsRecorridoBinding
    private lateinit var clave:String
    private lateinit var foto: String
    private lateinit var nombre: String
    var listaDatosReporte: ArrayList<reportesCopia>? = null
    var listaDatosEncuesta: ArrayList<encuestasCopia>? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsRecorridoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        nombre = intent.getStringExtra("nombre").toString()
        foto = intent.getStringExtra("fotoEstudiante").toString()
        clave = intent.getStringExtra("mapas").toString()
        if (clave == "reporte"){
            listaDatosReporte = intent.getParcelableArrayListExtra("MyArray")
            if (listaDatosReporte != null) {
                for (reporte in listaDatosReporte!!) {
                    Log.d("MapsActivityRecorrido", "ID: ${reporte.nombreDelTecnico}, Título: ${reporte.direccion}")
                }
            }else{
                println("no hay nadaaa")
            }
        }
        if (clave == "encuesta"){
            listaDatosEncuesta = intent.getParcelableArrayListExtra("MyArray")

        }




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var cont = 0

        if (clave == "reporte"){
            var listaDatos =  listaDatosReporte
            if (listaDatos != null) {
                for (reporte in listaDatos!!) {
                    var latitud: Double = reporte.latitud.toDouble()
                    var longitud: Double = reporte.longitud.toDouble()
                    val sydney = LatLng(latitud, longitud)
                    val height = 100
                    val width = 100
                    val imageUrl = foto
                    Glide.with(this)
                        .asBitmap()
                        .load(imageUrl)
                        .apply(RequestOptions.circleCropTransform()) // Aplicar la máscara circular
                        .into(object : CustomTarget<Bitmap>(width, height) {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                // Crear el BitmapDescriptor a partir del Bitmap escalado y con máscara circular
                                val bitmapDescriptor = getRoundedBitmapDescriptor(resource)
                                cont++

                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(sydney)
                                        .title("$cont. $nombre")
                                        .snippet("${reporte.nombreDelTecnico}, ${reporte.oficio}, ${reporte.telefonoDelTecnico}")
                                        .icon(bitmapDescriptor)
                                )
                                // Mover la cámara a la posición del marcador
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13F))
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                // Manejar caso si la carga de la imagen falla o se cancela
                            }
                        })
                }

            }else{
                println("no hay nadaaa")

            }
        }

        if (clave == "encuesta"){

            if (listaDatosEncuesta != null) {
                //showToast("algo bien ${listaDatosEncuesta!!.size}")
                for (encuesta in listaDatosEncuesta!!) {
                    showToast("algo bien ${encuesta.latitud}")
                    /*   var latitud: Double = encuesta.latitud.toDouble()
                       var longitud: Double = encuesta.longitud.toDouble()

                       val sydney = LatLng(latitud, longitud)
                       val height = 100
                       val width = 100
                       val imageUrl = foto
                       Glide.with(this)
                           .asBitmap()
                           .load(imageUrl)
                           .apply(RequestOptions.circleCropTransform()) // Aplicar la máscara circular
                           .into(object : CustomTarget<Bitmap>(width, height) {
                               override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                   // Crear el BitmapDescriptor a partir del Bitmap escalado y con máscara circular
                                   val bitmapDescriptor = getRoundedBitmapDescriptor(resource)
                                   cont++

                                   mMap.addMarker(
                                       MarkerOptions()
                                           .position(sydney)
                                           .title("$cont. $nombre")
                                           .icon(bitmapDescriptor)
                                   )
                                   // Mover la cámara a la posición del marcador
                                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13F))
                               }

                               override fun onLoadCleared(placeholder: Drawable?) {
                                   // Manejar caso si la carga de la imagen falla o se cancela
                               }
                           })*/
                }

            }else{
                println("no hay nadaaa")
                showToast("lat $ long $")
            }
        }

        // Add a marker in Sydney and move the camera

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    private fun getRoundedBitmapDescriptor(bitmap: Bitmap): BitmapDescriptor? {
        val size = Math.min(bitmap.width, bitmap.height)
        val radius = size / 2f

        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint().apply {
            isAntiAlias = true
        }
        canvas.drawCircle(radius, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return BitmapDescriptorFactory.fromBitmap(output)
    }
}