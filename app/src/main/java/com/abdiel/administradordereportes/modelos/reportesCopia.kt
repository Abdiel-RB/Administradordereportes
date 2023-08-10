package com.abdiel.administradordereportes.modelos

import android.os.Parcel
import android.os.Parcelable

//Creacion del modelo, todos los atributos de firebase

class reportesCopia(
    direccion: String,
    fechaHora: String,
    latitud: String,
    longitud: String,
    nombreDelTecnico: String,
    oficio: String,
    palabrasClaves: String,
    telefonoDelTecnico: String
) : Parcelable {
    var direccion: String = direccion
    var fechaHora: String = fechaHora
    var latitud: String = latitud
    var longitud: String = longitud
    var nombreDelTecnico: String = nombreDelTecnico
    var oficio: String = oficio
    var palabrasClaves: String = palabrasClaves
    var telefonoDelTecnico: String = telefonoDelTecnico

    constructor(): this ("","","","","","","","")
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(direccion)
        parcel.writeString(fechaHora)
        parcel.writeString(latitud)
        parcel.writeString(longitud)
        parcel.writeString(nombreDelTecnico)
        parcel.writeString(oficio)
        parcel.writeString(palabrasClaves)
        parcel.writeString(telefonoDelTecnico)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<reportesCopia> {
        override fun createFromParcel(parcel: Parcel): reportesCopia {
            return reportesCopia(parcel)
        }

        override fun newArray(size: Int): Array<reportesCopia?> {
            return arrayOfNulls(size)
        }
    }
}
