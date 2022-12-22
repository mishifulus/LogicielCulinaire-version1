
var salas;

function inicializarModuloSala()
{
    $("#divDetalleSala").hide();
    cargarSucursalLista();
    refrescarTablaSala();
}
/*
 * Esta función muestra y oculta el formulario del detalle,
 * dependiendo del valor
 * Si el valor es true, el formulario de detalle se muestra
 * Si el valor es false, el formulario de detalle se oculta
 */


function setFormularioDetalleVisible(valor)
{
    if(valor)
    {
        $("#divTablaSalas").removeClass("col-12");
        $("#divTablaSalas").addClass("col-8");
        $("#divDetalleSala").show();
    }
    else
    {
        $("#divDetalleSala").hide();
        $("#divTablaSalas").removeClass("col-8");
        $("#divTablaSalas").addClass("col-12");
    }
}

function cargarSucursalLista()
{
    var data = {"estatus": 1, "t":sessionStorage.getItem("token")};
    
    $.ajax({
        "url": "api/sucursal/getAll",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        sucursales = data;
        var datosListaS = "";

        for (var i = 0; i < sucursales.length; i++) {
            datosListaS += "<select>";
            datosListaS += "<option value='" + sucursales[i].id + "'>" + sucursales[i].nombre + "</option>";
            datosListaS += "</select>";
        }
        $("#listaSucursales").html(datosListaS);
    });
}

function guardarSala()
{
    if ( $('#txtNombre').val() === "" | $('#txtDescripcion').val() === "" | $('#txtRutaFoto').val() === "")
    {
        Swal.fire(
                '¡Movimiento no realizado!',
                'Existen campos vacíos',
                'warning'
            );
    }
    else
    {
        var sala;
        var sucursal;
        var data;

        var sucursalS = document.getElementById("listaSucursales").value;
        sucursal = {"id": sucursalS};
        
        var idS = parseInt($("#txtCodigo").val());
        var nombre = limpiarTexto($("#txtNombre").val());
        var descripcion = limpiarTexto($("#txtDescripcion").val());
        var foto = $("#txtBase64").val();
        var rutaFoto = $("#txtRutaFoto").val();
        var estatus = parseInt(1);
        sala = {"id": idS, "nombre": nombre, "descripcion": descripcion, "foto": foto, "rutaFoto": rutaFoto, "estatus": estatus, "sucursal": sucursal};

        if (idS > 0) {

            data = {"sala": JSON.stringify(sala), "t":sessionStorage.getItem("token")};
            $.ajax({
                "url": "api/sala/update",
                "type": "POST",
                "async": true,
                "data": data
            }).done(function (data) {
                if (data.result !== null)
                {
                    Swal.fire("Modificación exitosa!!", data.result, "success");
                    refrescarTablaSala();
                    limpiarFormulario();
                } else if (data.error !== null)
                {
                    Swal.fire("Error en la modificación", data.error, "error");
                    refrescarTablaSala();
                    limpiarFormulario();
                }
            });
        } else {
            data = {"sala": JSON.stringify(sala), "t":sessionStorage.getItem("token")};

            $.ajax({
                "url": "api/sala/insert",
                "type": "POST",
                "async": true,
                "data": data
            }).done(function (data) {
                if (data.idGenerado !== null)
                {
                    Swal.fire("Inserción exitosa!!", data.result, "success");
                    refrescarTablaSala();
                    limpiarFormulario();
                } else if (data.error !== null)
                {
                    Swal.fire("Inserción fallida", data.error, "error");
                    refrescarTablaSala();
                    limpiarFormulario();
                }
            });
        }
    }
}

function limpiarFormulario()
{
    $("#txtNombre").val('');
    $("#txtDescripcion").val('');
    $("#txtEstatus").val('');
    var imagen = document.getElementById("imgFoto");
    imagen.src = "http://localhost:8084/MySpa/media/img/predeterminado.jpg";
    $('#txtRutaFoto').val('');
    $('#txtBase64').val('');
    $('#txtFoto').val('');
    $('#txtCodigo').val('');
    $('#txtFiltro').val('');
    document.getElementById("listaSucursales").value = "";
}

function cargarFoto()
{
    //Obtenemos los objetos de la interfaz
    var archivoSeleccionado = document.getElementById("txtFoto");
    var imagen = document.getElementById("imgFoto");
    var base64 = document.getElementById("txtBase64");
    
    //Comprobar que con el choses elegimos algo
    if (archivoSeleccionado.files.length > 0)
    {
        //Creamos un objeto lector de archivos
        var fr = new FileReader();
        
        //Funciones lamda o autoinvocadas
        fr.onload = function()
        {
            //Para ver la imagen
            imagen.src = "";
            imagen.src = fr.result;
            
            //Para ver lo que obtiener la dirección
            base64.value = "";
            //Hacemos un pibote para quitar el encabezado que no necesitamos en la base de datos
            base64.value = imagen.src.replace(/^data:image\/(png|jpeg|jpg);base64,/,"");
        };
        
        fr.readAsDataURL(archivoSeleccionado.files[0]);
        document.getElementById("txtRutaFoto").value = archivoSeleccionado.value;
    }
}

function refrescarTablaSala()
{
    var data = {"estatus": 1, "t":sessionStorage.getItem("token")};
    
    $.ajax({
        "url": "api/sala/getAll",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        salas = data;
        var contenido = "";

        for (var i = 0; i < salas.length; i++) {
            var srcSala = "data:image/jpeg;base64,";
            srcSala += salas[i].foto;
            
            contenido += "<tr>";
            contenido += "<td>" + salas[i].id + "</td>";
            contenido += "<td>" + salas[i].nombre + "</td>";
            contenido += "<td>" + salas[i].descripcion + "</td>";
            contenido += "<td>" + salas[i].sucursal.nombre + "</td>";
            contenido += "<td> <img id='imgTabla' alt='Imagen' height='50' width='60' src ='" + srcSala + "'> </td>";
            contenido += "<td>" + salas[i].estatus + "</td>";
            contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarSala(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
            contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleSala(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
            contenido += "</tr>";
        }
        $("#tbodySalas").html(contenido);
    });
}

function refrescarTablaInactivos()
{
    setFormularioDetalleVisible(false);
    
    var data = {"estatus": 0, "t":sessionStorage.getItem("token")};
    
    $.ajax({
        "url": "api/sala/getAll",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        salas = data;
        var contenido = "";

        for (var i = 0; i < salas.length; i++) {
            var srcSala = "data:image/jpeg;base64,";
            srcSala += salas[i].foto;
            
            contenido += "<tr>";
            contenido += "<td>" + salas[i].id + "</td>";
            contenido += "<td>" + salas[i].nombre + "</td>";
            contenido += "<td>" + salas[i].descripcion + "</td>";
            contenido += "<td>" + salas[i].sucursal.nombre + "</td>";
            contenido += "<td> <img id='imgTabla' alt='Imagen' height='50' width='60' src ='" + srcSala + "'> </td>";
            contenido += "<td>" + salas[i].estatus + "</td>";
            contenido += "</tr>";
        }
        $("#tbodySalas").html(contenido);
    });
}

function eliminarSala(i)
{
    var data = {"id": salas[i].id, "t":sessionStorage.getItem("token")};

    $.ajax({
        "url": "api/sala/delete",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.result !== null) {
            Swal.fire("Eliminación exitosa", data.result, "success");
        } else if (data.error !== null) {
            Swal.fire("Error en la eliminación", data.error, "error");
        }
        refrescarTablaSala();
    });
}

function mostrarDetalleSala(i)
{
    setFormularioDetalleVisible(true);
    
     $("#txtCodigo").val(salas[i].id);
    $("#txtNombre").val(salas[i].nombre);
    $("#txtDescripcion").val(salas[i].descripcion);
    $("#txtRutaFoto").val(salas[i].rutaFoto);
    $("#txtBase64").val(salas[i].foto);
    var src = "data:image/jpeg;base64,";
    src += salas[i].foto;
    var newImage = document.getElementById("imgFoto");
    newImage.src = src;
    $("#listaSucursales").val(salas[i].sucursal.id);
    
    if (salas[i].estatus === 1)
    {
        $('#txtEstatus').val('Activo');
    }
    else
    {
        $('#txtEstatus').val('Inactivo');
    }
    
}

function buscarSala()
{
    var data = {"filter": limpiarTexto(document.getElementById("txtFiltro").value), "t":sessionStorage.getItem("token")};

    $.ajax({
        "url": "api/sala/search",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data !== null) {
            
        salas = data;
        var contenido = "";

        for (var i = 0; i < salas.length; i++) {
                var srcSala = "data:image/jpeg;base64,";
                srcSala += salas[i].foto;

                contenido += "<tr>";
                contenido += "<td>" + salas[i].id + "</td>";
                contenido += "<td>" + salas[i].nombre + "</td>";
                contenido += "<td>" + salas[i].descripcion + "</td>";
                contenido += "<td>" + salas[i].sucursal.nombre + "</td>";
                contenido += "<td> <img id='imgTabla' alt='Imagen' height='50' width='45' src ='" + srcSala + "'> </td>";
                contenido += "<td>" + salas[i].estatus + "</td>";
                contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarSala(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleSala(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                contenido += "</tr>";
            }
            $("#tbodySalas").html(contenido);

        } else if (data.error !== null)
        {
            Swal.fire({
                icon: 'error',
                title: 'Algo salio mal',
                text: data.error,
                showConfirmButton: false,
                timer: 2500
            });
        }
        if (JSON.stringify(data) === "[]")
        {
            Swal.fire({
                icon: 'error',
                title: '¡Lo sentimos!\nNo se encontrarón resultados para tu búsqueda',
                showConfirmButton: false,
                timer: 2500
            });
        }
    });
}

function agregarNuevo()
{
    setFormularioDetalleVisible(true);
    limpiarFormulario();
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
