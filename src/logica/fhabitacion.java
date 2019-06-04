/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import datos.vhabitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jalvarezotero
 */
public class fhabitacion {
//instanciamos con  la clase conexion
    private conexion mysql = new conexion();
    private Connection cn = mysql.conectar();
   //variable donde se va a almacenar nuestra cadena de conexión
    
    private String sSQL = "";
    public Integer totalregistros;
// para mostrasr los registros de la tabla
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;
//para almacenar los titulos de la columna con array

        String[] titulos = {"ID", "Numero", "piso", "Descripcion", "Caracteristicas", "Precio", "Estado", "Tipo habitacion"};
     
       String[] registro = new String[8];

        totalregistros = 0;
        modelo = new DefaultTableModel(null, titulos);
      
        sSQL = "select * from habitacion where piso like '%" + buscar + "%' order by idhabitacion";
       
                try {
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sSQL);
                //bucle while para recorrer la toda la variable rs
                if(rs!=null){
                    System.out.println("consulta exitosa!");
                    while (rs.next()) {
                        //System.out.println("Parámetros: "+rs.getString("idhabitacion"));
                        registro[0] = rs.getString("idhabitacion");
                        registro[1] = rs.getString("numero");
                        registro[2] = rs.getString("piso");
                        registro[3] = rs.getString("descripcion");
                        registro[4] = rs.getString("caracteristicas");
                        registro[5] = rs.getString("precio_diario");
                        registro[6] = rs.getString("estado");
                        registro[7] = rs.getString("tipo_habitacion");
                        //System.out.println("REGISTROS: "+registro[0]+" "+registro[1]+" "+registro[2]+" "+registro[3]+" "+registro[4]+" "+registro[5]+" "+registro[6]+" "+registro[7]);
                        
                        totalregistros = totalregistros + 1;
                        modelo.addRow(registro);
                        
                        //System.out.println("Modelo: "+modelo.getColumnName(1));

                    }
return modelo;
                }else{
                    System.out.println("Error al realizar consulta a la BD");
                }
                //return modelo;
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, e);
                System.out.println("Error al cargar modelo");
return null;
                //return null;

            }
        return null;
        
        
      

    }

    /**
     *
     * @param dts
     * @return
     */
    public boolean insertar(vhabitacion dts) {
        sSQL = "insert  into habitacion(numero,piso,descripcion,caracteristicas,precio_diario,estado,tipo_habitacion)"
                + "values(?,?,?,?,?,?,?)";

        try {
// dentro del try, preparo la consulta declarando la variable pst
//enviamos todos los valores
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;

        }

    }

    public boolean editar(vhabitacion dts) {
        //modifica la tabla habitacion mas los valores que quiero modificar
        sSQL = "update habitacion set numero=?,piso=?,descripcion=?,caracteristicas=?,precio_diario=?,estado=?,tipo_habitacion=?"
                + "where idhabitacion=?";
        try {
            // dentro del try, preparo la consulta declarando la variable pst
//enviamos todos los valores
            PreparedStatement pst = cn.prepareStatement(sSQL);
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            pst.setInt(8, dts.getIdhabitacion());

            int n = pst.executeUpdate();

            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;

        }
  

    }

    public boolean eliminar(vhabitacion dts) {
      //borra en paramedro idhabitacion que yo indique
        sSQL = "delete from habitacion where idhabitacion =?";

        try {
            PreparedStatement pst = cn.prepareStatement(sSQL);

            pst.setInt(1, dts.getIdhabitacion());

            int n = pst.executeUpdate();

            if (n != 0){
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }

}

    