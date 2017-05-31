package BaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelos.Vehiculo;

/**
 * 
 * @author Marina
 *
 */
public class BD_Vehiculo extends BD_Conecta{
	private static Statement s;	
	private static ResultSet reg;
	
	public BD_Vehiculo(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
 /**
  * Metodo que permite dar de alta un nuevo vehiculo
  * @param ve
  * @return
  */
	public int altaVehiculo(Vehiculo ve){
		String cadenaSQL="INSERT INTO vehiculo  VALUES('" + ve.getMatricula() +"',"+ve.getCod_personal()+") ";	
		
		try{
			this.abrir();
			s=c.createStatement();
			int filas=s.executeUpdate(cadenaSQL);
			s.close();
			this.cerrar();
			return filas;
			}
		catch ( SQLException e){
				this.cerrar();
				
				return -1;
			}
	}

	/**
	 * Metodo que oermite dar de baja un vehiuclo por averia,etc o eliminarlo de la base de datos
	 * @param cod_emple
	 * @return
	 */
	public int bajaVehiculo(int cod_emple){
		String cadenaSQL="DELETE FROM vehiculo WHERE cod_empleado='"+cod_emple+"' ";
		try{
			this.abrir();
			s=c.createStatement();
			int filas=s.executeUpdate(cadenaSQL);
			s.close();
			this.cerrar();
			return filas;
		}
		catch (SQLException e){		
			this.cerrar();
			return -1;
		}
	}
	/**
	 * Metodo que permite cambiarle la matricula a un vehiculo especifico	
	 * @param matricula
	 * @param codPer
	 * @return
	 */
	public int modificarMatricula(String matricula,int codPer){
		String cadenaSQL="UPDATE vehiculo SET matricula= '" + matricula + "' WHERE cod_empleado= "+codPer+" ";
		try{
			this.abrir();
			s=c.createStatement();
			int filas=s.executeUpdate(cadenaSQL);
			s.close();
			this.cerrar();
			return filas;
		}
		catch ( SQLException e){	
			this.cerrar();
			return -1;
		}
	}
	
}
