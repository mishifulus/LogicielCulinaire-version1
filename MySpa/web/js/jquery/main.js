/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cargarInfoAcercade()
{
   $.ajax({
       context: document.body,
       url    : "gestion/informacion/acercade.html" 
        }).done(function(data){
            $("#contenedorMain").html(data); // FORMA 2
        }); 
}

function cargarInfoContacto()
{
   $.ajax({
       context: document.body,
       url    : "gestion/informacion/contacto.html" 
        }).done(function(data){
            //document.getElementById("contenedorPrincipal").innerHTML = data; FORMA 1
            $("#contenedorMain").html(data); // FORMA 2
        }); 
}

function cargarInfoEmpleados()
{
   $.ajax({
       context: document.body,
       url    : "gestion/informacion/empleados.html" 
        }).done(function(data){
            //document.getElementById("contenedorPrincipal").innerHTML = data; FORMA 1
            $("#contenedorMain").html(data); // FORMA 2
        }); 
}

function cargarInfoTratamientos()
{
   $.ajax({
       context: document.body,
       url    : "gestion/informacion/tratamientos.html" 
        }).done(function(data){
            //document.getElementById("contenedorPrincipal").innerHTML = data; FORMA 1
            $("#contenedorMain").html(data); // FORMA 2
        }); 
}

