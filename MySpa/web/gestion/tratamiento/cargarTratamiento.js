
var tratamientos;

function inicializarModuloTratamiento(){
    $("#divDetalleTratamiento").hide();
    refrescarTablaTratamiento();
}

function setFormularioDetalleVisible(valor){
    if(valor)
    {
        $("#divTablaTratamientos").removeClass("col-12");
        $("#divTablaTratamientos").addClass("col-8");
        $("#divDetalleTratamiento").show();
    }
    else
    {
        $("#divDetalleTratamiento").hide();
        $("#divTablaTratamientos").removeClass("col-8");
        $("#divTablaTratamientos").addClass("col-12");
    }
}

function limpiarFormulario(){
    $('#txtIdTratamiento').val('');
    $('#txtNombre').val('');
    $('#txtDescripcion').val('');
    $('#txtCosto').val('');
    $('#txtEstatus').val('');
    $('#txtFiltro').val('');
}
function guardarTratamiento(){
    var pos = -1;
    //Generamos un nuevo objeto:
    var tratamiento = new Object();
    
    if ($('#txtNombre').val() === "" | $('#txtDescripcion').val() === "" | $('#txtCosto').val() === "")
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
        var idB = parseInt($('#txtIdTratamiento').val());
        var nombreB = limpiarTexto($('#txtNombre').val());
        var descripcionB = limpiarTexto($('#txtDescripcion').val());
        var costoB = limpiarNumeros($('#txtCosto').val());
        var estatusB = 1;
    
        var pro = {
                   "id": idB,
                   "nombre": nombreB,
                   "descripcion": descripcionB,
                   "costo": costoB,
                   "estatus": estatusB
                    };
        
        //Para volver el objeto una cadena String
        var data = {"t": JSON.stringify(pro), "tok": sessionStorage.getItem("token")};
    
        if (idB > 0) //Es un id valido
        {
            //Modificación
            $.ajax(
                    {
                        "url": "api/tratamiento/update",
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
                        refrescarTablaTratamiento();
                        limpiarFormulario();
                    }   
            );
        }
        else
        {
            //Inserción
            $.ajax(
                    {
                        "url": "api/tratamiento/insert",
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
                        refrescarTablaTratamiento();
                        limpiarFormulario();
                    }   
            );
        }
    }
}

function eliminarTratamiento(i)
{
    var data = {"id": tratamientos[i].id, "tok": sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/tratamiento/delete",
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
            refrescarTablaTratamiento();
            limpiarFormulario();
        }
    );
}



function refrescarTablaTratamiento()
{
    var data = {"estatus":1, "tok": sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/tratamiento/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                tratamientos = data;
                var contenido = "";
                for (var i = 0; i < tratamientos.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + tratamientos[i].id + "</td>";
                    contenido += "<td>" + tratamientos[i].nombre + "</td>";
                    contenido += "<td>" + tratamientos[i].descripcion + "</td>";
                    contenido += "<td>" + tratamientos[i].costo + "</td>";
                    contenido += "<td>" + tratamientos[i].estatus + "</td>";
                    contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarTratamiento("+i+");'><i class='fa fa-trash'></i></button> </td>";
                    contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleTratamiento("+i+")'><i class='fa fa-pencil-alt'></i></button> </td>";
                    contenido += "</tr>";
                }
                $('#tbodyTratamientos').html(contenido);
            }
    );
}

function refrescarTablaInactivos()
{
    setFormularioDetalleVisible(false);
    var data = {"estatus":0, "tok": sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/tratamiento/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                tratamientos = data;
                var contenido = "";
                for (var i = 0; i < tratamientos.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + tratamientos[i].id + "</td>";
                    contenido += "<td>" + tratamientos[i].nombre + "</td>";
                    contenido += "<td>" + tratamientos[i].descripcion + "</td>";
                    contenido += "<td>" + tratamientos[i].costo + "</td>";
                    contenido += "<td>" + tratamientos[i].estatus + "</td>";
                    contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleTratamiento("+i+")'><i class='fa fa-pencil-alt'></i></button> </td>";
                    contenido += "</tr>";
                }
                $('#tbodyTratamientos').html(contenido);
            }
    );
}

function mostrarDetalleTratamiento(i)
{
    setFormularioDetalleVisible(true);
    
    $('#txtIdTratamiento').val(tratamientos[i].id);
    $('#txtNombre').val(tratamientos[i].nombre);
    $('#txtDescripcion').val(tratamientos[i].descripcion);
    $('#txtCosto').val(tratamientos[i].costo);
    
    if (tratamientos[i].estatus === 1)
    {
        $('#txtEstatus').val('Activo');
    }
    else
    {
        $('#txtEstatus').val('Inactivo');
    }
}

function buscarTratamiento()
{
    var filtro = limpiarTexto($('#txtFiltro').val());;
    
    var data = {"filter": "" + filtro + "", "tok": sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/tratamiento/search",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if (data !== null)
                {
                    tratamientos = data;
                    var contenido = "";
                    for (var i = 0; i < tratamientos.length; i++)
                    {
                        contenido += "<tr>";
                        contenido += "<td>" + tratamientos[i].id + "</td>";
                        contenido += "<td>" + tratamientos[i].nombre + "</td>";
                        contenido += "<td>" + tratamientos[i].descripcion + "</td>";
                        contenido += "<td>" + tratamientos[i].costo + "</td>";
                        contenido += "<td>" + tratamientos[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarTratamiento(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleTratamiento(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyTratamientos').html(contenido);
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

function validarNumeros(event){
    
    if(event.charCode >= 48 && event.charCode <= 57 || event.charCode === 46)
    {
        return true;
    }
    return false;
}

function validarTexto(event){
    
    if(event.charCode >= 65 && event.charCode <= 90 || event.charCode === 32 || event.charCode >= 97 && event.charCode <= 122)
    {
        return true;
    }
    return false;
}

function normalizar(texto){
    
    texto = texto.toUpperCase();
    texto = texto.replaceAll("Á","A");
    texto = texto.replaceAll("É","E");
    texto = texto.replaceAll("Í","I");
    texto = texto.replaceAll("Ó","O");
    texto = texto.replaceAll("Ú","U");
    texto = texto.replaceAll(".","");
    
    return texto;
}

function sanitizar(texto){

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

function redondear(numero){
    // Switch
    numero = parseFloat(numero);
    numero = numero.toFixed(2);
    return numero;
}

function limpiarTexto(texto){
    
    texto = normalizar(texto);
    texto = sanitizar(texto);
    
    return texto;
}

function limppiarNumeros(numero){
    
    numero = sanitizar(numero);
    numero = redondear(numero);
    
    return numero;
}