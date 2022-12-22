var empleados;

function inicializarModuloEmpleado()
{
    $("#divDetalleEmpleado").hide();
    refrescarTablaEmpleado();
}

function setFormularioDetalleVisible(valor)
{
    if(valor)
    {
        $("#divTablaEmpleados").removeClass("col-12");
        $("#divTablaEmpleados").addClass("col-8");
        $("#divDetalleEmpleado").show(); 
    }
    else
    {
        $("#divDetalleEmpleado").hide();
        $("#divTablaEmpleados").removeClass("col-8");
        $("#divTablaEmpleados").addClass("col-12");
    }
}

function guardarEmpleado()
{
    
    if ( $('#txtrfc').val() === "" | $('#txtPuesto').val() === "" | $('#txtNombre').val() === "" | $('#txtApellidoP').val() === "" | $('#txtApellidoM').val() === "" | $('#txtTelefono').val() === "" | $('#txtRutaFoto').val() === "" | $('#txtDomicilio').val() === "" | $('#txtUsuario').val() === "" | $('#txtContrasenia').val() === "" | $('#txtConfirmContrasenia').val() === "")
    {
        Swal.fire(
                '¡Movimiento no realizado!',
                'Existen campos vacíos',
                'warning'
            );
    }
    else if ($('#rdoGeneroFemenino').prop("checked") === false && $('#rdoGeneroMaculino').prop("checked") === false && $('#rdoGeneroOtro').prop("checked") === false)
    {
        Swal.fire(
                '¡Movimiento no realizado!',
                'Existen campos vacíos',
                'warning'
            );
    }
    else if ($('#txtContrasenia').val() !== $('#txtConfirmContrasenia').val())
    {
        Swal.fire(
                '¡Movimiento no realizado!',
                'Confirmación de contraseña no coincidente',
                'error'
            );
    }
    else
    {
        var empleado;
        var persona;
        var usuario;
        var data;

        //Generamos los atributos del objeto usuario y armamos el json
        var idU = 0;
        var nU = sanitizar($("#txtUsuario").val());
        var contra = sanitizar($("#txtContrasenia").val());
        var rol = "EMPLEADO";
        usuario = {"id":idU, "nombreUsu":nU, "contrasenia":contra, "rol":rol};
        
        //Se genera el objeto de persona, con sus atributos y valores
        var idP = 0;
        var nombreP = limpiarTexto($("#txtNombre").val());
        var apellidoPe = limpiarTexto($("#txtApellidoP").val());
        var apellidoMe = limpiarTexto($("#txtApellidoM").val());
        var gen = "";
        if ($('#rdoGeneroFemenino').prop("checked") === true)
        {
            gen = "F";
        }
        else
            if ($('#rdoGeneroMasculino').prop("checked") === true)
        {
            gen = "M";
        }
        else
        {
            gen = "O";
        }
        var domicilioP = limpiarTexto($("#txtDomicilio").val());
        var telefonoP = limpiarNumeros($("#txtTelefono").val());
        var rfcP = limpiarTexto($("#txtRFC").val());
        persona = {"id":idP, "nombre":nombreP, "apellidoP": apellidoPe, "apellidoM": apellidoMe, "genero": gen, "domicilio": domicilioP, "telefono": telefonoP, "rfc": rfcP};
        
        //Generar el objeto de empleado
        var idE = 0;
        var numEmpleadoE = $("#txtNumeroEmp").val(); //Input no editable
        var puestoE = limpiarTexto($("#txtPuesto").val());
        var estatusE = parseInt(1);
        var fotoE = $("#txtBase64").val();
        var rutaFotoE = $("#txtRutaFoto").val();
        var personaE = persona;
        var usuarioE = usuario;
        empleado = {"id": idE, "numEmpleado": numEmpleadoE, "puesto": puestoE, "estatus": estatusE, "foto": fotoE, "rutaFoto": rutaFotoE, "persona": personaE, "usuario": usuarioE};
        
        
        if(empleado.numEmpleado.length > 0)
        {
            //MODIFICACIÓN
            //Id generados por el proceso
            empleado.id = parseInt($("#txtCodigoEmpleado").val());
            empleado.persona.id = parseInt($("#txtCodigoPersona").val());
            empleado.usuario.id = parseInt($("#txtCodigoUsuario").val());
            
            data = {"empleado": JSON.stringify(empleado), "t":sessionStorage.getItem("token")};
            
            $.ajax(
                    {
                        "url": "api/empleado/update",
                        "type": "POST",
                        "async": true,
                        "data": data
                    }
            ).done(
                    function(data)
                    {
                        if(data.result !== null)
                        {
                            Swal.fire("Modificación exitosa", data.result,"success");
                            refrescarTablaInactivos();
                            refrescarTablaEmpleado();
                            limpiarFormulario();
                        }
                        else if (data.error !== null)
                        {
                            Swal.fire("Error", data.error, "error");
                            refrescarTablaInactivos();
                            refrescarTablaEmpleado();
                            limpiarFormulario();
                        }
                    }
               );
            
        }
        else
        {
            //INSERCIÓN
            data = {"empleado": JSON.stringify(empleado), "t":sessionStorage.getItem("token")};
            $.ajax(
                    {
                        "url": "api/empleado/insert",
                        "type": "POST",
                        "async": true,
                        "data": data
                    }
            ).done(
                    function(data)
                    {
                        if(data.idGenerado !== null)
                        {
                            Swal.fire("Inserción exitosa", data.result,"success");
                            refrescarTablaEmpleado();
                            limpiarFormulario();
                        }
                        else if (data.error !== null)
                        {
                            Swal.fire("Error", data.error, "error");
                            refrescarTablaEmpleado();
                            limpiarFormulario();
                        }
                    }
               );
        }
    }
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

function eliminarEmpleado(i)
{
    var data = {"id": empleados[i].id, "t":sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/empleado/delete",
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
            refrescarTablaEmpleado();
            limpiarFormulario();
        }
    );
}

function limpiarFormulario()
{
    $('#txtCodigoEmpleado').val('');
    $('#txtNumeroEmp').val('');
    $('#txtCodigoPersona').val('');
    $('#txtCodigoUsuario').val('');
    $('#txtNombre').val('');
    $('#txtApellidoP').val('');
    $('#txtApellidoM').val('');
    $('#rdoGeneroFememino').prop("checked", false);
    $('#rdoGeneroMasculino').prop("checked", false);
    $('#rdoGeneroOtro').prop("checked", false);
    $('#txtRFC').val('');
    $('#txtPuesto').val('');
    $('#txtTelefono').val('');
    $('#txtRutaFoto').val('');
    $('#txtFoto').val('');
    $('#txtBase64').val('');
    $('#txtDomicilio').val('');
    $('#txtUsuario').val('');
    $('#txtRol').val('');
    $('#txtContrasenia').val('');
    $('#txtConfirmContrasenia').val('');
    $('#txtEstatus').val('');
    $('#txtFiltro').val('');
    var imagen = document.getElementById("imgFoto");
    imagen.src = "http://localhost:8084/MySpa/media/img/empleado.png";
}

function refrescarTablaEmpleado()
{
    empleados = null;
    
    var data = {"e":1, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/empleado/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if(data.error != null)
                {
                    Swal.fire("Error",data.error, "error");
                }
                else
                {
                    empleados = data;
                    var contenido = "";
                    
                    //recorremos el arreglo de empleados
                    for (var i = 0; i < empleados.length; i++)
                    {
                        var nombreCompleto = empleados[i].persona.nombre + " " + empleados[i].persona.apellidoP + " " + empleados[i].persona.apellidoM;
                        var srcEmpleado = "data:image/jpeg;base64,";
                        srcEmpleado += empleados[i].foto;
                        
                        contenido += "<tr>";
                        contenido += "<td>" + nombreCompleto + "</td>";
                        contenido += "<td>" + empleados[i].persona.telefono + "</td>";
                        contenido += "<td>" + empleados[i].numEmpleado + "</td>";
                        contenido += "<td> <img id='imgTabla' alt='Imagen' height='50' width='45' src ='" + srcEmpleado + "'> </td>";
                        contenido += "<td>" + empleados[i].puesto + "</td>";
                        contenido += "<td>" + empleados[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarEmpleado(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleEmpleado(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyEmpleados').html(contenido);
                }
            }
    );
}

function refrescarTablaInactivos()
{
    setFormularioDetalleVisible(false);
    empleados = null;
    
    var data = {"e":0, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/empleado/getAll",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if(data.error != null)
                {
                    Swal.fire("Error",data.error, "error");
                }
                else
                {
                    empleados = data;
                    var contenido = "";
                    
                    //recorremos el arreglo de emepleados
                    for (var i = 0; i < empleados.length; i++)
                    {
                        var nombreCompleto = empleados[i].persona.nombre + " " + empleados[i].persona.apellidoP + " " + empleados[i].persona.apellidoM;
                        var srcEmpleado = "data:image/jpeg;base64,";
                        srcEmpleado += empleados[i].foto;
                        
                        contenido += "<tr>";
                        contenido += "<td>" + nombreCompleto + "</td>";
                        contenido += "<td>" + empleados[i].persona.telefono + "</td>";
                        contenido += "<td>" + empleados[i].numEmpleado + "</td>";
                        contenido += "<td> <img id='imgTabla' alt='Imagen' height='50' width='45' src ='" + srcEmpleado + "'> </td>";
                        contenido += "<td>" + empleados[i].puesto + "</td>";
                        contenido += "<td>" + empleados[i].estatus + "</td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyEmpleados').html(contenido);
                }
            }
    );
}

function mostrarDetalleEmpleado(i)
{
    setFormularioDetalleVisible(true);
    $('#txtCodigoEmpleado').val(empleados[i].id);
    $('#txtNumeroEmp').val(empleados[i].numEmpleado);
    $('#txtCodigoPersona').val(empleados[i].persona.id);
    $('#txtCodigoUsuario').val(empleados[i].usuario.id);
    $('#txtNombre').val(empleados[i].persona.nombre);
    $('#txtApellidoP').val(empleados[i].persona.apellidoP);
    $('#txtApellidoM').val(empleados[i].persona.apellidoM);
    $('#txtRFC').val(empleados[i].persona.rfc);
    $('#txtPuesto').val(empleados[i].puesto);
    $('#txtTelefono').val(empleados[i].persona.telefono);
    $('#txtRutaFoto').val(empleados[i].rutaFoto);
    $('#txtBase64').val(empleados[i].foto);
    $('#txtDomicilio').val(empleados[i].persona.domicilio);
    $('#txtUsuario').val(empleados[i].usuario.nombreUsu);
    $('#txtRol').val(empleados[i].usuario.rol);
    $('#txtContrasenia').val(empleados[i].usuario.contrasenia);
    $('#txtConfirmContrasenia').val(empleados[i].usuario.contrasenia);
    
    if (empleados[i].estatus === 1)
    {
        $('#txtEstatus').val('Activo');
    }
    else
    {
        $('#txtEstatus').val('Inactivo');
    }
    
    var src = "data:image/jpeg;base64,";
    src += empleados[i].foto;
    var newImage = document.getElementById("imgFoto");
    newImage.src = src;
    
    if (empleados[i].persona.genero === "F")
    {
        $('#rdoGeneroFememino').prop("checked", true);
    }
    else
        if (empleados[i].persona.genero === "M")
    {
        $('#rdoGeneroMasculino').prop("checked", true);
    }
    else
    {
        $('#rdoGeneroOtro').prop("checked", true);
    }
    
}

function buscarEmpleado()
{
    var filtro = limpiarTexto(document.getElementById("txtFiltro").value);
    
    var data = {"filter": "" + filtro + "", "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/empleado/search",
                "type": "GET",
                "async": true,
                "data": data
            }
    ).done(
            function(data)
            {
                if(data.error != null)
                {
                    Swal.fire("Error",data.error, "error");
                }
                else
                {
                    empleados = data;
                    var contenido = "";
                    
                    //recorremos el arreglo de emepleados
                    for (var i = 0; i < empleados.length; i++)
                    {
                        var nombreCompleto = empleados[i].persona.nombre + " " + empleados[i].persona.apellidoP + " " + empleados[i].persona.apellidoM;
                        var srcEmpleado = "data:image/jpeg;base64,";
                        srcEmpleado += empleados[i].foto;
                        
                        contenido += "<tr>";
                        contenido += "<td>" + nombreCompleto + "</td>";
                        contenido += "<td>" + empleados[i].persona.telefono + "</td>";
                        contenido += "<td>" + empleados[i].numEmpleado + "</td>";
                        contenido += "<td> <img id='imgTabla' alt='Imagen' height='50' width='45' src ='" + srcEmpleado + "'> </td>";
                        contenido += "<td>" + empleados[i].puesto + "</td>";
                        contenido += "<td>" + empleados[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarEmpleado(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleEmpleado(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyEmpleados').html(contenido);
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
    if(event.charCode >= 48 && event.charCode <= 57)
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