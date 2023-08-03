package com.abdiel.administradordereportes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.adaptadores.adaptadorEncuestas
import com.abdiel.administradordereportes.modelos.encuestas
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivityEncuestas : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var miadapter: adaptadorEncuestas

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReferenceEstudiantes: DatabaseReference

    private lateinit var childEventListener: ChildEventListener

    private lateinit var b: Bundle
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_encuestas)

        recyclerView = findViewById(R.id.recyclerEncuestas)

        b = intent.extras!!
        uid = b.getString("uid").toString()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReferenceEstudiantes = firebaseDatabase.getReference("Estudiantes").child(uid).child("EncuestaPersonas")

        miadapter = adaptadorEncuestas(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = miadapter

        miadapter.registerAdapterDataObserver(object:RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollbar()
            }
        })

        childEventListener = databaseReferenceEstudiantes.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                val e = snapshot.getValue(encuestas::class.java)
                miadapter.agregarEncuesta(e!!)
                miadapter.notifyDataSetChanged()
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



    }

    private fun setScrollbar(){
        recyclerView.scrollToPosition(miadapter.itemCount-1)
    }
}