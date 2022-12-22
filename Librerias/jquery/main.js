/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cargarModuloProducto()
{
   $.ajax({
       context: document.body,
       url    : "gestion/producto/producto.html" 
        }).done(function(data){ //Función anónima
            //document.getElementById("contenedorPrincipal").innerHTML = data; FORMA 1
            $("#contenedorPrincipal").html(data); // FORMA 2
        }); 
}

function cargarModuloCliente()
{
    
}

function cargarModuloEmpleado()
{
    
}

function cargarModuloSala()
{
    
}

function cargarModuloSucursal()
{
    
}

function cargarModuloHorario()
{
    
}

function cerrarModulo()
{
    $("#contenedorPrincipal").html("");
}