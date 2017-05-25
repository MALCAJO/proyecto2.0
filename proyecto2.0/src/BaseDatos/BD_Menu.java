package BaseDatos;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.*;
import Main.MainProyecto;
import modelos.Menu;



public class BD_Menu extends BD_Conecta{
	private static Statement s;	
	private static ResultSet reg;
	
	public BD_Menu(String fileName) {
		super(fileName);
		// TODO Auto-generated constructor stub
	}
	
//Este metodo añade un menu a un restaurante determinado.
	public  int añadir_menu( int codres, double precio, String nombre){	
		String cadenaSQL="INSERT  INTO menu (cod_restaurante,precio,nombre) VALUES('"+codres+"','"+precio+"','"+nombre+"')"; 	
		
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
//Método que borra todos los menus de un restaurante cuyo código le pasamos como parámetro
	public int borrar_menusXrestaurante(int cod_restaurante){
		String cadenaSQL="DELETE FROM menu WHERE cod_restaurante='"+cod_restaurante+"'";
		try{
			this.abrir();
			s=c.createStatement();
			int filas=s.executeUpdate(cadenaSQL);				
			s.close();
			return filas;
		}
		catch ( SQLException e){
			this.cerrar();
			return -1;
		}
	}
//Método para borrar un menu de un restaurante	
	public int borrar_menu(int cod_menu){
		String cadenaSQL="DELETE FROM menu WHERE cod_plato = '"+cod_menu+"' ";
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
//Método para cambiar precio de  un plato.
	public int cambiar_precio(int cod_menu, double nuevoprecio){
		String cadenaSQL="UPDATE menu SET precio = '" +nuevoprecio + "' WHERE cod_plato = '" +cod_menu+"' ";
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
//metodo que muestra los menus de un restaurante
	public  Vector<Menu> listarmenusXrestaurante(int cod_restaurante){
		String cadenaSQL="SELECT * from menu WHERE cod_restaurante='"+cod_restaurante+"'";
		Vector<Menu> listaMenu=new Vector<Menu>();
		try{
			this.abrir();
			s=c.createStatement();
			reg=s.executeQuery(cadenaSQL);
			while ( reg.next()){
				listaMenu.add(new Menu(reg.getDouble("precio"),reg.getString("nombre"),reg.getInt("cod_restaurante"),reg.getInt("cod_plato") ) );
			}
			s.close();
			this.cerrar();
			return listaMenu;
		}
		catch ( SQLException e){
			this.cerrar();
			return null;			
		}
	}
	
	
}
