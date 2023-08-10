package com.abdiel.administradordereportes.adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdiel.administradordereportes.R
import com.abdiel.administradordereportes.modelos.reportes
import com.abdiel.administradordereportes.modelos.reportesCopia

class adaptadorReportes(c: Context): RecyclerView.Adapter<adaptadorReportes.ViewHolder>() {
    var lista = ArrayList<reportes>()
    var listaDatos= ArrayList<reportesCopia>()
    var context = c

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val fecha = view.findViewById<TextView>(R.id.textViewFecha)
        val direccion = view.findViewById<TextView>(R.id.textViewDireccion)
        val nombre = view.findViewById<TextView>(R.id.textViewNombre)
        val telefono = view.findViewById<TextView>(R.id.textViewTelefono)
        val oficio = view.findViewById<TextView>(R.id.textViewOficio)
        val palabrasClaves = view.findViewById<TextView>(R.id.textViewPalabrasClaves)
        val numeroReporte = view.findViewById<TextView>(R.id.numeroReporte)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_reporte_tecnicos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.fecha.text = lista[position].fechaHora.trim()
        holder.direccion.text = lista[position].direccion.trim()
        holder.nombre.text = lista[position].nombreDelTecnico.trim()
        holder.telefono.text = lista[position].telefonoDelTecnico.trim()
        holder.oficio.text = lista[position].oficio.trim()
        holder.palabrasClaves.text = lista[position].palabrasClaves.trim()


        //for (i in 1 until  lista.size){
        val po = position +1
            holder.numeroReporte.text = po.toString()
       // }

    }

    fun agregarReporte(reportes: reportes, reportesCopia: reportesCopia){
        lista.add(reportes)
        listaDatos.add(reportesCopia)
        notifyItemInserted(lista.size)
    }

    fun inicializarReporte(){
        var tam = lista.size
        lista.remove(lista.get(tam-1))
        notifyItemInserted(lista.size)
    }
}