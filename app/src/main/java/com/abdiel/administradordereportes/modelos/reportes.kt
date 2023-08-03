package com.abdiel.administradordereportes.modelos

//Creacion del modelo, todos los atributos de firebase
class reportes(direccion: String, fechaHora: String, latitud: String, longitud: String, nombreDelTecnico: String, oficio: String, palabrasClaves: String, telefonoDelTecnico: String) {
    var direccion: String = direccion
    var fechaHora: String = fechaHora
    var latitud: String = latitud
    var longitud: String = longitud
    var nombreDelTecnico: String = nombreDelTecnico
    var oficio: String = oficio
    var palabrasClaves: String = palabrasClaves
    var telefonoDelTecnico: String = telefonoDelTecnico

    //Constructor
    constructor(): this ("","","","","","","","")
}