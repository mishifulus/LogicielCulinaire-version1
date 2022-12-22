/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.utl.dsm.myspa.db;

import edu.utl.dsm.myspa.controller.ControllerCliente;
import edu.utl.dsm.myspa.controller.ControllerEmpleado;
import edu.utl.dsm.myspa.controller.ControllerHorario;
import edu.utl.dsm.myspa.controller.ControllerLogin;
import edu.utl.dsm.myspa.controller.ControllerProducto;
import edu.utl.dsm.myspa.controller.ControllerSucursal;
import edu.utl.dsm.myspa.controller.ControllerTratamiento;
import edu.utl.dsm.myspa.model.Cliente;
import edu.utl.dsm.myspa.model.Empleado;
import edu.utl.dsm.myspa.model.Horario;
import edu.utl.dsm.myspa.model.Persona;
import edu.utl.dsm.myspa.model.Producto;
import edu.utl.dsm.myspa.model.Sucursal;
import edu.utl.dsm.myspa.model.Tratamiento;
import edu.utl.dsm.myspa.model.Usuario;
import java.util.List;

/**
 *
 * @author marti
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //CONEXIÓN
        //probarConn();
        
        
        //SUCURSAL
        //probarInsert();
        //probarGetAll();
        //probarDelete();
        //probarSearch();
        //probarUpdate();
        
        
        //EMPLEADO
        //probarGAE();
        //probarIE();
        //probarME();
        //probarDE();
        //probarSE();
        //probarLogin();
        
        
        //CLIENTE
        //probarGAC();
        //probarIC();
        //probarMC();
        //probarDC();
        //probarSC();
        //probarLoginC();
        
        //PRODUCTO
        //probarGAP();
        //probarIP();
        //probarUP();
        //probarDP();
        //probarSP();
        
        //TRATAMIENTO
        //pIT();
        //pGAT();
        //pDT();
        
        //HORARIO
        //probarInsertH();
        probarGetAllH();
        //probarUpdateH();
        //probarSearchH();
        //probarDeleteH();
    }
    
    //SUCURSAL
    
    public static void probarConn()
    {
        ConexionMySQL objCon = new ConexionMySQL();
        try {
            objCon.open();
            System.out.println(objCon.toString());
            objCon.close();
        }catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public static void probarInsert()
    {
        try
        {
            Sucursal s = new Sucursal("Sur2", "Local 10 Plaza Punta Sur", 10.1, -101.20, 1);
            ControllerSucursal objCS = new ControllerSucursal();
            int idG = objCS.insert(s);
            System.out.println(idG);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void probarGetAll()
    {
        try
        {
            ControllerSucursal objCS = new ControllerSucursal();
            List<Sucursal> sucursales = objCS.getAll(1);
            for (int i = 0; i < sucursales.size(); i++)
            {
                System.out.println(sucursales.get(i).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarDelete()
    {
        try
        {
            ControllerSucursal objCS = new ControllerSucursal();
            objCS.delete(5);
            System.out.println("Eliminación exitosa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarUpdate()
    {
        try
        {
            ControllerSucursal objCS = new ControllerSucursal();
            Sucursal s = new Sucursal(11,"Sucursal Sur2", "Local 10 Plaza Punta Sur", 207.1, -101.20, 1);
            objCS.update(s);
            System.out.println("Actualización exitosa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarSearch()
    {
        try
        {
            ControllerSucursal objCS = new ControllerSucursal();
            List<Sucursal> sucursales = objCS.search("Sur");
            for (int i = 0; i < sucursales.size(); i++)
            {
                System.out.println(sucursales.get(i).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    //EMPLEADO
    
    public static void probarGAE()
    {
            try
            {
                ControllerEmpleado objCE = new ControllerEmpleado();
                List<Empleado> empleados = objCE.getAll(1);
                
                for (int i = 0; i < empleados.size(); i++)
                {
                    System.out.println(empleados.get(i).toString());
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
    }
    
    public static void probarIE()
    {
        try
        {
            ControllerEmpleado objCE = new ControllerEmpleado();
            
            Usuario u = new Usuario("Nombre", "Contras", "1", "123");
            Persona p = new Persona("Nom", "ApellidoP", "ApellidoM", "H", "Dom", "Tel", "RFC");
            
            Empleado e = new Empleado(0, "1234", "Almacen", 1, "", "", p, u);
            int idG = objCE.insert(e);
            System.out.println(idG);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarME()
    {
        try
        {
            ControllerEmpleado objCE = new ControllerEmpleado();
            
            Usuario u = new Usuario(134, "NombreM", "ContrasM", "Almacenista", "123");
            Persona p = new Persona(134, "NomM", "ApellidoPM", "ApellidoMM", "H", "DomM", "TelM", "RFCM");
            
            Empleado e = new Empleado(117, "123M", "AlmacenM", 1, "M", "M", p, u);
            objCE.update(e);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarDE()
    {
        try
        {
            ControllerEmpleado objCE = new ControllerEmpleado();
            objCE.delete(105);
            System.out.println("Eliminación exitosa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void probarSE()
    {
        try
        {
            ControllerEmpleado objCE = new ControllerEmpleado();
            List<Empleado> empleados = objCE.search("Gerente");
            for (int i = 0; i < empleados.size(); i++)
            {
                System.out.println(empleados.get(i).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarLogin()
    {
        try
        {
            ControllerLogin objCL = new ControllerLogin();
            Empleado e = objCL.login("admin", "admin");
            System.out.println(e.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    //CLIENTE
    
    public static void probarGAC()
    {
            try
            {
                ControllerCliente objCC = new ControllerCliente();
                List<Cliente> clientes = objCC.getAll(1);
                
                for (int i = 0; i < clientes.size(); i++)
                {
                    System.out.println(clientes.get(i).toString());
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
    }
    
    public static void probarIC()
    {
        try
        {
            ControllerCliente objCC = new ControllerCliente();
            
            Usuario u = new Usuario("NombreC", "ContrasC", "1", "123");
            Persona p = new Persona("NomC", "ApellidoPC", "ApellidoMC", "M", "DomC", "TelC", "RFCC");
            
            Cliente c = new Cliente("123","cliente@", 1, p, u);
            int idG = objCC.insert(c);
            System.out.println(idG);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarMC()
    {
        try
        {
            ControllerCliente objCC = new ControllerCliente();
            
            Usuario u = new Usuario(138, "NombreM", "ContrasM", "Almacenista", "123");
            Persona p = new Persona(142, "NomM", "ApellidoPM", "ApellidoMM", "M", "DomM", "TelM", "RFCM");
            
            Cliente c = new Cliente(18,"NANANA", "correos@", 1, p, u);
            objCC.update(c);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarDC()
    {
        try
        {
            ControllerCliente objCC = new ControllerCliente();
            objCC.delete(18);
            System.out.println("Eliminación exitosa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void probarSC()
    {
        try
        {
            ControllerCliente objCC = new ControllerCliente();
            List<Cliente> clientes = objCC.search("Diana");
            for (int i = 0; i < clientes.size(); i++)
            {
                System.out.println(clientes.get(i).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    //PRODUCTO
    public static void probarIP()
    {
        try
        {
            Producto p = new Producto("Esencia de Coco", "Esence", 1, (float) 87.80);
            ControllerProducto objCP = new ControllerProducto();
            int idG = objCP.insert(p);
            System.out.println(idG);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void probarGAP()
    {
        try
        {
            ControllerProducto objCP = new ControllerProducto();
            List<Producto> productos = objCP.getAll(1);
            for (int i = 0; i < productos.size(); i++)
            {
                System.out.println(productos.get(i).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarDP()
    {
        try
        {
            ControllerProducto objCP = new ControllerProducto();
            objCP.delete(2);
            System.out.println("Eliminación exitosa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarUP()
    {
        try
        {
            ControllerProducto objCP = new ControllerProducto();
            Producto p = new Producto( 397,"Esencia de Citricos", "Esence", 1, (float) 85.80);
            objCP.update(p);
            System.out.println("Actualización exitosa");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void probarSP()
    {
        try
        {
            ControllerProducto objCP = new ControllerProducto();
            List<Producto> productos = objCP.search("Esence");
            for (int i = 0; i < productos.size(); i++)
            {
                System.out.println(productos.get(i).toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public static void pIT(){
        try{

            Tratamiento t = new Tratamiento("nombre", "descripcion", 236, 1);
            ControllerTratamiento objCT = new ControllerTratamiento();
            int idG = objCT.insert(t);
            System.out.println(idG);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void pGAT(){
        try{
            ControllerTratamiento objCS = new ControllerTratamiento();
            // 1 - 0 : Indica el estatus activo/incativo
            List<Tratamiento> tratamientos = objCS.getAll(0);

            for(int i=0; i<tratamientos.size(); i++){
                System.out.println(tratamientos.get(i).toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void pDT(){
        try{
            ControllerTratamiento objCT = new ControllerTratamiento();
            objCT.delete(1);
            System.out.println("Eliminación exitosa");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void probarInsertH() {
        try {
            Horario h = new Horario("14:00:00", "14:30:00");
            ControllerHorario objCH = new ControllerHorario();
            int idG = objCH.insert(h);
            System.out.println(idG);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void probarGetAllH() {
        try {
            ControllerHorario objCH = new ControllerHorario();
            List<Horario> horarios = objCH.getAll();
            for (int i = 0; i < horarios.size(); i++) {
                System.out.println(horarios.get(i).toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void probarUpdateH() {
        try {
            Horario h = new Horario(1,"14:00:00", "15:00:00");
            ControllerHorario objCH = new ControllerHorario();
            objCH.update(h);
            System.out.println("Actulizacion exitosa");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void probarSearchH() {
        try {
            ControllerHorario objCH = new ControllerHorario();
            List<Horario> horarios = objCH.search("1");
            for (int i = 0; i < horarios.size(); i++) {
                System.out.println(horarios.get(i).toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void probarDeleteH() {
        try {
            ControllerHorario objCH = new ControllerHorario();
            objCH.delete(11);
            System.out.println("Eliminacion exitosa");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
