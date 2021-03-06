package modelos;

public class Restaurante {
	
	private String direccion;
	private int cod_restaurante;
	private int cod_postal;
	private int telefono;
	private String cif;
	private String nombre;
	
	public Restaurante(String direccion, String nombre, int cod_restaurante, int cod_postal,int telefono,String cif) {
		super();
		this.direccion = direccion;
		this.nombre = nombre;
		this.cod_restaurante = cod_restaurante;
		this.cod_postal = cod_postal;
		this.telefono = telefono;
		this.cif = cif;
	}
	
	
	public Restaurante(String direccion, int cod_postal, int telefono, String cif, String nombre) {
		super();
		this.direccion = direccion;
		this.cod_postal = cod_postal;
		this.telefono = telefono;
		this.cif = cif;
		this.nombre = nombre;
	}


	/**
	 * @return the nombre
	 * 
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the cod_restaurante
	 */
	public int getCod_restaurante() {
		return cod_restaurante;
	}
	/**
	 * @param cod_restaurante the cod_restaurante to set
	 */
	public void setCod_restaurante(int cod_restaurante) {
		this.cod_restaurante = cod_restaurante;
	}
	/**
	 * @return the cod_postal
	 */
	public int getCod_postal() {
		return cod_postal;
	}
	/**
	 * @param cod_postal the cod_postal to set
	 */
	public void setCod_postal(int cod_postal) {
		this.cod_postal = cod_postal;
	}
	/**
	 * @return the telefono
	 */
	public int getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}
	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}
	/**
	 * a�addido toString
	 */
	@Override
	public String toString() {
		return "Restaurante: " + nombre + ", en: "+direccion+", "+cod_postal+". C�digo: "+cod_restaurante+", t�lefono: " +telefono;
	}
	
}
