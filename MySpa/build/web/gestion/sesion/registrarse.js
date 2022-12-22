/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function registrarCliente()
{
    alert("Cliente registrado con éxito");
};

function reiniciarFormulario()
{
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtApP").value = "";
    document.getElementById("txtApM").value = "";
    document.getElementById("txtrfc").value = "";
    document.getElementById("txtEdad").value = 0;
    document.getElementById("txtTelefono").value = "";
    document.getElementById("txtCorreo").value = "";
    document.getElementById("txtUsuario").value = "";
    document.getElementById("txtContrasennia").value = "";
    document.getElementById("txtConfirmContrasennia").value = "";
    var sexoM = document.getElementById("rdoSexoM").checked = false;
    var sexoH = document.getElementById("rdoSexoH").checked = false;
    var sexoO = document.getElementById("rdoSexoO").checked = false;
}

