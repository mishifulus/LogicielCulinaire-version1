var horarios;

function inicializarModuloHorario(){
    $("#divDetalleHorario").hide();
    refrescarTablaHorario();
}

function setFormularioDetalleVisible(valor){
    if(valor)
    {
        $("#divTablaHorarios").removeClass("col-12");
        $("#divTablaHorarios").addClass("col-8");
        $("#divDetalleHorario").show();
    }
    else
    {
        $("#divDetalleHorario").hide();
        $("#divTablaHorarios").removeClass("col-8");
        $("#divTablaHorarios").addClass("col-12");
    }
}

function limpiarFormulario(){
    $('#txtIdHorario').val('');
    $('#txtHoraInicio').val('');
    $('#txtHoraFin').val('');
}
function guardarHorario(){
    var idH = parseInt(document.getElementById("txtIdHorario").value);
    var horaI = limpiarNumeros(document.getElementById("txtHoraInicio").value);
    var horaF = limpiarNumeros(document.getElementById("txtHoraFin").value);

    var hor = {
        "id": idH, "horaI": horaI, "horaF": horaF
    };
    var data = {"h": JSON.stringify(hor), "t":sessionStorage.getItem("token")};

    if (idH > 0)
    {
        //Modificacion
        $.ajax(
                {
                    "url": "api/horario/update",
                    "type": "GET",
                    "async": true,
                    "data": data
                }
        ).done(
                function (data)
                {
                    if (data.result != null)
                    {
                        Swal.fire("Modificacion exitosa", data.result, "success");
                    } else if (data.error != null)
                    {
                        Swal.fire("Error", data.error, "error");
                    }
                    refrescarTablaHorario();
                    limpiarFormulario()
                }
        );
    } else
    {
        //Inserccion
        $.ajax(
                {
                    "url": "api/horario/insert",
                    "type": "GET",
                    "async": true,
                    "data": data
                }
        ).done(
                function (data)
                {
                    if (data.idGenerado != null)
                    {
                        Swal.fire("Insercion exitosa", data.result, "success");
                    } else if (data.error != null)
                    {
                        Swal.fire("Error", data.error, "error");
                    }
                    refrescarTablaHorario();
                    limpiarFormulario()
                }
        );
    }
    refrescarTablaHorario();
    limpiarFormulario()
}

function eliminarHorario(i)
{
    var data = {"id": horarios[i].id, "t":sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/horario/delete",
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
            refrescarTablaHorario();
            limpiarFormulario();
        }
    );
}



function refrescarTablaHorario()
{
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/horario/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                horarios = data;
                var contenido = "";
                for (var i = 0; i < horarios.length; i++)
                {
                    contenido += "<tr>";
                    contenido += "<td>" + horarios[i].id + "</td>";
                    contenido += "<td>" + horarios[i].horaI + "</td>";
                    contenido += "<td>" + horarios[i].horaF + "</td>";
                    contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarHorario("+i+");'><i class='fa fa-trash'></i></button> </td>";
                    contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleHorario("+i+")'><i class='fa fa-pencil-alt'></i></button> </td>";
                    contenido += "</tr>";
                }
                $('#tbodyHorarios').html(contenido);
            }
    );
}

function mostrarDetalleHorario(i)
{
    setFormularioDetalleVisible(true);
    
    $('#txtIdHorario').val(horarios[i].id);
    $('#txtHoraInicio').val(horarios[i].horaI);
    $('#txtHoraFin').val(horarios[i].horaF);
}

function buscarProducto()
{
    var filtro = limpiarTexto($('#txtFiltro').val());
    
    var data = {"filter": "" + filtro + "", "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/horario/search",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if (data !== null)
                {
                    horarios = data;
                    var contenido = "";
                    for (var i = 0; i < horarios.length; i++)
                    {
                        contenido += "<tr>";
                        contenido += "<td>" + horarios[i].id + "</td>";
                        contenido += "<td>" + horarios[i].horaI + "</td>";
                        contenido += "<td>" + horarios[i].horaF + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarHorario(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleHorario(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
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
    if(event.charCode >= 48 && event.charCode <= 57 || event.charCode === 58)
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
    texto = texto.replaceAll("/","");
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



