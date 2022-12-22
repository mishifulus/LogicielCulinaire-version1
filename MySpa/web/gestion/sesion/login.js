/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validarUsuario()
{
    
    var u = document.getElementById("txtUsuario").value;
    var p = document.getElementById("txtContrasennia").value;
    
    if (u === "" || p === "" || (document.getElementById("rdoRolEmpleado").checked === false && document.getElementById("rdoRolCliente").checked === false))
    {
        Swal.fire("¡Atención!", "Ingresa tu usuario, contraseña y rol", "error");
    }
    else
    {
        var data = {"nU": u, "c" : p};
        
        if (document.getElementById("rdoRolEmpleado").checked === true)
        {
            $.ajax(
                {
                    url: "../../api/log/in",
                    type: "POST",
                    data: data,
                    async: true
                }).done (
                        function (data)
                {
                    if(data == null)
                    {
                        Swal.fire("Acceso denegado", "Datos de acceso incorrectos", "error");
                        reiniciarFormulario();
                    }
                    else if(data.error != null)
                    {
                        Swal.fire("Error", data.error, "error");
                        reiniciarFormulario();
                    }
                    else if (data.id != 0)
                    {
                        reiniciarFormulario();
                        var nombre = data.persona.nombre + " " + data.persona.apellidoP + 
                                " " + data.persona.apellidoM;
                        var numEmpleadoL = data.numEmpleado;
                        sessionStorage.setItem("nombre", nombre);
                        sessionStorage.setItem("numEmpleadoL", numEmpleadoL)
                        sessionStorage.setItem("token",data.usuario.token)
                        sessionStorage.setItem("idUsuario",data.usuario.id)
                        window.location = "../../menuPrincipal.html";
                    }
                });
        }
        else
        {
            $.ajax(
                {
                    url: "../../api/log/inC",
                    type: "POST",
                    data: data,
                    async: true
                }).done (
                        function (data)
                {
                    if(data == null)
                    {
                        Swal.fire("Acceso denegado", "Datos de acceso incorrectos", "error");
                        reiniciarFormulario();
                    }
                    else if(data.error != null)
                    {
                        Swal.fire("Error", data.error, "error");
                        reiniciarFormulario();
                    }
                    else if (data.id != 0)
                    {
                        reiniciarFormulario();
                        var nombre = data.persona.nombre + " " + data.persona.apellidoP + 
                                " " + data.persona.apellidoM;
                        var numeroUniL = data.numeroUni;
                        sessionStorage.setItem("nombre", nombre);
                        sessionStorage.setItem("token",data.usuario.token)
                        sessionStorage.setItem("idUsuario",data.usuario.id)
                        sessionStorage.setItem("numeroUniL", numeroUniL)
                        window.location = "../../menuPrincipalCliente.html";
                    }
                });
        }
        
        
    }
    
}

function out()
{
    var data = {"idU": sessionStorage.getItem("idUsuario")};
    
    $.ajax({
        url: "../MySpa/api/log/out",
        type: "POST",
        data: data,
        asyn: true
    }).done(
            function(data)
    {
        if(data.result != '')
        {
            sessionStorage.clear();
            window.location = "index.html";
        }
        else
            if(data.error != '')
        {
            Swal.fire("Cierre de sesión fallido", "Vuelve a intentarlo", "error")
        }
    });
}

function reiniciarFormulario()
{
    document.getElementById("txtUsuario").value = "";
    document.getElementById("txtContrasennia").value = "";
    document.getElementById("rdoRolEmpleado").checked = false;
    document.getElementById("rdoRolCliente").checked = false;
}

