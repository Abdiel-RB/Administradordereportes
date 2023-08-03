package com.abdiel.administradordereportes.adaptadores

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.R
import com.abdiel.administradordereportes.modelos.usuarios

import com.squareup.picasso.Picasso


class adaptadorPerfiles(c: Context): RecyclerView.Adapter<adaptadorPerfiles.ViewHolder>()  {
    var lista = ArrayList<usuarios>()
    private var context = c

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val foto = view.findViewById<ImageView>(R.id.imageView)
        val fecha = view.findViewById<TextView>(R.id.textViewFecha)
        val nombre = view.findViewById<TextView>(R.id.textViewNombre)
        val matricula = view.findViewById<TextView>(R.id.textViewMatricula)
        val correo = view.findViewById<TextView>(R.id.textViewCorreo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_perfil_estudiante, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(lista[position].foto.trim()).into(holder.foto)
        holder.fecha.text = lista[position].fechaHora.trim()
        val nombre = "${lista[position].nombre.trim()} ${lista[position].apellido_Paterno.trim()} ${lista[position].apellido_Materno.trim()}"
        holder.nombre.text = nombre
        holder.matricula.text = lista[position].matricula.trim()
        holder.correo.text = lista[position].correo.trim()


    }

    fun agregarUsuario(reportes: usuarios){
        lista.add(reportes)
        println("tamaño de la lista: ${lista.size}")
        notifyItemInserted(lista.size)
    }



}