package Main;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;
import BaseDatos.*;
import modelos.*;

public class Main_pruebas {
	static Scanner sc;
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		sc=new Scanner(System.in);;
		int  telefono,cod_postal, opc = 0, codres;
		int filas;
		String email;
		String contrasena;
		String direc,nombre,apellido, regist;
		String direccion;
		int i=0;
//Forzando commmit
		BD_Menu bdmenu=new BD_Menu("base_propiedades.xml");
		BD_Restaurante bdrest=new BD_Restaurante("base_propiedades.xml");		
		BD_Usuario bdusu=new BD_Usuario("base_propiedades.xml");
		BD_Empleado bdemple=new BD_Empleado("base_propiedades.xml");
		BD_Vehiculo bdmoto=new BD_Vehiculo("base_propiedades.xml");
		
		do{			
			System.out.println("Menu de opciones:");
			System.out.println("1. Login");
			System.out.println("2. Registro de nuevo usuario");
			System.out.println("3. Listar restaurantes");
			System.out.println("4. Salir");
			opc = sc.nextInt();
			switch(opc){
				case 1://Para hacer pedido entra por aqui
					sc.nextLine();
					do{
						System.out.println("Eres un usuario registrado?(si/no)");
						regist =sc.nextLine().toUpperCase();
					}while(!regist.equals("SI") && !regist.equals("NO"));
					
					//si es un usuario registrado
					if(regist.equals("SI")){
						System.out.println("Introduce tu email: ");
						email=sc.nextLine();
						System.out.println("Y contraseña: ");
						contrasena=sc.nextLine();
						Usu_Registrado usuarior = bdusu.verificar_login(email, contrasena);
						if(usuarior==null)
							System.out.println("Error en la conexión.");
						else{
							if (usuarior.getApellidos()==null)
								System.out.println("el usuario y la contraseña no coinciden");
							
							else{
								//si es del tipo usuario por aqui
								if(usuarior.getTipo().equals("usuar")){
									do{
										System.out.println("quieres realizar el pedido en tu direccion habitual? ");
										direc =sc.nextLine().toUpperCase();
									}while(!direc.equals("SI")&!direc.equals("NO"));
								
									//muestra los restaurantes del codigo postal de la direccion de entrega habitual
									if (direc.equals("SI")){
										
										//System.out.println(direccion = usuarior.getDireccion_habitual());
										cod_postal = usuarior.getCod_postal();
										System.out.println("Tienes estos restaurantes a tu disposición:");
										Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(cod_postal);
										for (i=0;i<restaurantes.size();i++)
											System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
										System.out.println("Introduce el codigo de restaurante para ver sus platos:");
										codres=sc.nextInt();
										Vector <Menu>  menus=bdmenu.listarmenusXrestaurante(codres);
										if (menus==null){
											System.out.println("En este momento no podemos realizar la operación");
										}
										else{
											System.out.println("Platos: ");
											for (i=0;i<menus.size();i++)
												System.out.println((i+1)+ ".- "+menus.get(i).toString());													
										}
										
									}
									
									else{
										System.out.println("Introduce el código postal de la zona donde quieres el pedido: ");
										cod_postal=sc.nextInt();
										Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(cod_postal);
										for (i=0;i<restaurantes.size();i++)
											System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
										System.out.println("Introduce el codigo de restaurante para ver sus platos:");
										codres=sc.nextInt();
										Vector <Menu>  menus=bdmenu.listarmenusXrestaurante(codres);
										if (menus==null){
											System.out.println("En este momento no podemos realizar la operación");
										}
										else{
											System.out.println("Platos: ");
											for (i=0;i<menus.size();i++)
												System.out.println((i+1)+ ".- "+menus.get(i).toString());													
										}
									}
								}
								//si es un usuario tipo restaurante por aqui..
								if(usuarior.getTipo().equals("resta")){
									codres = bdrest.buscar_codrestaurante(usuarior.getNombre(), usuarior.getDireccion_habitual());
									Vector <Menu>  menus=bdmenu.listarmenusXrestaurante(codres);
									
									System.out.println("el codigo del restaurante es:"+codres);
									System.out.println("Opciones:");
									System.out.println("1- Añadir nuevo menu.");
									System.out.println("2- Borrar un menu de la lista.");
									System.out.println("3- Modificar precio de menu.");
									System.out.println("4- Salir.");
									opc=sc.nextInt();
									switch(opc){
									
										case 1://añadir menu
											sc.nextLine();
											System.out.println("Nombre del menu? ");
											nombre=sc.nextLine();
											System.out.println("Precio del menu?");
											double precio=sc.nextDouble();
											filas = bdmenu.añadir_menu(codres, precio, nombre);
											if(filas==1)
												System.out.println("Menu añadido");
											if(filas==0)
												System.out.println("No se ha podido añadir el menu");
											if(filas== -1)
												System.out.println("Ha surgido un error inesperado, vuelva a intentarlo más tarde.");
										
											break;
										case 2://borrar menu											
											System.out.println("Que menu deseas eliminar?");
											if (menus==null){
												System.out.println("En este momento no podemos realizar la operación");
											}
											else{
												System.out.println("Platos: ");
												for (i=0;i<menus.size();i++)
													System.out.println((i+1)+ ".- "+menus.get(i).toString());													
											}
											System.out.println("Introduce el codigo de menu:");
											System.out.println(codres);
											int cod_menu=sc.nextInt();
											int menu=bdmenu.borrar_menu(cod_menu);
											if(menu==1)
												System.out.println("Menu borrado");
											if(menu==0)
												System.out.println("No se ha eliminado ningún menu");
											if(menu==-1)
												System.out.println("Ha surgido un error en el proceso, vuelva a intentarlo.");
											
											break;
										case 3://modificar menu
											System.out.println("Actualizar precio de un menu?");
											System.out.println("Menu: ");
											for (i=0;i<menus.size();i++)
												System.out.println((i+1)+ ".- "+menus.get(i).toString());
											System.out.println("Introduce el código del menu que quieres modificar");
											int cod_plato=sc.nextInt();
											System.out.println("Ahora introduce el precio:");
											precio=sc.nextDouble();
											filas=bdmenu.cambiar_precio(cod_plato, precio);
											if(filas==1)
												System.out.println("Menu modificado");
											if(filas==0)
												System.out.println("No se ha modificar el menu");
											if(filas==-1)
												System.out.println("Ha surgido un error en el proceso, vuelva a intentarlo.");
											break;
										case 4:
											System.out.println("Hasta pronto.");
											break;
										default:
											System.out.println("En serio?");
									}
									
								}
								//el administrador
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
										opc=sc.nextInt();
										switch(opc){
											case 1://opcion para añadir un nuevo restaurante a la BBDD
												sc.nextLine();												
												System.out.println("Nombre del restaurante: ");
												nombre=sc.nextLine();
												System.out.println("Tipo de comida");
												String tipo_comida=sc.nextLine();
												System.out.println("Dirección");
												direccion=sc.nextLine();
												do{	
													System.out.println("Email: ");//hacer comprobacion de email.
													email=sc.nextLine();
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
												contrasena=sc.nextLine();
												System.out.println("cif:");
												String cif=sc.nextLine();
												System.out.println("Código postal:");
												cod_postal=sc.nextInt();
												System.out.println("Teléfono:");
												telefono=sc.nextInt();
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
												codres=sc.nextInt();
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
												sc.nextLine();
												System.out.println("Introduce el email del usuario que quieres eliminar:");
												email=sc.nextLine();
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
												sc.nextLine();
												System.out.println("Introduce nombre");
												nombre=sc.nextLine();
												System.out.println("Apellido");
												apellido=sc.nextLine();
												System.out.println("DNI");
												String dni=sc.nextLine();										
												LocalDate fechaActual = LocalDate.now();
												Empleado emple=new Empleado(nombre,apellido,dni,fechaActual);
												filas=bdemple.altaEmpleado(emple);
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
													String matricula=sc.nextLine();
													int cod_emple=bdemple.buscar_empleXdni(dni);
													Vehiculo vehi=new Vehiculo(matricula,cod_emple);
													filas=bdmoto.altaVehiculo(vehi);
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
												int cod_emple=sc.nextInt();
												filas=bdemple.bajaEmpleado(cod_emple);
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
									}while(opc!=9);
								}
							}
						}
						
					}
					
					//si es un visitante
					else{
						
					}
					
					break;
					
					
				case 2://Para darte de alta en la aplicación 
					
					//email, contraseña,nombre, apellido,direccion,cod postal y codigo_oferta=1"la de bienvenida"
					System.out.println("A continuación te pediremos los datos para poder realizar el registro.");
					sc.nextLine();
					System.out.println("Cual es tu nombre?");
					nombre=sc.nextLine();
					System.out.println("Apellido?");
					apellido=sc.nextLine();
					do{	System.out.println("Email: ");//hacer comprobacion de email.
						email=sc.nextLine();
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
					contrasena= sc.nextLine();
					System.out.println("Direccion:");
					direccion=sc.nextLine();
					System.out.println("Código postal");
					cod_postal=sc.nextInt();
					System.out.println("Y por último, teléfono:");
					telefono=sc.nextInt();
					String tipo="usuar";
					//creamos el usuario_registrado nuevo
					Usu_Registrado usu=new Usu_Registrado( direccion, telefono,email, cod_postal, nombre, apellido, contrasena, direccion, 1,tipo);
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
										
					break;
				case 3://Lista los restaurante por codigo postal o todos los que estan dados de alta en la aplicación.					
					System.out.println("1. Lista los restaurante de un codigo postal.");
					System.out.println("2. Listar todos los restaurantes de la aplicación");
					System.out.println("3. Salir sin listar");	
					opc = sc.nextInt();
					
					switch(opc){
						case 1://lista los restaurantes solo de un codigo postal
							System.out.println("introduce el codigo postal en el que estas interesado:");
							cod_postal = sc.nextInt();
							Vector <Restaurante> restaurantesXzona=bdrest.listarRestaurantesXzona(cod_postal);
							if (restaurantesXzona==null){
								System.out.println("En este momento no podemos realizar la operación");

							}else{
								System.out.println("Listado de restaurantes");
								for (i=0;i<restaurantesXzona.size();i++){
									System.out.println((i+1)+ ".- "+restaurantesXzona.get(i).toString());
								}
								System.out.println("Introduce el codigo de restaurante para ver sus platos:");
								codres=sc.nextInt();
								Vector <Menu>  menus=bdmenu.listarmenusXrestaurante(codres);
								if (menus==null){
									System.out.println("En este momento no podemos realizar la operación");
								}
								else{
									System.out.println("Platos: ");
									for (i=0;i<menus.size();i++)
										System.out.println((i+1)+ ".- "+menus.get(i).toString());													
								}
							}
							
							break;
						case 2://lista todos los restaurantes en la aplicacion
							Vector <Restaurante> restaurantes=bdrest.listarRestaurantes();
							if (restaurantes==null){
								System.out.println("En este momento no podemos realizar la operación");

							}else{
								System.out.println("Listado de restaurantes");
								for (i=0;i<restaurantes.size();i++)
									System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
								System.out.println("Introduce el codigo de restaurante para ver sus platos:");
								codres=sc.nextInt();
								Vector <Menu>  menus=bdmenu.listarmenusXrestaurante(codres);
								if (menus==null){
									System.out.println("En este momento no podemos realizar la operación");

								}else{
									System.out.println("Platos: ");
									for (i=0;i<menus.size();i++)
										System.out.println((i+1)+ ".- "+menus.get(i).toString());													
								}
							}
							
							break;
						case 3:
							System.out.println("Como gustes");
							break;
						default:
							System.out.println("En serio??");
					}
					
					break;
				case 4://Sales de la aplicación.
					System.out.println("Hasta pronto");
					break;
				default://Entradas erroneas en el menu
					System.out.println("En serio??");	
			
			}
		}while(opc!=4);
	}

}
