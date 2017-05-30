package BaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import modelos.Oferta;
/**
 * 
 * @author Marina
 *
 */
public class BD_Oferta extends BD_Conecta{
	private static Statement s;	
	private static ResultSet reg;
	
	public BD_Oferta(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Metodo que lista y muestra todas las ofertas disponibles
	 * @return
	 */
	public Vector<Oferta> listarOfertas(){
		String cadenaSQL="SELECT * from oferta";
		Vector<Oferta> listaOferta=new Vector<Oferta>();
		try{
			this.abrir();
			s=c.createStatement();
			reg=s.executeQuery(cadenaSQL);
			while ( reg.next()){
				listaOferta.add(new Oferta(reg.getInt("cod_oferta"),reg.getInt("descuento"),reg.getString("descripcion")));
			}
			s.close();
			this.cerrar();
			return listaOferta;
		}
		catch ( SQLException e){	
			this.cerrar();
			return null;			
		}
	}
	/**
	 * Metodo que devuleve las ofertas que estan disponibles por cada usuario
	 * @param email
	 * @return
	 */
	public Vector<Oferta> listaOfertaXUsu(String email){
		String cadenaSQL="SELECT * from menu WHERE cod_restaurante='"+email+"'";
		Vector<Oferta> listaOferta=new Vector<Oferta>();
		try{
			this.abrir();
			s=c.createStatement();
			reg=s.executeQuery(cadenaSQL);
			while ( reg.next()){
				listaOferta.add(new Oferta(reg.getInt("cod_oferta"),reg.getInt("descuento"),reg.getString("descripcion")));
			}
			s.close();
			this.cerrar();
			return listaOferta;
		}
		catch ( SQLException e){
			this.cerrar();
			return null;			
		}
	}
	
	/**
	 * Metodo que permite a�adir una nueva oferta
	 * @param of
	 * @return
	 */
	public  int a�adir_oferta( Oferta of){	
		String cadenaSQL="INSERT INTO oferta VALUES('" + of.getCod_oferta()+ "','" +
		of.getDescuento()+"','"+of.getDescripcion() +"')"; 	
		
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
	 * Metodo que permite borrar una oferta de las que existen actualmente
	 * @param cod_oferta
	 * @return
	 */
	public int borrar_oferta(int cod_oferta){
		String cadenaSQL="DELETE FROM oferta WHERE cod_oferta='"+cod_oferta+"' ";
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
