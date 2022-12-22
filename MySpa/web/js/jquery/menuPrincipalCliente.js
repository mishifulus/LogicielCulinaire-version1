/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cerrarSesion()
{
    window.location = "gestion/sesion/iniciarsesion.html";
}

function cargarModuloReservacionCliente()
{
    $.ajax({
       context: document.body,
       url    : "gestion/reservacionCliente/reservacionCliente.html" 
        }).done(function(data){
            $("#contenedorMenuCliente").html(data);
        }); 
}

function cargarModuloInformacionCliente()
{
    $.ajax({
       context: document.body,
       url    : "gestion/informacionCliente/informacionCliente.html" 
        }).done(function(data){
            $("#contenedorMenuCliente").html(data);
        }); 
}
