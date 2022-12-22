var reservaciones;
var clientes;
var empleados;
var tratamientos;
var productos;
var servicioActual;
var servicioTratamientoActual;
var sucursales;

function inicializarModuloReserva()
{
    $("#divDetalleReserva").hide();
    $("#divTablaClientesReserva").hide();
    $("#divEmpleadoReserva").hide();
    $("#divTratamientoReserva").hide();
    $("#divProductoReserva").hide();
    $("#divTratamientosProductosReserva").hide();
    setFormularioDetalleVisible(false);
    setFormularioDetalleVisibleServicio(false);
    refrescarTablaReserva();
}

function setFormularioDetalleVisible(valor)
{
    if(valor)
    {
        $("#divTablaReservas").removeClass("col-12");
        $("#divTablaReservas").addClass("col-8");
        $("#divDetalleReserva").show(); 
    }
    else
    {
        $("#divDetalleReserva").hide();
        $("#divTablaReservas").removeClass("col-8");
        $("#divTablaReservas").addClass("col-12");
    }
}

function guardarReserva()
{
    var estatus = 1;
    var fecha = $("#txtFecha").val();
    var sala = $("#salaReserva").val();
    var cliente = $("#txtCodigoCliente").val();
    var horario = $("#txtHorario").val();
    
    var data = {"fecha": fecha, "estatus": estatus, "sala": sala, "cliente": cliente, "horario": horario, "t": sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/reservacion/insert",
                "type": "GET",
                "async": true,
                "data": data
            }).done(
                    function (data)
            {
                if (data.error != null)
                {
                    Swal.fire("Error", data.error, "error");
                }
                else if (data.exception != null)
                {
                    Swal.fire("Error inesperado", data.exception, "error");
                }
                else if (data.result != null)
                {
                    cliente = $("#txtCodigoCliente").val("");
                    var nombreC = $("#txtCliente").val("");
                    inicializarModuloReserva();
                    
                    Swal.fire("Inserción realizada con éxito", data.result, "success");
                }
            });
}

function buscarClienteReserva()
{
    //Elemento visual de la busqueda de cliente txtserachCB
    var searchCB = document.getElementById("txtsearchCB").value;
    var data = {filter: searchCB, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                url: "api/cliente/search",
                type: "GET",
                async: true,
                data: data
                }).done(function(data)
                {
                    clientes = data;
                    var contenido = "";
                    for (var i = 0; i < clientes.length; i++)
                    {
                        var nombreCompleto = clientes[i].persona.nombre+" "+clientes[i].persona.apellidoP+" "+
                                clientes[i].persona.apellidoM;
                        contenido+="<tr>";
                        contenido+="<td>"+nombreCompleto+"</td>";
                        contenido+="<td><button class='btn btn-outline-success' onclick='seleccionarClienteReserva("+i+")'>Elegir</button></td>";
                        contenido+="</tr>";
                    }
                    
                    $("#tbodyClientesReserva").html(contenido);
                    $("#divTablaClientesReserva").show();
                    cargarSucursalesReserva();
                });
}

function seleccionarClienteReserva(i)
{
    var nombreC = clientes[i].persona.nombre + " " + clientes[i].persona.apellidoP + " " + clientes[i].persona.apellidoM;
    $("#txtCliente").val(nombreC);
    $("#txtCodigoCliente").val(clientes[i].id);
    cerrarTablaC();
}

function cargarSucursalesReserva()
{
    var data = {"estatus": 1, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/sucursal/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function(data)
            {
                sucursales = data;
                var contenido = "";
                for (var i = 0; i < sucursales.length; i++)
                {
                    contenido+="<option value='"+sucursales[i].id+"'>";
                    contenido+=" "+sucursales[i].nombre+" ";
                    contenido+="</option>";
                }
                //Colocar el contenido en la lista de sucursalReserva
                $("#sucursalReserva").html(contenido);
            });
}

function buscarSalaReserva()
{
    var idSucursal = $("#sucursalReserva").val();
    var data = {"idSucursal": idSucursal, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url":"api/sala/getAllBySucursal",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function(data)
            {
                var salas = data;
                var contenido = "";
                for (var i = 0; i < salas.length; i++)
                {
                    contenido+="<option value='"+salas[i].id+"'>";
                    contenido+=" "+salas[i].nombre+" ";
                    contenido+="</option>";
                }
                //Colocar el contenido en la lista de salaReserva
                $("#salaReserva").html(contenido);
            });
}

function consultarHorario()
{
    var fecha = document.getElementById("txtFecha").value;
    var sala = document.getElementById("salaReserva").value;
    
    var data = {"fecha": fecha, "sala": sala, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/reservacion/getHourAv",
                "type": "GET",
                "async": true,
                "data": data
            }
            ).done(function(data)
    {
        var horas = data;
        var contenido = "";
        for(var i = 0; i < horas.length; i++)
        {
            contenido+= "<option value='"+ horas[i].id + "'>";
            contenido+= " " + horas[i].horaI + "-" + horas[i].horaF;
            contenido+= "</option>";
        }
        $("#txtHorario").html(contenido);
    });
}

function cancelarReserva(i)
{
    var data = {"id": reservaciones[i].id, "t":sessionStorage.getItem("token")};

    $.ajax({
        "url": "api/reservacion/cancel",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.result !== null) {
            Swal.fire("Cancelación exitosa", data.result, "success");
        } else if (data.error !== null) {
            Swal.fire("Error en la cancelación", data.error, "error");
        }
        refrescarTablaReserva();
    });
}

function limpiarFormulario()
{
    $('#txtCodigoReserva').val('');
    $('#txtCodigoCliente').val('');
    $('#txtsearchCB').val('');
    $('#txtCliente').val('');
    document.getElementById("sucursalReserva").value = "";
    document.getElementById("salaReserva").value = "";
    $('#txtFecha').val('');
    document.getElementById("txtHorario").value = "";
    $('#txtEstatus').val('');
    $('#txtFiltro').val('');
}

function refrescarTablaReserva()
{
    var data = {"estatus":1, "t": sessionStorage.getItem("token")};
    
    $.ajax({
        "url": "api/reservacion/getAll",
        "type": "GET",
        "async" : true,
        "data" : data
    }).done(function (data)
    {
        reservaciones = data;
        var contenido = "";
        
        for (var i = 0; i < reservaciones.length; i++)
        {
            var nombreCom = reservaciones[i].cliente.persona.nombre + " " + reservaciones[i].cliente.persona.apellidoP + " " +
                    reservaciones[i].cliente.persona.apellidoM;
                    
            contenido += "<tr>";
            contenido += "<td>" + reservaciones[i].id + "</td>"
            contenido += "<td>" + reservaciones[i].fecha + "</td>";
            contenido += "<td>" + reservaciones[i].horario.horaI + "</td>";
            contenido += "<td>" + reservaciones[i].horario.horaF + "</td>";
            contenido += "<td>" + nombreCom + "</td>";
            contenido += "<td>" + reservaciones[i].sala.nombre + "</td>";
            contenido += "<td>" + reservaciones[i].estatus + "</td>";
            contenido += "<td> <button class='btn btn-outline-danger' onclick='cancelarReserva(" + i + ");'><i class='fa fa-trash'></i>&nbsp;&nbsp;Cancelar</button> </td>";
            contenido += "<td> <button class='btn btn-outline-primary' onclick='atenderReserva(" + i + ");'><i class='fa fa-check'></i>&nbsp;&nbsp;Atender</button> </td>";
            contenido += "</tr>";
        }
        $("#tbodyReservas").html(contenido);
    });
}

function refrescarTablaInactivos()
{
    var data = {"estatus":0, "t": sessionStorage.getItem("token")};
    
    $.ajax({
        "url": "api/reservacion/getAll",
        "type": "GET",
        "async" : true,
        "data" : data
    }).done(function (data)
    {
        reservaciones = data;
        var contenido = "";
        
        for (var i = 0; i < reservaciones.length; i++)
        {
            var nombreCom = reservaciones[i].cliente.persona.nombre + " " + reservaciones[i].cliente.persona.apellidoP + " " +
                    reservaciones[i].cliente.persona.apellidoM;
                    
            contenido += "<tr>";
            contenido += "<td>" + reservaciones[i].fecha + "</td>";
            contenido += "<td>" + reservaciones[i].horario.horaI + "</td>";
            contenido += "<td>" + reservaciones[i].horario.horaF + "</td>";
            contenido += "<td>" + nombreCom + "</td>";
            contenido += "<td>" + reservaciones[i].sala.nombre + "</td>";
            contenido += "<td>" + reservaciones[i].estatus + "</td>";contenido += "</tr>";
        }
        $("#tbodyReservas").html(contenido);
    });
}

function buscarReserva()
{
    var data = {"filter": document.getElementById("txtFiltro").value, "t":sessionStorage.getItem("token")};
    
    $.ajax({
        "url": "api/reservacion/search",
        "type": "GET",
        "async" : true,
        "data" : data
    }).done(function (data)
    {
        reservaciones = data;
        var contenido = "";
        
        for (var i = 0; i < reservaciones.length; i++)
        {
            var nombreCom = reservaciones[i].cliente.persona.nombre + " " + reservaciones[i].cliente.persona.apellidoP + " " +
                    reservaciones[i].cliente.persona.apellidoM;
                    
            contenido += "<tr>";
            contenido += "<td>" + reservaciones[i].fecha + "</td>";
            contenido += "<td>" + reservaciones[i].horario.horaI + "</td>";
            contenido += "<td>" + reservaciones[i].horario.horaF + "</td>";
            contenido += "<td>" + nombreCom + "</td>";
            contenido += "<td>" + reservaciones[i].sala.nombre + "</td>";
            contenido += "<td>" + reservaciones[i].estatus + "</td>";
            contenido += "<td> <button class='btn btn-outline-danger' onclick='cancelarReserva(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
            contenido += "<td> <button class='btn btn-outline-primary' onclick='atenderReserva(" + i + ");'><i class='fa fa-check'></i></button> </td>";
            contenido += "</tr>";
        }
        $("#tbodyReservas").html(contenido);
    });
}

function buscarEmpleadoReserva()
{
    var searchEB = document.getElementById("txtSearchEB").value;
    var data = {"filter": searchEB, "t": sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/empleado/search",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function (data)
            {
                empleados = data;
                var contenido = "";
                for (var i = 0; i < empleados.length; i++)
                {
                    var nombreCompleto = empleados[i].persona.nombre + " "
                        + empleados[i].persona.apellidoP + " " + 
                        empleados[i].persona.apellidoM;
                    contenido += "<tr>";
                    contenido += "<td>"+ nombreCompleto + "</td>";
                    contenido += "<td><button onclick='seleccionarEmpleadoReserva("+i+");'>Elegir</button></td>";
                    contenido += "</tr>";
                }
                $("#tbodyEmpleadosReserva").html(contenido);
                $("#divEmpleadoReserva").show();
            });
}

function seleccionarEmpleadoReserva(i)
{
    var nombreC = empleados[i].persona.nombre + " "
            + empleados[i].persona.apellidoP + " "
            + empleados[i].persona.apellidoM;
    $("#txtEmpleadoReserva").val(nombreC);
    $("#txtIdEmpleadoReserva").val(empleados[i].id);
    
    //Primero declaramos el nombr ey el tipo del atributo
    servicioActual.empleado = new Object();
    //Asignamos el valor
    servicioActual.empleado = empleados[i];
    cerrarTablaE();
}

function cargarTratamientosReserva()
{
    var data = {"estatus": 1, "tok": sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/tratamiento/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function (data)
            {
                tratamientos = data;
                var contenido = "";
                for (var i = 0; i < tratamientos.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + tratamientos[i].id + "-" + tratamientos[i].nombre +  " - $" + tratamientos[i].costo + "</td>";
                    contenido += "<td><button onclick='agregarTratamientoReserva("+i+");'>Agregar</button></td>";
                    contenido += "</tr>";
                }
                $("#tbodyTratamientosReserva").html(contenido);
            });
            
            $("#divTratamientoReserva").show();
            
            servicioActual.serviciosT = [];
}

function agregarTratamientoReserva(i)
{
    var t = tratamientos[i];
    var i = servicioActual.serviciosT.length;
    var tr = "<tr>"+
            "<td>" + t.id + "</td>" +
            "<td> $ " + t.costo + "</td>" +
            "<td class = 'text-right'>" + t.nombre + "</td>" +
            "<td>" +
            "<a href = '#' class = 'text-primary' onclick='cargarProductosTratamiento("+ t.id +");'>"+
            "<i class = 'fas fa-cog'></i>&nbsp;Detalle"+
            "</a>"+
            "<a href='#' class='text-danger' onclick='borrarTratamientoReserva("+ t.id + ");'>"+
            "<i class = 'fas fa-trash'></i>&nbsp;Quitar"+
            "</a>" +
            "</td>" +
            "</tr>";
    
    servicioActual.serviciosT[i] = new Object();
    servicioActual.serviciosT[i].tratamiento = new Object();
    servicioActual.serviciosT[i].tratamiento = t;
    servicioActual.serviciosT[i].productos = new Object();
    servicioActual.serviciosT[i].productos = [];
    
    $("#tbodyServicioTratamientos").append(tr);
}

function borrarTratamientoReserva(idTratamiento)
{
    for (var i = 0; i < servicioActual.serviciosT.length; i++)
    {
        if (servicioActual.serviciosT[i].tratamiento.id === idTratamiento)
        {
            servicioActual.serviciosT.splice(i,1);
            $("#tbodyServicioTratamientos tr").eq(i).remove();
            return;
        }
    }
}

function cargarProductosTratamiento(idTratamiento)
{
    var content = "";
    servicioTratamientoActual = null;
    
    //Buscamos el tratamiento asociado al servicio actual
    servicioTratamientoActual = buscarServicioTratamiento(idTratamiento);
    
    if(servicioTratamientoActual != null)
    {
        //Cargamos el listado de productos que han sido agregados
        for (var i = 0; i < servicioTratamientoActual.productos.length; i++)
        {
            content += '<tr>' +
                    '<td>' + servicioTratamientoActual.productos[i].id + '</td>' +
                    '<td>' + servicioTratamientoActual.productos[i].nombre + '</td>' +
                    '<td>' + servicioTratamientoActual.productos[i].precioUso + '</td>' +
                    '<td>' +
                        '<a href = "#"' + 'class = "text-danger" onclick="borrarProductoServicio('+idTratamiento+','+ servicioTratamientoActual.productos[i].id+');">'+
                            '<i class= "fas fa-trash"></i>&nbsp;Eliminar' +
                        '</a>' +
                    '</td>' +
                    '</tr>';
        }
        $('#tbodyTratamientosProductosReserva').html(content);
        $('#tratamientoTitulo').html(servicioTratamientoActual.tratamiento.nombre);
        $('#divTratamientosProductosReserva').show();
    }
}

function buscarServicioTratamiento(id)
{
    for(var i=0; i < servicioActual.serviciosT.length; i++)
    {
        if(servicioActual.serviciosT[i].tratamiento.id === id)
            return servicioActual.serviciosT[i];
        return null;
    }
}

function cargarProductosReserva()
{
    var data = {"estatus": 1, "t": sessionStorage.getItem("token")};
    
    $.ajax({
                "type": "GET",
                "dataType": "json",
                "url": "api/producto/getAll",
                "async": true,
                "data": data
    }).done(function(datos)
    {
       productos = datos; 
       var contenido = '';
       for (var i = 0; i < productos.length; i++)
       {
           contenido += '<tr>';
           contenido += '<td class="text-right">' + productos[i].id + '</td>';
           contenido += '<td>' + productos[i].nombre + ' $' + productos[i].precioUso + '</td>';
           contenido += '<td>';
           contenido += '<a href="#" class="text-primary" onclick="agregarProductosReserva('+ i + ');">';
           contenido += '<i class="fas fa-plus"></i>';
           contenido += '</a>';
           contenido += '</td>';
           contenido += '</tr>';
       }
       
       $('#tbodyProductosReserva').html(contenido);
       $('#divProductoReserva').show();
    });
}

function agregarProductosReserva(index)
{
    var p = productos[index];
    var i = 0;
    var tr = null;
    var t = null;
    
    if (servicioTratamientoActual == null)
    {
        Swal.fire("Se debe seleccionar un tratamiento", "", "error");
        return;
    }
    
    i = servicioTratamientoActual.productos.length;
    t = servicioTratamientoActual.tratamiento;
    //ServicioTratamientoActual.productos[i] = new Object();
    servicioTratamientoActual.productos[i] = p;
    
    //Cada vez que agrego un producto, debo identificar el TR paea
    //poderlo eliminar cuando quito productos de un tratamiento
    tr = '<tr id="tr_' + t.id + '_' + p.id + '">' +
            '<td>' + p.id + '</td>' +
            '<td>' + p.nombre + '</td>' +
            '<td> $' + p.precioUso + '</td>' +
            '<td>' + 
            '<a href="#" class="text-danger" ' +
            'onclick="borrarProductoServicio(' + t.id + ', ' + p.id + ');">' +
            '<i class="fas fa-trash"></i>&nbsp;Quitar'+
            '</a></td></tr>';
    $("#tbodyTratamientosProductosReserva").append(tr);
    calculateTotal();
}

function calculateTotal()
{
    var subtotal = 0;
    var total = 0;
    var st = null;
    
    for (var i = 0; i < servicioActual.serviciosT.length; i++)
    {
        subtotal = 0;
        st = servicioActual.serviciosT[i];
        subtotal = st.tratamiento.costo;
        
        for (var j = 0; j < st.productos.length; j++)
        {
            subtotal += st.productos[j].precioUso;
        }
        total += subtotal;
    }
    $("#txtTotal").val(total);
}

function borrarProductoServicio(idTratamiento, idProducto)
{
    for (var i = 0; i < servicioActual.serviciosT.length; i++)
    {
        if (servicioActual.serviciosT[i].tratamiento.id === idTratamiento)
        {
            for (var j = 0; j < servicioActual.serviciosT[i].productos.length; j++)
            {
                if (servicioActual.serviciosT[i].productos[j].id === idProducto)
                {
                    servicioActual.serviciosT[i].productos.splice(j, 1);
                    $('#tr_' + servicioActual.serviciosT[i].tratamiento.id + '_' + idProducto).remove();
                    calculateTotal();
                }
            }
        }
    }
}

//no funciona
function guardarServicio()
{
    var d = new Date();
    var di = d.getDate();
    var m = d.getMonth() + 1;
    var y = d.getFullYear();
    var hoy = y + '/' + m + '/' + di;
    servicioActual.fecha = hoy;
    
    alert(JSON.stringify(servicioActual));
    
    var error = servicio_validarDatos();
    
    if(error != null)
    {
        Swal.fire(error.titulo, error.descripcion, error.tipo);
        return;
    }
    
    var data = {"s": JSON.stringify(servicioActual), "t": sessionStorage.getItem("token")};
    $.ajax({
        "type": "POST",
        "dataType": "json",
        "url": "api/servicio/insert",
        "async": true,
        "data": data
    }).done(function(data){
        if (data.error != null)
        {
            Swal.fire('Error', data.error, 'error');
            return;
        }
        if (data.exception != null)
        {
            Swal.fire('Error', data.exception, 'error');
            return;
        }
        Swal.fire('Movimiento realizado','','success');
    });
}

// revisar
function servicio_validarDatos()
    {
        var sinProductos = false;
        if (servicioActual == null)
        {
            return {titulo: "", descripcion: "Error desconocido", tipo: "error"};
        }
        if (servicioActual.reservacion == null)
        {
            return {titulo: "Verificar datos", descripcion: "Seleccione una reservación...", tipo: "warning"};
        }
        if (servicioActual.serviciosT.length < 1)
        {
            return {titulo: "Verificar datos", descripcion: "Debe asociar por lo menos un tratamiento al servicio...", tipo: "warning"};
        }
        for (var i = 0; i < servicioActual.serviciosT.length; i++)
        {
            if(servicioActual.serviciosT[i].productos.length < 1)
            {
                sinProductos = true;
                i = servicioActual.serviciosT.length + 1;
            }
        }
        if (sinProductos)
        {
            return {titulo: "Verificar Datos", descripcion: "Verifique que todos los tratamientos tengan productos", tipo: "warning"};
        }
        return null;
}

//atender
function atenderReserva(i)
{
    var datosReserva = reservaciones[i].cliente.persona.nombre + " " +
            reservaciones[i].cliente.persona.apellidoP + " " +
            reservaciones[i].cliente.persona.apellidoM + "\n" +
            reservaciones[i].sala.nombre + "\n" +
            reservaciones[i].fecha + "\n" +
            reservaciones[i].horario.horaI + "-" + reservaciones[i].horario.horaF;
        $("#txaReservacion").html(datosReserva);
        $("#divServicioDetalle").show();
        
        //Definimos que el servicio actual es un objeto    
        servicioActual = new Object();
        //Declaramos un atributo que es de tipo objeto
        servicioActual.reservacion = new Object();
        //Sele asigna el objeto de la reservacion seleccionada al objeto-atributo reservacion
        servicioActual.reservacion = reservaciones[i];
}

function agregarNuevo()
{
    setFormularioDetalleVisible(true);
    limpiarFormulario();
}

function validarNumeros(event)
{
    if(event.charCode >= 48 && event.charCode <= 57 || event.charCode === 58 || event.charCode === 47)
    {
        return true;
    }
    return false;
}

function validarTextos(event)
{
    if(event.charCode >= 65 && event.charCode <= 90 || event.charCode === 32 || event.charCode >= 97 && event.charCode <= 122)
    {
        return true;
    }
    return false;
}

function cerrarTablaC()
{
    $("#divTablaClientesReserva").hide();
}

function cerrarTablaE()
{
    $("#divEmpleadoReserva").hide();
}

function cerrarTablaT()
{
    $("#divTratamientoReserva").hide();
}

function cerrarTablaTP()
{
    $("#divTratamientosProductosReserva").hide();
}

function cerrarTablaP()
{
    $("#divProductoReserva").hide();
}

function setFormularioDetalleVisibleServicio(valor)
{
    if(valor)
    {
        $("#divServicioDetalle").show(); 
    }
    else
    {
        $("#divServicioDetalle").hide();
    }
}