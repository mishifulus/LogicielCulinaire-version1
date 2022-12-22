
var productos;

function inicializarModuloProducto(){
    $("#divDetalleProducto").hide();
    refrescarTablaProducto();
}

function setFormularioDetalleVisible(valor){
    if(valor)
    {
        $("#divTablaProductos").removeClass("col-12");
        $("#divTablaProductos").addClass("col-8");
        $("#divDetalleProducto").show();
    }
    else
    {
        $("#divDetalleProducto").hide();
        $("#divTablaProductos").removeClass("col-8");
        $("#divTablaProductos").addClass("col-12");
    }
}

function limpiarFormulario(){
    $('#txtIdProducto').val('');
    $('#txtNombre').val('');
    $('#txtMarca').val('');
    $('#txtPrecioUso').val('');
    $('#txtEstatus').val('');
    $('#txtFiltro').val('');
}
function guardarProducto(){
    //var pos = -1;
    //Generamos un nuevo objeto:
    //var producto= new Object();
    
    if ($('#txtNombre').val() === "" | $('#txtMarca').val() === "" | $('#txtPrecioUso').val() === "")
    {
        Swal.fire(
                '¡Movimiento no realizado!',
                'Existen campos vacíos',
                'warning'
            );
    }
    else
    {
        //Definimos las propiedades del objeto y su valores
        var idB = parseInt($('#txtIdProducto').val());
        var nombreB = limpiarTexto($('#txtNombre').val());
        var marcaB = limpiarTexto($('#txtMarca').val());
        var precioUsoB = limpiarNumeros($('#txtPrecioUso').val());
        var estatusB = 1;
    
        var pro = {
                   "id": idB,
                   "nombre": nombreB,
                   "marca": marcaB,
                   "precioUso": precioUsoB,
                   "estatus": estatusB
                    };
        
        //Para volver el objeto una cadena String
        var data = {"p": JSON.stringify(pro), "t":sessionStorage.getItem("token")};
    
        if (idB > 0) //Es un id valido
        {
            //Modificación
            $.ajax(
                    {
                        "url": "api/producto/update",
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
                        refrescarTablaProducto();
                        limpiarFormulario();
                    }   
            );
        }
        else
        {
            //Inserción
            $.ajax(
                    {
                        "url": "api/producto/insert",
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
                        refrescarTablaProducto();
                        limpiarFormulario();
                    }   
            );
        }
    }
}

function eliminarProducto(i)
{
    var data = {"id": productos[i].id, "t":sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/producto/delete",
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
            refrescarTablaProducto();
            limpiarFormulario();
        }
    );
}



function refrescarTablaProducto()
{
    var data = {"estatus":1, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/producto/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                productos = data;
                var contenido = "";
                for (var i = 0; i < productos.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + productos[i].id + "</td>";
                    contenido += "<td>" + productos[i].nombre + "</td>";
                    contenido += "<td>" + productos[i].marca + "</td>";
                    contenido += "<td>" + productos[i].precioUso + "</td>";
                    contenido += "<td>" + productos[i].estatus + "</td>";
                    contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarProducto("+i+");'><i class='fa fa-trash'></i></button> </td>";
                    contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleProducto("+i+")'><i class='fa fa-pencil-alt'></i></button> </td>";
                    contenido += "</tr>";
                }
                $('#tbodyProductos').html(contenido);
            }
    );
}

function refrescarTablaInactivos()
{
    setFormularioDetalleVisible(false);
    var data = {"estatus":0, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/producto/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                productos = data;
                var contenido = "";
                for (var i = 0; i < productos.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + productos[i].id + "</td>";
                    contenido += "<td>" + productos[i].nombre + "</td>";
                    contenido += "<td>" + productos[i].marca + "</td>";
                    contenido += "<td>" + productos[i].precioUso + "</td>";
                    contenido += "<td>" + productos[i].estatus + "</td>";
                    contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleProducto("+i+")'><i class='fa fa-pencil-alt'></i></button> </td>";
                    contenido += "</tr>";
                }
                $('#tbodyProductos').html(contenido);
            }
    );
}

function mostrarDetalleProducto(i)
{
    setFormularioDetalleVisible(true);
    
    $('#txtIdProducto').val(productos[i].id);
    $('#txtNombre').val(productos[i].nombre);
    $('#txtMarca').val(productos[i].marca);
    $('#txtPrecioUso').val(productos[i].precioUso);
    
    if (productos[i].estatus === 1)
    {
        $('#txtEstatus').val('Activo');
    }
    else
    {
        $('#txtEstatus').val('Inactivo');
    }
}

function buscarProducto()
{
    var filtro = limpiarTexto($('#txtFiltro').val());
    
    var data = {"filter": "" + filtro + "", "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/producto/search",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if (data !== null)
                {
                    productos = data;
                    var contenido = "";
                    for (var i = 0; i < productos.length; i++)
                    {
                        contenido += "<tr>";
                        contenido += "<td>" + productos[i].id + "</td>";
                        contenido += "<td>" + productos[i].nombre + "</td>";
                        contenido += "<td>" + productos[i].marca + "</td>";
                        contenido += "<td>" + productos[i].precioUso + "</td>";
                        contenido += "<td>" + productos[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarProducto(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleProducto(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyProductos').html(contenido);
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

function agregarNuevo()
{
    setFormularioDetalleVisible(true);
    limpiarFormulario();
}

function validarNumeros(event)
{
    if(event.charCode >= 48 && event.charCode <= 57 || event.charCode === 46)
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
    texto = texto.replaceAll("-","");
    texto = texto.replaceAll("'","");
    texto = texto.replaceAll(":","");
    texto = texto.replaceAll("/","");
    return texto;
}

function redondear(numero)
{
    // Cambio
    numero = parseFloat(numero);
    numero = numero.toFixed(2);
    return numero;
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
    numero = redondear(numero);
    return numero;
}
