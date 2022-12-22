/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cerrarModulo()
{
    window.location = "menuPrincipal.html";
}

function cargarModuloServicio()
{
    $.ajax({
       context: document.body,
       url    : "gestion/servicio/servicio.html" 
        }).done(function(data){ //Función anónima
            $("#contenedorMenu").html(data);
        }); 
}

function cargarModuloReservacion()
{
    $.ajax({
       context: document.body,
       url    : "gestion/reservacion/reservacion.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        });  
}

function cargarModuloCliente()
{
    $.ajax({
       context: document.body,
       url    : "gestion/cliente/cliente.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        }); 
}

function cargarModuloEmpleado()
{
    $.ajax({
       context: document.body,
       url    : "gestion/empleado/empleado.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        }); 
}

function cargarModuloHorario()
{
    $.ajax({
       context: document.body,
       url    : "gestion/horario/horario.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        }); 
}

function cargarModuloProducto()
{
    $.ajax({
       context: document.body,
       url    : "gestion/producto/producto.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        }); 
}

function cargarModuloSala()
{
    $.ajax({
       context: document.body,
       url    : "gestion/sala/sala.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        }); 
}

function cargarModuloSucursal()
{
    $.ajax({
       context: document.body,
       url    : "gestion/sucursal/sucursal.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        });
        
//    $.ajax(
//            {
//                "url": "gestion/sucursal/sucursal.html",
//                "type": "GET",
//                "async": true
//            }
//    ).done(
//            function (data)
//            {
//                $("#contenedorMenu").html(data);
//            }
//    );
}

function cargarModuloTratamiento()
{
    $.ajax({
       context: document.body,
       url    : "gestion/tratamiento/tratamiento.html" 
        }).done(function(data){
            $("#contenedorMenu").html(data);
        }); 
}
