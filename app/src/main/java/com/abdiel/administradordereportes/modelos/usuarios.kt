package com.abdiel.administradordereportes.modelos

class usuarios(matricula: String, nombre: String, apellido_Paterno: String, apellido_Materno: String, foto: String, fechaHora: String, correo: String, uid: String) {
    var matricula: String = matricula
    var nombre: String = nombre
    var apellido_Paterno: String = apellido_Paterno
    var apellido_Materno: String = apellido_Materno
    var foto: String = foto
    var fechaHora: String = fechaHora
    var correo: String = correo
    var uid: String = uid

    constructor() : this("","","","","", "","", "")
}