package com.abdiel.administradordereportes

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.adaptadores.adaptadorReportes
import com.abdiel.administradordereportes.modelos.reportes
import com.abdiel.administradordereportes.modelos.reportesCopia
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView


class MainActivityReportesEstudiantes : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private  lateinit var adapter: adaptadorReportes

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReferenceEstudiantes: DatabaseReference

    private lateinit var imageView: CircleImageView
    private lateinit var childEventListener: ChildEventListener

    private lateinit var b: Bundle
    private lateinit var uid: String
    private lateinit var botonR: Button
    private lateinit var foto: String
    private lateinit var nombre: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_reportes_estudiantes)
        recyclerView = findViewById(R.id.recyclerReportesEstudiantes)

        b = intent.extras!!
        uid = b.getString("uid").toString()
        foto = b.getString("fotoEstudiante").toString()
        nombre = b.getString("nombre").toString()
        println("HOLA: " + uid)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReferenceEstudiantes = firebaseDatabase.getReference("Estudiantes").child(uid.toString()).child("MisReportes")

        adapter = adaptadorReportes(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.registerAdapterDataObserver(object:RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }
        })

        childEventListener = databaseReferenceEstudiantes.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val r = snapshot.getValue(reportes::class.java)
                val r2 = snapshot.getValue(reportesCopia::class.java)
                //println("mi json" + snapshot.getValue())
                print("min clave "+snapshot.key)
                adapter.agregarReporte(r!!, r2!!)
                adapter.notifyDataSetChanged()

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                adapter.notifyDataSetChanged()
            }

        })

        botonR = findViewById(R.id.buttonRecorrido)
        botonR.setOnClickListener {
          //  var listaDatos: ArrayList<reportesCopia> = ArrayList()
           //  listaDatos =   adapter.listaDatos // Puedes obtener tu ArrayList de alguna manera
            val intent = Intent(this, MapsActivityRecorrido::class.java)
            intent.putParcelableArrayListExtra("MyArray", adapter.listaDatos)
            intent.putExtra("fotoEstudiante", foto)
            intent.putExtra("nombre", nombre)
            startActivity(intent)
        }


    }

    private fun setScrollbar(){
        recyclerView.scrollToPosition(adapter.itemCount-1)
    }
}