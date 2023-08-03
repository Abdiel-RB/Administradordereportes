package com.abdiel.administradordereportes.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.R
import com.abdiel.administradordereportes.modelos.encuestas

class adaptadorEncuestas(c: Context): RecyclerView.Adapter<adaptadorEncuestas.ViewHolder>() {
    var lista = ArrayList<encuestas>()
    var context = c

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val fecha = view.findViewById<TextView>(R.id.textViewFecha)
        val pregunta1 = view.findViewById<TextView>(R.id.textViewPregunta1)
        val pregunta2 = view.findViewById<TextView>(R.id.textViewPregunta2)
        val pregunta3 = view.findViewById<TextView>(R.id.textViewPregunta3)
        val pregunta4 = view.findViewById<TextView>(R.id.textViewPregunta4)
        val pregunta5 = view.findViewById<TextView>(R.id.textViewPregunta5)
        val pregunta6 = view.findViewById<TextView>(R.id.textViewPregunta6)
        val pregunta7 = view.findViewById<TextView>(R.id.textViewPregunta7)
        val pregunta8 = view.findViewById<TextView>(R.id.textViewPregunta8)

        val numeroEncuestas = view.findViewById<TextView>(R.id.numeroEncuesta)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_encuesta_personas, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fecha.text = lista[position].fehcha.trim()
        holder.pregunta1.text = lista[position].pregunta1.trim()
        holder.pregunta2.text = lista[position].pregunta2.trim()
        holder.pregunta3.text = lista[position].pregunta3.trim()
        holder.pregunta4.text = lista[position].pregunta4.trim()
        holder.pregunta5.text = lista[position].pregunta5.trim()
        holder.pregunta6.text = lista[position].pregunta6.trim()
        holder.pregunta7.text = lista[position].pregunta7.trim()
        holder.pregunta8.text = lista[position].pregunta8.trim()


        //Contador para el numero de Encuesta
        val en = position +1
        holder.numeroEncuestas.text = en.toString()


    }

    fun agregarEncuesta(encuestas: encuestas){
        lista.add(encuestas)
        notifyItemInserted(lista.size)
    }

    fun inicializarEncuesta(){
        var tam = lista.size
        lista.remove(lista.get(tam-1))
        notifyItemInserted(lista.size)
    }
}