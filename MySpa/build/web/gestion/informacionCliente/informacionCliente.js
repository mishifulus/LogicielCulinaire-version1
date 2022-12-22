
function inicializarModuloClienteInfo()
{
    buscarCliente();
}

function buscarCliente()
{
    var filtro = document.getElementById("txtIdCliente").value;
    
    var data = {"filter": "" + filtro + ""};
    
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
                    cliente = data;
                    
                    //Llenamos el formulario con los datos del cliente
                    $('#txtCodigoCliente').val(cliente.id);
                    $('#txtCodigoPersona').val(cliente.persona.id);
                    $('#txtCodigoUsuario').val(cliente.usuario.id);
                    $('#txtNumeroUni').val(cliente.numeroUni);
                    $('#txtNombre').val(cliente.persona.nombre);
                    $('#txtApellidoP').val(cliente.persona.apellidoP);
                    $('#txtApellidoM').val(cliente.persona.apellidoM);
                    $('#txtRFC').val(cliente.persona.rfc);
                    $('#txtTelefono').val(cliente.persona.telefono);
                    $('#txtCorreo').val(cliente.correo);
                    $('#txtDomicilio').val(cliente.persona.domicilio);
                    $('#txtUsuario').val(cliente.usuario.nombreUsu);
                    $('#txtRol').val(cliente.usuario.rol);
                    $('#txtContrasenia').val(cliente.usuario.contrasenia);
                    $('#txtConfirmContrasenia').val(cliente.usuario.contrasenia);
                    $('#txtEstatus').val(cliente.estatus);

                    if (cliente.persona.genero === "F")
                    {
                        $('#rdoGeneroFememino').prop("checked", true);
                    } else
                    if (cliente.persona.genero === "M")
                    {
                        $('#rdoGeneroMasculino').prop("checked", true);
                    } else
                    {
                        $('#rdoGeneroOtro').prop("checked", true);
                    }
                }
            }
    );
}

function limpiarFormulario()
{
    $('#txtCodigoCliente').val('');
    $('#txtCodigoPersona').val('');
    $('#txtCodigoUsuario').val('');
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

function guardarCliente()
{
    if ( $('#txtRFC').val() === "" | $('#txtCorreo').val() === "" | $('#txtNombre').val() === "" | $('#txtApellidoP').val() === "" | $('#txtApellidoM').val() === "" | $('#txtTelefono').val() === "" | $('#txtNumeroUni').val() === "" | $('#txtDomicilio').val() === "" | $('#txtUsuario').val() === "" | $('#txtContrasenia').val() === "" | $('#txtConfirmContrasenia').val() === "")
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
        var nU = $("#txtUsuario").val();
        var contra = $("#txtContrasenia").val();
        var rol = $("#txtRol").val();
        usuario = {"id":idU, "nombreUsu":nU, "contrasenia":contra, "rol":rol};
        
        //Se genera el objeto de persona, con sus atributos y valores
        var idP = 0;
        var nombreP = $("#txtNombre").val();
        var apellidoPe = $("#txtApellidoP").val();
        var apellidoMe = $("#txtApellidoM").val();
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
        var domicilioP = $("#txtDomicilio").val();
        var telefonoP = $("#txtTelefono").val();
        var rfcP = $("#txtRFC").val();
        persona = {"id":idP, "nombre":nombreP, "apellidoP": apellidoPe, "apellidoM": apellidoMe, "genero": gen, "domicilio": domicilioP, "telefono": telefonoP, "rfc": rfcP};
        
        //Generar el objeto de cliente
        var idC = parseInt($("#txtCodigoCliente").val());
        var numeroUniC = $("#txtNumeroUni").val();
        var correoC = $("#txtCorreo").val();
        var estatusC = parseInt(1);
        var personaC = persona;
        var usuarioC = usuario;
        cliente = {"id": idC, "numeroUni": numeroUniC, "correo": correoC, "estatus": estatusC, "persona": personaC, "usuario": usuarioC};
        
            //MODIFICACIÓN
        //Id generados por el proceso
        cliente.persona.id = parseInt($("#txtCodigoPersona").val());
        cliente.usuario.id = parseInt($("#txtCodigoUsuario").val());

        data = {"cliente": JSON.stringify(cliente)};

        $.ajax(
                {
                    "url": "api/cliente/update",
                    "type": "POST",
                    "async": true,
                    "data": data
                }
        ).done(
                function (data)
                {
                    if (data.result !== null)
                    {
                        Swal.fire("Modificación exitosa", data.result, "success");
                        refrescarTablaInactivos();
                        refrescarTablaCliente();
                        limpiarFormulario();
                    } else if (data.error !== null)
                    {
                        Swal.fire("Error", data.error, "error");
                        refrescarTablaInactivos();
                        refrescarTablaCliente();
                        limpiarFormulario();
                    }
                }
        );
    }
}
