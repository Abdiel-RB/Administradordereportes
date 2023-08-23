package com.abdiel.administradordereportes

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.adaptadores.adaptadorEncuestas
import com.abdiel.administradordereportes.modelos.encuestas
import com.abdiel.administradordereportes.modelos.encuestasCopia
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class MainActivityEncuestas : AppCompatActivity() {
    private lateinit var imageView: CircleImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var miadapter: adaptadorEncuestas
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReferenceEstudiantes: DatabaseReference
    private lateinit var childEventListener: ChildEventListener
    private lateinit var b: Bundle
    private lateinit var uid: String
    private lateinit var foto: String
    private lateinit var nombre: String
    private lateinit var floatingActionButton: FloatingActionButton

    //atributos para la imagen y mensaje de error
    private lateinit var textViewMensajeError: TextView
    private lateinit var imageButtonPerro: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_encuestas)

        recyclerView = findViewById(R.id.recyclerEncuestas)

        b = intent.extras!!
        uid = b.getString("uid").toString()
        foto = b.getString("fotoEstudiante").toString()
        nombre = b.getString("nombre").toString()
      //  showToast(nombre)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReferenceEstudiantes =
            firebaseDatabase.getReference("Estudiantes").child(uid).child("EncuestaPersonas")

        miadapter = adaptadorEncuestas(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = miadapter

        miadapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }


        })

        //instancias de la imagen y mensaje de error
        textViewMensajeError = findViewById(R.id.textoEncuesta)
        imageButtonPerro = findViewById(R.id.imageButtonEncuesta)


        databaseReferenceEstudiantes.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    println("con datos")

                }else{
                    println("sin datos")

                    //Se activa la visibilidad de la imagen y mensaje de error
                    textViewMensajeError.visibility = View.VISIBLE
                    imageButtonPerro.visibility = View.VISIBLE

                    //Ocultamos el boton flotante
                    floatingActionButton.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


        childEventListener = databaseReferenceEstudiantes.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val e = snapshot.getValue(encuestas::class.java)
                    val e2 = snapshot.getValue(encuestasCopia::class.java)
                    //println("mi json" + snapshot.getValue())
                    println("min clave " + snapshot.key)
                    miadapter.agregarEncuesta(e!!, e2!!)
                    miadapter.notifyDataSetChanged()

                    //Se cambia la visibilidad a oculto
                    textViewMensajeError.visibility = View.GONE
                    imageButtonPerro.visibility = View.GONE

                    //Mostramos el boton flotante
                    floatingActionButton.visibility = View.VISIBLE

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    miadapter.notifyDataSetChanged()
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    miadapter.notifyDataSetChanged()
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    miadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    miadapter.notifyDataSetChanged()
                }
            })
        floatingActionButton = findViewById(R.id.fabMaps)
        //floatingActionButton.imageTintList
        // floatingActionButton.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        floatingActionButton.setOnClickListener {
            //Toast.makeText(this, "funciona", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MapsActivityRecorrido::class.java)
            intent.putParcelableArrayListExtra("MyArray", miadapter.listaDatos)
            intent.putExtra("fotoEstudiante", foto)
            intent.putExtra("nombre", nombre)
            intent.putExtra("mapas", "encuesta")
            startActivity(intent)


        }
    }
    private fun setScrollbar() {
        recyclerView.scrollToPosition(miadapter.itemCount - 1)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}