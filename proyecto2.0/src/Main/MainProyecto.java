package Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Vector;

import BaseDatos.*;

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
		int i=0,filas,telefono=0, codigo = 0,salida=0, cod_plato = 0, qPlatos=0, cod_personal=100;
		double precio = 0, importe=0;

		BD_Menu bdmenu=new BD_Menu("base_propiedades.xml");
		BD_Restaurante bdrest=new BD_Restaurante("base_propiedades.xml");		
		BD_Usuario bdusu=new BD_Usuario("base_propiedades.xml");		
		BD_Pedido bdped=new BD_Pedido("base_propiedades.xml");
		BD_Empleado bdemp=new BD_Empleado("base_propiedades.xml");
		BD_Oferta bdof=new BD_Oferta("base_propiedades.xml");
		BD_Vehiculo bdveh=new BD_Vehiculo("base_propiedades.xml");

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
							if(usuarior.getTipo().equals("usuar")){
								do{
									System.out.println("quieres realizar el pedido en tu direccion habitual? ");
									direc = br.readLine().toUpperCase();
								}while(!direc.equals("SI")&!direc.equals("NO"));

								if (direc.equals("SI")){
									//quitar los syso
									direccion = usuarior.getDireccion_habitual();
									System.out.println(direccion);
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
										precio = bdmenu.devuelve_precio(cod_plato, qPlatos);
										Vector <Linea_pedido> lPedido = new Vector<Linea_pedido>();
										lPedido.add(new Linea_pedido(cod_plato, qPlatos, fechaActual, precio));
										Pedido ped = new Pedido(fechaActual, cod_personal,codigo,direccion);
										bdped.nuevo_pedido(ped, lPedido);

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



										}}}}}
					if(usuarior.getTipo().equals("admin")){
						do{
							System.out.println("Opciones:");
							System.out.println("1- Añadir nuevo restaurante.");
							System.out.println("2- Borrar restaurante.");
							System.out.println("3- Borrar usuario.");
							System.out.println("4- Dar de alta a un nuevo empleado+vehículo.");
							System.out.println("5- Dar de baja a un empleado+vehículo.");										
							System.out.println("6- Añadir una oferta.");
							System.out.println("7- Borrar una oferta.");
							System.out.println("8- Modificar una matrícula.");
							System.out.println("9- Logout.");
							menu=Integer.parseInt(br.readLine());
							switch(menu){
							case 1://opcion para añadir un nuevo restaurante a la BBDD

								System.out.println("Nombre del restaurante: ");
								nombre= br.readLine();
								System.out.println("Tipo de comida");
								String tipo_comida=br.readLine();
								System.out.println("Dirección");
								direccion=br.readLine();
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
								System.out.println("Contraseña que quieres usar.");
								contrasena=br.readLine();
								System.out.println("cif:");
								String cif=br.readLine();;
								System.out.println("Código postal:");
								cod_postal=Integer.parseInt(br.readLine());
								System.out.println("Teléfono:");
								telefono=Integer.parseInt(br.readLine());
								String tipo="resta";
								//primero genero un nuevo objeto restaurante y lo se lo paso al metodo para añadirlo a la BBDD
								Restaurante resta=new Restaurante(direccion,cod_postal,telefono,cif,nombre);
								filas=bdrest.añadir_Restaurante(resta);
								switch(filas){
								case 1://si  se añade con exito le creo un usuario a ese restaurante para que pueda operar 
									Usu_Registrado usu=new Usu_Registrado(direccion, telefono,email, cod_postal, nombre, tipo_comida, contrasena, direccion, 2,tipo);
									filas=bdusu.alta_usuario(usu);
									switch(filas){
									case 1:	
										System.out.println("Registro de restaurante y su usuario completado con exito");
										break;
									case 0:
										System.out.println("El usuario del restaurante no se ha podido realizar.");
										break;
									case -1:
										System.out.println("Lo sentimo, ha ocurrido un problema durante el registro del usuario. Vuelva a intentarlo.");
										break;							
									}
									break;
								case 0:
									System.out.println("El registro del restaurante no se ha podido realizar.");
									break;
								case -1:
									System.out.println("Lo sentimo, ha ocurrido un problema durante el registro del restaurante. Vuelva a intentarlo.");
									break;							
								}

								break;
							case 2://opcion para borrar un restaurante mediante el codigo del mismo
								// listo todo los restaurantes y selecciono uno de ellos para borrarlo
								Vector <Restaurante> restaurantes=bdrest.listarRestaurantes();
								if (restaurantes==null){
									System.out.println("En este momento no podemos realizar la operación");

								}else{
									System.out.println("Listado de restaurantes");
									for (i=0;i<restaurantes.size();i++)
										System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
								}
								System.out.println("Introduce el código de restaurante para borrarlo de la BBDD.");
								codres=Integer.parseInt(br.readLine());
								filas=bdmenu.borrar_menusXrestaurante(codres);												
								if(filas==0)												
									System.out.println("No se ha borrado los menus del restaurante");

								if(filas>=1){

									filas=bdrest.borrar_Restaurante(codres);
									switch(filas){
									case 0:
										System.out.println("No se ha borrado el restaurante");
										break;
									case 1:
										filas=bdusu.borrar_usuario(email);
										switch(filas){
										case 0:
											System.out.println("No se ha borrado el usuario");
											break;
										case 1:
											System.out.println("Usuario borrado de la BBDD");
											break;
										case -1:
											System.out.println("Lo sentimo, ha ocurrido un problema durante el proceso. Vuelva a intentarlo.");
											break;
										}
										System.out.println("Restaurante y menus borrados de la BBDD");
										break;
									case -1:
										System.out.println("Lo sentimo, ha ocurrido un problema durante el proceso. Vuelva a intentarlo.");
										break;							
									}
								}

								if(filas<0)
									System.out.println("Lo sentimo, ha ocurrido un problema durante el registro. Vuelva a intentarlo.");



								break;
							case 3://opcion para eliminar un usuario de la BBDD

								System.out.println("Introduce el email del usuario que quieres eliminar:");
								email=br.readLine();
								filas=bdusu.borrar_usuario(email);
								switch(filas){
								case 0:
									System.out.println("No se ha borrado el usuario");
									break;
								case 1:
									System.out.println("Usuario borrado de la BBDD");
									break;
								case -1:
									System.out.println("Lo sentimo, ha ocurrido un problema durante el proceso. Vuelva a intentarlo.");
									break;
								}
								break;
							case 4://dar de alta un nuevo empleado y su respectivo vehículo

								System.out.println("Introduce nombre");
								nombre=br.readLine();
								System.out.println("Apellido");
								apellido=br.readLine();
								System.out.println("DNI");
								String dni=br.readLine();									
								LocalDate fechaActual = LocalDate.now();
								Empleado emple=new Empleado(nombre,apellido,dni,fechaActual);
								filas=bdemp.altaEmpleado(emple);
								switch(filas){
								case 0:
									System.out.println("No se ha realizado el alta");
									break;
								case 1:
									System.out.println("Alta realizada con éxito");														
									break;
								case -1:
									System.out.println("Ha ocurrido un error, vuelva ha intentarlo más tarde.");
									break;
								}
								if (filas==1){
									System.out.println("Introduce la matricula del vehiculo que usará el empleado:");
									String matricula=br.readLine();
									int cod_emple=bdemp.buscar_empleXdni(dni);
									Vehiculo vehi=new Vehiculo(matricula,cod_emple);
									filas=bdveh.altaVehiculo(vehi);
									switch(filas){
									case 0:
										System.out.println("No se ha podido añadir el vehículo.");
										break;
									case 1:
										System.out.println("Vehículo añadido con éxito");														
										break;
									case -1:
										System.out.println("Ha ocurrido un error, vuelva ha intentarlo más tarde.");
										break;
									}
								}

								break;
							case 5://dar de baja a un empleado
								System.out.println("Introduce el codigo del empleado:");
								int cod_emple=Integer.parseInt(br.readLine());
								filas=bdemp.bajaEmpleado(cod_emple);
								switch(filas){
								case 0:
									System.out.println("No se ha realizado la baja");
									break;
								case 1:
									System.out.println("Baja realizada con éxito");
									break;
								case -1:
									System.out.println("Ha ocurrido un error, vuelva ha intentarlo más tarde.");
									break;
								}
								break;
							case 6:
								break;
							case 7:
								break;
							case 8:
								break;
							case 9:
								System.out.println("Hasta otra");
								break;
							default:
								System.out.println("En serio?? pon bien la opcion");
							}
						}while(menu!=9);
					}

					else{
						if(usuarior.getTipo().equals("resta")){
							try{
								do{
									//con nombre y direccion obtener cod rest
									System.out.println("1. añadir menu");
									System.out.println("2. borrar menu");
									System.out.println("3. cambiar precio");
									System.out.println("4. logout");
									salida = Integer.parseInt(br.readLine());
								}while(salida!=4);
							}catch(NumberFormatException e){
								System.out.println(e.getMessage());
							}
							switch(salida){
							case 1:

								break;

							case 2:

								break;

							case 3:

								break;

							case 4:

								break;

							default:
								break;
							}
						}}

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
