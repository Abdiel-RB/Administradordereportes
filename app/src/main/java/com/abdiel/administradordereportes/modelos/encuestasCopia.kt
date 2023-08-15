package com.abdiel.administradordereportes.modelos

import android.os.Parcel
import android.os.Parcelable

class encuestasCopia (
    direccion: String,
    fehcha: String,
    pregunta1: String,
    pregunta2: String,
    pregunta3: String,
    pregunta4: String,
    pregunta5: String,
    pregunta6: String,
    pregunta7: String,
    pregunta8: String,
    latitud: String,
    longitud: String
) : Parcelable {
    var direccion: String = direccion
    var fehcha: String = fehcha
    var pregunta1: String = pregunta1
    var pregunta2: String = pregunta2
    var pregunta3: String = pregunta3
    var pregunta4: String = pregunta4
    var pregunta5: String = pregunta5
    var pregunta6: String = pregunta6
    var pregunta7: String = pregunta7
    var pregunta8: String = pregunta8
    var latitud: String = latitud
    var longitud: String = longitud

    constructor(): this ("","","","","","","","","","","","")

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
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
        parcel.writeString(fehcha)
        parcel.writeString(pregunta1)
        parcel.writeString(pregunta2)
        parcel.writeString(pregunta3)
        parcel.writeString(pregunta4)
        parcel.writeString(pregunta5)
        parcel.writeString(pregunta6)
        parcel.writeString(pregunta7)
        parcel.writeString(pregunta8)
        parcel.writeString(latitud)
        parcel.writeString(longitud)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<encuestasCopia> {
        override fun createFromParcel(parcel: Parcel): encuestasCopia {
            return encuestasCopia(parcel)
        }

        override fun newArray(size: Int): Array<encuestasCopia?> {
            return arrayOfNulls(size)
        }
    }


}