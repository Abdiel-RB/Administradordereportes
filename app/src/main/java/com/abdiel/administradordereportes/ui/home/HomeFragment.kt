package com.abdiel.administradordereportes.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.MainActivityEncuestas
import com.abdiel.administradordereportes.MainActivityReportesEstudiantes
import com.abdiel.administradordereportes.R
import com.abdiel.administradordereportes.adaptadores.adaptadorPerfiles
import com.abdiel.administradordereportes.databinding.FragmentHomeBinding
import com.abdiel.administradordereportes.modelos.usuarios
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.text.DateFormat
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var recyclerView: RecyclerView
    private lateinit var miAdaptador: adaptadorPerfiles
    private lateinit var firebaseDatabaseEstudiante: FirebaseDatabase
    private lateinit var databaseEstudiante: DatabaseReference

    private lateinit var bundle: Bundle


    private lateinit var botonReportes: Button

    private lateinit var dialogOpciones: Dialog



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.recyclerEstudiantes
        miAdaptador = adaptadorPerfiles(requireContext())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = miAdaptador

        bundle = Bundle()


        dialogOpciones = Dialog(requireContext())


        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        miAdaptador.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                setScrollBar()
            }
        })

        var firebaseDatabaseLista: FirebaseDatabase
        var databaseReference: DatabaseReference
        firebaseDatabaseLista = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabaseLista.getReference("Estudiantes")

        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                mostrarInformacion(snapshot)
                miAdaptador.notifyDataSetChanged();
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                miAdaptador.notifyDataSetChanged();
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                miAdaptador.notifyDataSetChanged();
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                miAdaptador.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {
                miAdaptador.notifyDataSetChanged();
            }


        })

        return root
    }

    private fun setScrollBar() {
        recyclerView.scrollToPosition(miAdaptador.itemCount)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun mostrarInformacion(snapshot: DataSnapshot){

        firebaseDatabaseEstudiante = FirebaseDatabase.getInstance()
        databaseEstudiante = firebaseDatabaseEstudiante.getReference("Estudiantes").child(snapshot.key.toString()).child("informacionEstudiante")

       databaseEstudiante.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val u2 = snapshot.getValue(usuarios::class.java)

                if (u2 == null) {
                    Toast.makeText(
                        requireContext(),
                        "No Tienes Ningun Estudiante Registrado",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    miAdaptador.agregarUsuario(u2!!)
                }
                val mGestureDetector = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                    GestureDetector(
                        requireContext(),
                        object : GestureDetector.SimpleOnGestureListener() {
                            override fun onSingleTapUp(e: MotionEvent): Boolean {
                                return true
                            }
                        })
                } else {
                    TODO("VERSION.SDK_INT < CUPCAKE")
                }

                recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        try {
                            val child = recyclerView.findChildViewUnder(e.x, e.y)
                            if (child != null && mGestureDetector.onTouchEvent(e)) {
                                val position = recyclerView.getChildAdapterPosition(child)
                                val correo = miAdaptador.lista[position].correo
                                val uid =  miAdaptador.lista[position].uid
                                val foto = miAdaptador.lista[position].foto
                                val nombre = miAdaptador.lista[position].nombre

                                println(correo.trim())
                               // showToast("hola soy ${miAdaptador.lista[position].nombre} ${miAdaptador.lista[position].apellido_Paterno} ${miAdaptador.lista[position].apellido_Materno} ${miAdaptador.lista[position].uid}")

                               /* val intent = Intent (requireContext(), MainActivityEncuestas::class.java)
                                bundle.putString("uid", miAdaptador.lista[position].uid)

                                intent.putExtras(bundle)
                                startActivity(intent)*/
                                opciones(uid, foto, nombre)
                                return true
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        return false

                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

                    }

                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                        TODO("Not yet implemented")
                    }


                })

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    fun opciones(uid: String, foto: String, nombre: String){
        dialogOpciones.setContentView(R.layout.opciones_pop_up)
        dialogOpciones.setCanceledOnTouchOutside(false) // Evitar el cierre al hacer clic fuera del diálogo

        botonReportes = dialogOpciones.findViewById(R.id.botonReportes)
        val botonEncuaestas = dialogOpciones.findViewById<Button>(R.id.botonEncuestas)

        botonReportes.setOnClickListener{
         //Toast.makeText(requireContext(), "click en reportes", Toast.LENGTH_SHORT) .show()

            val intent = Intent (requireContext(), MainActivityReportesEstudiantes::class.java)
            bundle.putString("uid", uid)
            bundle.putString("fotoEstudiante", foto)
            bundle.putString("nombre", nombre)

            intent.putExtras(bundle)
            startActivity(intent)
            dialogOpciones.dismiss()

        }

        botonEncuaestas.setOnClickListener{
            //Toast.makeText(requireContext(), "click en encuestas", Toast.LENGTH_SHORT) .show()

            val intent = Intent (requireContext(), MainActivityEncuestas::class.java)
            bundle.putString("uid", uid)

            intent.putExtras(bundle)
            startActivity(intent)
            dialogOpciones.dismiss()
        }
        dialogOpciones.show()
    }

}