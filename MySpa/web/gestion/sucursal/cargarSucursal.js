var sucursales;

function inicializarModuloSucursal()
{
    $("#divDetalleSucursal").hide();
    refrescarTablaSucursal();
}

function setFormularioDetalleVisible(valor)
{
    if(valor)
    {
        $("#divTablaSucursales").removeClass("col-12");
        $("#divTablaSucursales").addClass("col-8");
        $("#divDetalleSucursal").show();
    }
    else
    {
        $("#divDetalleSucursal").hide();
        $("#divTablaSucursales").removeClass("col-8");
        $("#divTablaSucursales").addClass("col-12");
    }
}

function guardarSucursal()
{
    if ($('#txtNombre').val() === "" | $('#txtLatitud').val() === "" | $('#txtLongitud').val() === "" | $('#txtDomicilio').val() === "")
    {
        Swal.fire(
                '¡Movimiento no realizado!',
                'Existen campos vacíos',
                'warning'
            );
    }
    else
    {
        var idB = parseInt($('#txtIdSucursal').val());
        var nombreB = limpiarTexto($('#txtNombre').val());
        var domicilioB = limpiarTexto($('#txtDomicilio').val());
        var latitudB = limpiarNumeros(parseFloat($('#txtLatitud').val()));
        var longitudB = limpiarNumeros(parseFloat($('#txtLongitud').val()));
        var estatusB = parseInt(1);
        
        var suc = {"id": idB,
                   "nombre": nombreB,
                   "domicilio": domicilioB,
                   "latitud": latitudB,
                   "longitud": longitudB,
                   "estatus": estatusB};
    
        //Para volver el objeto una cadena String
        var data = {"s": JSON.stringify(suc), "t":sessionStorage.getItem("token")};
        
        
        if (idB > 0) //Es un id valido
        {
            //Modificación
            $.ajax(
                    {
                        "url": "api/sucursal/update",
                        "type": "GET",
                        "async": true,
                        "data": data
                    }
            ).done(
                    
                    function(data)
                    {
                        if (data.result !== null)
                        {
                            //Se logra la modificación
                            Swal.fire("Modificación exitosa", data.result,"success");
                        }
                        else if (data.error !== null)
                        {
                            Swal.fire("Error", data.error, "error");
                        }
                        refrescarTablaInactivos();
                        refrescarTablaSucursal();
                        limpiarFormulario();
                    }   
            );
        }
        else
        {
            //Inserción
            $.ajax(
                    {
                        "url": "api/sucursal/insert",
                        "type": "GET",
                        "async": true,
                        "data": data
                    }
            ).done(
                    
                    function(data)
                    {
                        if (data.idGenerado !== null)
                        {
                            //Se logra la inserción
                            Swal.fire("Inserción exitosa", data.result,"success");
                        }
                        else if (data.error !== null)
                        {
                            Swal.fire("Error", data.error, "error");
                        }
                        refrescarTablaSucursal();
                        limpiarFormulario();
                    }   
            );
        }
    }
}

function eliminarSucursal(i)
{
    var data = {"id": sucursales[i].id, "t":sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/sucursal/delete",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
        {
            if(data.result !== null)
            {
                //Se logró la eliminación
                Swal.fire(
                '¡Movimiento realizado!',
                data.result,
                'success');
            }
            else if (data.error !== null)
            {
                //No se logró la eliminación
                Swal.fire("Error", data.error, "error");
            }
            refrescarTablaSucursal();
            limpiarFormulario();
        }
    );
}

function limpiarFormulario()
{
    $('#txtIdSucursal').val('');
    $('#txtNombre').val('');
    $('#txtDomicilio').val('');
    $('#txtLatitud').val('');
    $('#txtLongitud').val('');
    $('#txtEstatus').val('');
    $('#txtFiltro').val('');
}

function refrescarTablaSucursal()
{
    var data = {"estatus":1, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/sucursal/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                sucursales = data;
                var contenido = "";
                for (var i = 0; i < sucursales.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + sucursales[i].id + "</td>";
                    contenido += "<td>" + sucursales[i].nombre + "</td>";
                    contenido += "<td>" + sucursales[i].domicilio + "</td>";
                    contenido += "<td>" + sucursales[i].latitud + "</td>";
                    contenido += "<td>" + sucursales[i].longitud + "</td>";
                    contenido += "<td>" + sucursales[i].estatus + "</td>";
                    contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarSucursal("+i+");'><i class='fa fa-trash'></i></button> </td>";
                    contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleSucursal("+i+")'><i class='fa fa-pencil-alt'></i></button> </td>";
                    contenido += "</tr>";
                }
                $('#tbodySucursales').html(contenido);
            }
    );
}

function refrescarTablaInactivos()
{
    setFormularioDetalleVisible(false);
    var data = {"estatus":0, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/sucursal/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                sucursales = data;
                var contenido = "";
                for (var i = 0; i < sucursales.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + sucursales[i].id + "</td>";
                    contenido += "<td>" + sucursales[i].nombre + "</td>";
                    contenido += "<td>" + sucursales[i].domicilio + "</td>";
                    contenido += "<td>" + sucursales[i].latitud + "</td>";
                    contenido += "<td>" + sucursales[i].longitud + "</td>";
                    contenido += "<td>" + sucursales[i].estatus + "</td>";
                    contenido += "<td> <button class='btn btn-outline-danger' onclick='mostrarDetalleSucursal("+i+")'><i class='fa fa-ban'></i></button> </td>";
                   contenido += "</tr>";
                }
                $('#tbodySucursales').html(contenido);
            }
    );
    
}

function mostrarDetalleSucursal(i)
{
    setFormularioDetalleVisible(true);
    
    $('#txtIdSucursal').val(sucursales[i].id);
    $('#txtNombre').val(sucursales[i].nombre);
    $('#txtDomicilio').val(sucursales[i].domicilio);
    $('#txtLatitud').val(sucursales[i].latitud);
    $('#txtLongitud').val(sucursales[i].longitud);
    
    if (sucursales[i].estatus === 1)
    {
        $('#txtEstatus').val('Activo');
    }
    else
    {
        $('#txtEstatus').val('Inactivo');
    }
}

function agregarNuevo()
{
    setFormularioDetalleVisible(true);
    limpiarFormulario();
}

function buscarSucursal()
{
    var filtro = limpiarTexto($('#txtFiltro').val());
    
    var data = {"filter": "" + filtro + "", "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/sucursal/search",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if (data !== null)
                {
                    sucursales = data;
                    var contenido = "";
                    for (var i = 0; i < sucursales.length; i++)
                    {
                        contenido += "<tr>";
                        contenido += "<td>" + sucursales[i].id + "</td>";
                        contenido += "<td>" + sucursales[i].nombre + "</td>";
                        contenido += "<td>" + sucursales[i].domicilio + "</td>";
                        contenido += "<td>" + sucursales[i].latitud + "</td>";
                        contenido += "<td>" + sucursales[i].longitud + "</td>";
                        contenido += "<td>" + sucursales[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarSucursal(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleSucursal(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodySucursales').html(contenido);
                }
                else if (data.error !== null)
                {
                    //No se ncontró nada
                    Swal.fire("Error", data.error, "error");
                }
            }
    );
    $('#txtFiltro').val('');
}

function validarNumeros(event)
{
    if(event.charCode >= 48 && event.charCode <= 57 || event.charCode === 46 || event.charCode === 45)
    {
        return true;
    }
    return false;
}

function validarTextos(event)
{
    if(event.charCode >= 65 && event.charCode <= 90 || event.charCode === 32 || event.charCode >= 97 && event.charCode <= 122 || event.charCode === 46)
    {
        return true;
    }
    return false;
}

function normalizar(texto)
{
    texto = texto.toUpperCase();
    texto = texto.replaceAll("Á","A");
    texto = texto.replaceAll("Í","I");
    texto = texto.replaceAll("É","E");
    texto = texto.replaceAll("Ó","O");
    texto = texto.replaceAll("Ú","U");
    texto = texto.replaceAll(".","");
    
    return texto;
}

function sanitizar(texto)
{
    texto = texto.replaceAll("(","");
    texto = texto.replaceAll(")","");
    texto = texto.replaceAll("\"","");
    texto = texto.replaceAll("“","");
    texto = texto.replaceAll("”","");
    texto = texto.replaceAll(";","");
    texto = texto.replaceAll("'","");
    texto = texto.replaceAll(":","");
    return texto;
}

function limpiarTexto(texto)
{
    texto = normalizar(texto);
    texto = sanitizar(texto);
    return texto;
}

function limpiarNumeros(numero)
{
    numero = sanitizar(numero);
    return numero;
}