var clientes;

function inicializarModuloCliente()
{
    $("#divDetalleCliente").hide();
    refrescarTablaCliente();
}

function setFormularioDetalleVisible(valor)
{
    if(valor)
    {
        $("#divTablaClientes").removeClass("col-12");
        $("#divTablaClientes").addClass("col-8");
        $("#divDetalleCliente").show();
    }
    else
    {
        $("#divDetalleCliente").hide();
        $("#divTablaClientes").removeClass("col-8");
        $("#divTablaClientes").addClass("col-12");
    }
}

function guardarCliente()
{
    if ( $('#txtRFC').val() === "" | $('#txtCorreo').val() === "" | $('#txtNombre').val() === "" | $('#txtApellidoP').val() === "" | $('#txtApellidoM').val() === "" | $('#txtTelefono').val() === "" | $('#txtDomicilio').val() === "" | $('#txtUsuario').val() === "" | $('#txtContrasenia').val() === "" | $('#txtConfirmContrasenia').val() === "")
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
        var cliente;
        var persona;
        var usuario;
        var data;

        //Generamos los atributos del objeto usuario y armamos el json
        var idU = 0;
        var nU = sanitizar($("#txtUsuario").val());
        var contra = sanitizar($("#txtContrasenia").val());
        var rol = "CLIENTE";
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
        
        //Generar el objeto de cliente
        var idC = 0;
        var numeroUniC = $("#txtNumeroUni").val();
        var correoC = limpiarCorreo($("#txtCorreo").val());
        var estatusC = parseInt(1);
        var personaC = persona;
        var usuarioC = usuario;
        cliente = {"id": idC, "numeroUni": numeroUniC, "correo": correoC, "estatus": estatusC, "persona": personaC, "usuario": usuarioC};
        
        
        if(cliente.numeroUni.length > 0)
        {
            //MODIFICACIÓN
            //Id generados por el proceso
            cliente.id = parseInt($("#txtCodigoCliente").val);
            cliente.persona.id = parseInt($("#txtCodigoPersona").val());
            cliente.usuario.id = parseInt($("#txtCodigoUsuario").val());
            
            data = {"cliente": JSON.stringify(cliente), "t":sessionStorage.getItem("token")};
            
            $.ajax(
                    {
                        "url": "api/cliente/update",
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
                            refrescarTablaCliente();
                            limpiarFormulario();
                        }
                        else if (data.error !== null)
                        {
                            Swal.fire("Error", data.error, "error");
                            refrescarTablaInactivos();
                            refrescarTablaCliente();
                            limpiarFormulario();
                        }
                    }
               );
            
        }
        else
        {
            //INSERCIÓN
            data = {"cliente": JSON.stringify(cliente), "t":sessionStorage.getItem("token")};
            $.ajax(
                    {
                        "url": "api/cliente/insert",
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
                            refrescarTablaCliente();
                            limpiarFormulario();
                        }
                        else if (data.error !== null)
                        {
                            Swal.fire("Error", data.error, "error");
                            refrescarTablaCliente();
                            limpiarFormulario();
                        }
                    }
               );
        }
    }
}

function eliminarCliente(i)
{
    var data = {"id": clientes[i].id, "t":sessionStorage.getItem("token")};
    $.ajax(
            {
                "url": "api/cliente/delete",
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
            refrescarTablaCliente();
            limpiarFormulario();
        }
    );
}

function limpiarFormulario()
{
    $('#txtCodigoCliente').val('');
    $('#txtCodigoPersona').val('');
    $('#txtCodigoUsuario').val('');
    $('#txtFiltro').val('');
    $('#txtNumeroUni').val('');
    $('#txtNombre').val('');
    $('#txtApellidoP').val('');
    $('#txtApellidoM').val('');
    $('#rdoGeneroFemenino').prop("checked", false);
    $('#rdoGeneroMasculino').prop("checked", false);
    $('#rdoGeneroOtro').prop("checked", false);
    $('#txtRFC').val('');
    $('#txtTelefono').val('');
    $('#txtCorreo').val('');
    $('#txtDomicilio').val('');
    $('#txtUsuario').val('');
    $('#txtRol').val('');
    $('#txtContrasenia').val('');
    $('#txtConfirmContrasenia').val('');
    $('#txtEstatus').val('');
}

function refrescarTablaCliente()
{
    clientes = null;
    
    var data = {"estatus":1, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/cliente/getAll",
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
                    clientes = data;
                    var contenido = "";
                    
                    //recorremos el arreglo de clientes
                    for (var i = 0; i < clientes.length; i++)
                    {
                        var nombreCompleto = clientes[i].persona.nombre + " " + clientes[i].persona.apellidoP + " " + clientes[i].persona.apellidoM;
                        
                        contenido += "<tr>";
                        contenido += "<td>" + nombreCompleto + "</td>";
                        contenido += "<td>" + clientes[i].persona.telefono + "</td>";
                        contenido += "<td>" + clientes[i].numeroUni + "</td>";
                        contenido += "<td>" + clientes[i].correo + "</td>";
                        contenido += "<td>" + clientes[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarCliente(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleCliente(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyClientes').html(contenido);
                }
            }
    );
    
}

function refrescarTablaInactivos()
{
    setFormularioDetalleVisible(false);
    clientes = null;
    
    var data = {"estatus":0, "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/cliente/getAll",
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
                    clientes = data;
                    var contenido = "";
                    
                    //recorremos el arreglo de clientes
                    for (var i = 0; i < clientes.length; i++)
                    {
                        var nombreCompleto = clientes[i].persona.nombre + " " + clientes[i].persona.apellidoP + " " + clientes[i].persona.apellidoM;
                        
                        contenido += "<tr>";
                        contenido += "<td>" + nombreCompleto + "</td>";
                        contenido += "<td>" + clientes[i].persona.telefono + "</td>";
                        contenido += "<td>" + clientes[i].numeroUni + "</td>";
                        contenido += "<td>" + clientes[i].correo + "</td>";
                        contenido += "<td>" + clientes[i].estatus + "</td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyClientes').html(contenido);
                }
            }
    );
    
}

function mostrarDetalleCliente(i)
{
    setFormularioDetalleVisible(true);
    
    //Llenamos el formulario con los datos del cliente
    $('#txtCodigoCliente').val(clientes[i].id);
    $('#txtCodigoPersona').val(clientes[i].persona.id);
    $('#txtCodigoUsuario').val(clientes[i].usuario.id);
    $('#txtNumeroUni').val(clientes[i].numeroUni);
    $('#txtNombre').val(clientes[i].persona.nombre);
    $('#txtApellidoP').val(clientes[i].persona.apellidoP);
    $('#txtApellidoM').val(clientes[i].persona.apellidoM);
    $('#txtRFC').val(clientes[i].persona.rfc);
    $('#txtTelefono').val(clientes[i].persona.telefono);
    $('#txtCorreo').val(clientes[i].correo);
    $('#txtDomicilio').val(clientes[i].persona.domicilio);
    $('#txtUsuario').val(clientes[i].usuario.nombreUsu);
    $('#txtRol').val(clientes[i].usuario.rol);
    $('#txtContrasenia').val(clientes[i].usuario.contrasenia);
    $('#txtConfirmContrasenia').val(clientes[i].usuario.contrasenia);
    $('#txtEstatus').val(clientes[i].estatus);
    
    if (clientes[i].persona.genero === "F")
    {
        $('#rdoGeneroFememino').prop("checked", true);
    }
    else
        if (clientes[i].persona.genero === "M")
    {
        $('#rdoGeneroMasculino').prop("checked", true);
    }
    else
    {
        $('#rdoGeneroOtro').prop("checked", true);
    }
}

function buscarCliente()
{
    var filtro = limpiarTexto(document.getElementById("txtFiltro").value);
    
    var data = {"filter": "" + filtro + "", "t":sessionStorage.getItem("token")};
    
    $.ajax(
            {
                "url": "api/cliente/search",
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
                    clientes = data;
                    var contenido = "";
                    
                    //recorremos el arreglo de emepleados
                    for (var i = 0; i < clientes.length; i++)
                    {
                        var nombreCompleto = clientes[i].persona.nombre + " " + clientes[i].persona.apellidoP + " " + clientes[i].persona.apellidoM;
                        
                        contenido += "<tr>";
                        contenido += "<td>" + nombreCompleto + "</td>";
                        contenido += "<td>" + clientes[i].persona.telefono + "</td>";
                        contenido += "<td>" + clientes[i].numeroUni + "</td>";
                        contenido += "<td>" + clientes[i].correo + "</td>";
                        contenido += "<td>" + clientes[i].estatus + "</td>";
                        contenido += "<td> <button class='btn btn-outline-danger' onclick='eliminarCliente(" + i + ");'><i class='fa fa-trash'></i></button> </td>";
                        contenido += "<td> <button class='btn btn-outline-primary' onclick='mostrarDetalleCliente(" + i + ")'><i class='fa fa-pencil-alt'></i></button> </td>";
                        contenido += "</tr>";
                    }
                    $('#tbodyClientes').html(contenido);
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

function limpiarCorreo(texto)
{
    texto = sanitizarTexto(texto);
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
