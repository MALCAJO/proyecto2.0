package BaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelos.Empleado;
/**
 * 
 * @author Marina
 *
 */
public class BD_Empleado extends BD_Conecta {
	private static Statement s;	
	private static ResultSet reg;
	
	public BD_Empleado(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
/**
 * metodo para dar de alta un nuevo empleado
 * @param em
 * @return
 */
	public int altaEmpleado(Empleado em){
		String cadenaSQL="INSERT INTO personal (dni,fecha_alta,nombre,apellido) VALUES('"+em.getDni()+"','"+em.getFecha_Alta()+"','"+em.getNombre() +"','"+ em.getApellidos()+ "')"; 	
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
	 * le pasamos el dni y retorna el codigo de empleado
	 * @param dni
	 * @return
	 */
	public int buscar_empleXdni(String dni){
		String cadenaSQL="SELECT cod_empleado FROM personal WHERE dni = '"+dni+"' ";
		int cod=0;
		try{
			this.abrir();
			s=c.createStatement();
			reg=s.executeQuery(cadenaSQL);	
			if ( reg.next()){
				cod=reg.getInt("cod_empleado");
			}
			s.close();
			this.cerrar();
			return cod;			
		}
		catch ( SQLException e){
			this.cerrar();
			return -1;
		}
	}
	
	
	
/**
 * 	método para dar de baja a un empleado
 * @param codPer
 * @return
 */
	public int bajaEmpleado(int codPer){
		String cadenaSQL="DELETE FROM personal WHERE cod_empleado='"+codPer+"' ";
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
