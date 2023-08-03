package com.abdiel.administradordereportes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.adaptadores.adaptadorReportes
import com.abdiel.administradordereportes.modelos.reportes
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_reportes_estudiantes)
        recyclerView = findViewById(R.id.recyclerReportesEstudiantes)
println("----------->aqui")
        b = intent.extras!!
        uid = b.getString("uid").toString()
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
                println("mi json" + snapshot.getValue())
                print(snapshot.key)
                adapter.agregarReporte(r!!)
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


    }

    private fun setScrollbar(){
        recyclerView.scrollToPosition(adapter.itemCount-1)
    }
}