package com.abdiel.administradordereportes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.abdiel.administradordereportes.conexionInternet.conexionInternet
import com.abdiel.administradordereportes.databinding.ActivityMainBinding
import com.abdiel.administradordereportes.ui.home.HomeFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DateFormat
import java.util.Arrays
import java.util.Date

class MainActivity : AppCompatActivity() {

    //Autentication para saber la hora activo
    var providers: MutableList<AuthUI.IdpConfig?>? = null
    private var mAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var MY_REQUEST_CODE = 200


    //Prueba
    private var prueba = 220

    private lateinit var ImageViewFoto: CircleImageView
    private lateinit var txt_nombre: TextView
    private lateinit var txt_correo: TextView

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        setTheme(R.style.Theme_AdministradorDeReportes)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
/* ESTE ERA EL BOTON EN KOTLIN-------------------------
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        */
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val view = navView.getHeaderView(0)
        txt_nombre = view.findViewById(R.id.txtNombre)
        txt_correo = view.findViewById(R.id.txtCorreo)
        ImageViewFoto = view.findViewById(R.id.imageViewFoto)

        //Firebase
        mAuth = FirebaseAuth.getInstance()
        providers = Arrays.asList(
            AuthUI.IdpConfig.GoogleBuilder().build()

        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val conexion = conexionInternet(this)

        if (conexion.isInternetConnected()){
            if (requestCode == MY_REQUEST_CODE){
                currentUser = mAuth!!.currentUser
                txt_nombre.text = currentUser!!.displayName.toString()
                txt_correo.text = currentUser!!.email.toString()
                cargarImagen(currentUser!!.photoUrl.toString())
                Toast.makeText(this@MainActivity, "Bienvenido ${currentUser!!.email}", Toast.LENGTH_SHORT).show()
                val f = HomeFragment()
                var fm = supportFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment_content_main, f).commit()
                }

            }else{
                finish()
            }
        }else{
            Toast.makeText(this@MainActivity, "No tienes Internet", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onStart() {
        super.onStart()
        currentUser = mAuth!!.currentUser
        if(currentUser != null){
            txt_nombre.text = currentUser!!.displayName.toString()
            txt_correo.text = currentUser!!.email.toString()
            cargarImagen(currentUser!!.photoUrl.toString())

            val database = FirebaseDatabase.getInstance()
            val databaseReference = database.getReference("Administrador").child(currentUser!!.uid.toString())
            val updates = HashMap<String, Any>()
            updates["fechaHora"] = DateFormat.getDateTimeInstance().format(Date())
            databaseReference.child("informacionAdministrador").updateChildren(updates){ error, ref ->
                if (error!=null){
                    //Manejar el error en caso de que ocurra
                }else{
                    Toast.makeText(this@MainActivity, "Bienvenido ${currentUser!!.displayName}", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            muestraOpciones()
        }
    }


    private fun cargarImagen(urlImagen: String){
        //cargando la imagen en la ImageView
        Picasso.get().load(urlImagen).into(ImageViewFoto)
    }

    fun muestraOpciones(){
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(providers!!)
                .build(),MY_REQUEST_CODE
        )
    }

    //funcion para seleccionar, en la parte de los 3 puntos
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_salir -> {
                //Codigo para manejar la selecion del item 1
                metodoSalir()
                return true
            }
            // Agrega más casos para otros ítems del menú si es necesario
            else -> return super.onOptionsItemSelected(item)
        }

    }

//Funcion para cerrar sesion
    fun metodoSalir(){
        val conexionInternet = conexionInternet(this)
        if (conexionInternet.isInternetConnected()){
            AuthUI.getInstance()
                .signOut(applicationContext).addOnCompleteListener{
                }.addOnFailureListener{ e ->
                    Toast.makeText(applicationContext, "" + e.message, Toast.LENGTH_LONG).show()
                }
            finish()
        }else{
            Toast.makeText(this@MainActivity, "No tienes Internet", Toast.LENGTH_SHORT).show()
        }
    }
}