package Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Vector;

import BaseDatos.BD_Menu;
import BaseDatos.BD_Pedido;
import BaseDatos.BD_Restaurante;
import BaseDatos.BD_Usuario;
import modelos.*;


public class MainProyecto {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int cod_postal, menu = 0, codres;
		String email;
		String contrasena;
		int codpos=0;
		String direc, regist;
		String direccion, nombre, apellido;
		int i=0,filas,telefono=0, codigo = 0,salida=0, cod_plato = 0, qPlatos=0;
		double precio = 0;

		BD_Menu bdmenu=new BD_Menu("base_propiedades.xml");
		BD_Restaurante bdrest=new BD_Restaurante("base_propiedades.xml");		
		BD_Usuario bdusu=new BD_Usuario("base_propiedades.xml");		
		BD_Pedido bdped=new BD_Pedido("base_propiedades.xml");

		do {
			try {
				System.out.println("Menu");
				System.out.println("1. identificarse");
				System.out.println("2. dar de alta");
				System.out.println("3. listar restaurantes");
				System.out.println("4. salir");
				menu = Integer.parseInt(br.readLine());
			} catch (NumberFormatException N) {
				System.out.println(N.getMessage());
			}
			catch (IOException e){
				System.out.println(e.getMessage());
				System.exit(0);
			}


			switch (menu) {

			case 1:

				do{//usuario registrado o no
					System.out.println("eres un usuario registrado?");
					regist = br.readLine().toUpperCase();
				}while(!regist.equals("SI") && !regist.equals("NO"));

				//usuario registrado
				if (regist.equals("SI")){
					System.out.println("login");
					System.out.println("email:");
					email = br.readLine();
					System.out.println("contraseña:");
					contrasena = br.readLine();
					Usu_Registrado usuarior = bdusu.verificar_login(email, contrasena);

					if(usuarior==null)
						System.out.println("error, no se ha podido conectar");
					else
						if (usuarior.getApellidos()==null)
							System.out.println("el usuario y la contraseña no coinciden");
						else{
							do{
								System.out.println("quieres realizar el pedido en tu direccion habitual? ");
								direc = br.readLine().toUpperCase();
							}while(!direc.equals("SI")&!direc.equals("NO"));

							if (direc.equals("SI")){
								//quitar los syso
								System.out.println(direccion = usuarior.getDireccion_habitual());
								cod_postal = usuarior.getCod_postal();
								System.out.println("estos son los restaurantes del codigo postal");
								Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(cod_postal);
								for (i=0;i<restaurantes.size();i++)
									System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());

								System.out.println("que restaurante quieres, escribe su codigo?");
								codigo = Integer.parseInt(br.readLine());
								Vector <Menu> menus = bdmenu.listarmenusXrestaurante(codigo);
								System.out.println(menus);
								if(menus.size()==0){
									System.out.println("todavia no se han dado de alta menus");
								}
								try{
								do{
									System.out.println("1. agregar plato del menu");
									System.out.println("2. quitar plato del menu");
									System.out.println("3. confirmar pedido");
									System.out.println("4. salida");
									salida = Integer.parseInt(br.readLine());
								}while(salida==4);
								}catch(NumberFormatException e){
									System.out.println(e.getMessage());
								}
								
								switch(salida){
								
								case 1:
									
									LocalDate fechaActual = LocalDate.now();
									System.out.println("que plato quieres? dime el codigo del plato");
									cod_plato = Integer.parseInt(br.readLine());
									System.out.println("cuantos platos quieres?");
									qPlatos = Integer.parseInt(br.readLine());
									precio = 
									Vector <Linea_pedido> lPedido = new Vector<Linea_pedido>(cod_plato,qPlatos,fechaActual,precio);
									//prueba
									break;
									
								case 2:
									
									
									break;
								case 3:
									
									
									break;
								case 4:
									
									
									break;
								}
								
							}else{

								if(direc.equals("NO")){
									try{
										do{
											System.out.println("dime un codigo postal: ");
											codpos = Integer.parseInt(br.readLine());
										}while(codpos<0 || codpos>99999);
									}catch(NumberFormatException e){
										System.out.println(e.getMessage());
									}
									Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(codpos);
									if (restaurantes==null){
										System.out.println("En este momento no podemos realizar la operación");

									}else{
										System.out.println("Listado de restaurantes");
										for (i=0;i<restaurantes.size();i++)
											System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
										try{
										System.out.println("que restaurante quieres, escribe su codigo?");
										codigo = Integer.parseInt(br.readLine());
										}catch(NumberFormatException e){
											System.out.println(e.getMessage());
										}
										Vector <Menu> menus = bdmenu.listarmenusXrestaurante(codigo);
										System.out.println(menus);
										
										
										
									}}}}

					break;

				}
				//usuario no registrado
				if(regist.equals("NO")){
					try{
						do{
							System.out.println("dime un codigo postal: ");
							codpos = Integer.parseInt(br.readLine());
						}while(codpos<0 || codpos>99999);
					}catch(NumberFormatException e){
						System.out.println(e.getMessage());
					}
					Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(codpos);
					if (restaurantes==null){
						System.out.println("En este momento no podemos realizar la operación");

					}else{
						System.out.println("Listado de restaurantes");
						for (i=0;i<restaurantes.size();i++)
							System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
						System.out.print("dime el restaurante que quieres");
						codres = Integer.parseInt(br.readLine());
						
						
						
						
					}
					break;
				}





			case 2:

				System.out.println("A continuación te pediremos los datos para poder realizar el registro.");

				System.out.println("Cual es tu nombre?");
				nombre=br.readLine();
				System.out.println("Apellido?");
				apellido = br.readLine();
				do{

					System.out.println("Email: ");//hacer comprobacion de email.
					email=br.readLine();
					filas=bdusu.comprobar_email(email);
					switch(filas){
					case 0:
						System.out.println("Email disponible.");
						break;
					case 1:
						System.out.println("El email introducido ya pertenece a un usuario registrado");
						break;
					case -1:
						System.out.println("Lo sentimo, ha ocurrido un problema durante el registro. Vuelva a intentarlo.");
						break;							
					}
				}while(filas!=0);	
				System.out.println("Contraseña:");
				contrasena= br.readLine();
				System.out.println("Direccion:");
				direccion=br.readLine();
				try{
					do{
						System.out.println("Código postal");
						codpos=Integer.parseInt(br.readLine());
					}while(codpos<0 || codpos>99999);
				}catch(NumberFormatException e){
					System.out.println(e.getMessage());
				}
				try{
					System.out.println("Y por último, teléfono:");
					telefono=Integer.parseInt(br.readLine());
				}catch(NumberFormatException e){
					System.out.println(e.getMessage());
				}
				String tipo="usuar";
				//creamos el usuario_registrado nuevo
				Usu_Registrado usu=new Usu_Registrado( direccion, telefono,email, codpos, nombre, apellido, contrasena, direccion, 1,tipo);
				filas=bdusu.alta_usuario(usu);
				switch(filas){
				case 1:
					System.out.println("Registro completado con exito");
					break;
				case 0:
					System.out.println("El registro no se ha podido realizar.");
					break;
				case -1:
					System.out.println("Lo sentimo, ha ocurrido un problema durante el registro. Vuelva a intentarlo.");
					break;							
				}


				//prueba
				
				break;

			}

		} while (menu != 4);
	}

}
