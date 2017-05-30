package Main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BaseDatos.*;

import modelos.*;

/**
 * 
 * @author Alejandro
 *
 */

public class MainProyecto {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int cod_postal=0, menu = 0, codres=0;
		String email;
		String contrasena;
		int codpos=0,cod_restaurante=0, cod_emple=0;
		String direc, regist, descripcion,matricula;
		String direccion, nombre, apellido;
		int i=0,filas,telefono=0, codigo = 0,salida=0, cod_plato = 0, qPlatos=0, cod_personal=100,pos=0, cod_oferta=0, descuento=0,vehiculo=0;
		double precio = 0, importe=0;
		boolean correcto=false, validar=false;
		LocalDate fechaActual = LocalDate.now();
		Vector <Linea_pedido> lPedido = new Vector<Linea_pedido>();


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
					do{
						System.out.println("email:");
						email = br.readLine();

						validar=validarEmail(email);
						if(validar==true)
							System.out.println("email en formato correcto");
						else
							System.out.println("email en formato incorrecto");
					}while(validar!=true);

					System.out.println("contraseña:");
					contrasena = br.readLine();
					Usu_Registrado usuarior = bdusu.verificar_login(email, contrasena);

					if(usuarior==null)
						System.out.println("error, no se ha podido conectar");
					else
						if (usuarior.getApellidos()==null)
							System.out.println("el usuario y la contraseña no coinciden");
						else{
							//TIPO USUARIO
							//usuario USUARIO


							if(usuarior.getTipo().equals("usuar")){
								//REALIZAR A DIRECCION HABITUAL
								do{
									System.out.println("quieres realizar el pedido en tu direccion habitual? ");
									direc = br.readLine().toUpperCase();
								}while(!direc.equals("SI")&!direc.equals("NO"));

								if (direc.equals("SI")){

									direccion = usuarior.getDireccion_habitual();
									System.out.println(direccion);
									cod_postal = usuarior.getCod_postal();
									System.out.println("estos son los restaurantes del codigo postal");
									//filtrar por codigo postal
									Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(cod_postal);
									for (i=0;i<restaurantes.size();i++)
										System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
									//codigo del restaurante
									do{
										try{
											correcto=false;

											System.out.println("que restaurante quieres, escribe su codigo?");
											codigo = Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										for(i=0;i<restaurantes.size();i++){
											if(restaurantes.get(i).getCod_restaurante()==codigo)
												correcto=true;
										}

									}while(correcto==false);
									Vector <Menu> menus = bdmenu.listarmenusXrestaurante(codigo);
									for(i=0;i<menus.size();i++)
										System.out.println(menus.get(i).toString());
									if(menus.size()==0){
										System.out.println("todavia no se han dado de alta menus");
										break;
									}

									do{
										try{
											System.out.println("1. agregar plato del menu");
											System.out.println("2. quitar plato del menu");
											System.out.println("3. confirmar pedido y finalizar");

											salida = Integer.parseInt(br.readLine());

										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}

										switch(salida){

										case 1:

											do{
												try{
													correcto=false;
													System.out.println("que plato quieres? dime el codigo del plato");
													cod_plato = Integer.parseInt(br.readLine());
												} catch (NumberFormatException N) {
													System.out.println(N.getMessage());
												}
												catch (IOException e){
													System.out.println(e.getMessage());
													System.exit(0);
												}
												for(i=0;i<menus.size();i++){
													if(menus.get(i).getCod_plato()==cod_plato)
														correcto=true;
												}

											}while(correcto==false);
											try{
												System.out.println("cuantos platos quieres?");
												qPlatos = Integer.parseInt(br.readLine());
											} catch (NumberFormatException N) {
												System.out.println(N.getMessage());
											}
											catch (IOException e){
												System.out.println(e.getMessage());
												System.exit(0);
											}

											precio = bdmenu.devuelve_precio(cod_plato, qPlatos);
											System.out.println(precio+ " este es el precio del plato por cantidad");
											lPedido.add(new Linea_pedido(cod_plato, qPlatos, fechaActual, precio));

											break;

										case 2:

											for(i=0;i<lPedido.size();i++)
												System.out.println(i+1+" "+lPedido.get(i).toString());
											do{
												try{
													correcto=false;
													System.out.println("que platos quieres quitar? dime el codigo");
													pos = Integer.parseInt(br.readLine());
												} catch (NumberFormatException N) {
													System.out.println(N.getMessage());
												}
												catch (IOException e){
													System.out.println(e.getMessage());
													System.exit(0);
												}
												for(i=0;i<menus.size();i++){
													if(menus.get(i).getCod_plato()==cod_plato)
														correcto=true;
												}

											}while(correcto==false);
											lPedido.remove(pos-1);

											break;
										case 3:

											System.out.println("tu pedido");
											for(i=0;i<lPedido.size();i++)
												System.out.println(lPedido.get(i).toString());

											Pedido ped = new Pedido(fechaActual, cod_personal,codigo,direccion);
											bdped.nuevo_pedido(ped, lPedido);
											System.out.println("tu pedido ha sido realizado con exito");
											break;

										}
									}while(salida!=3);

								}else{

									if(direc.equals("NO")){
										try{
											do{
												System.out.println("dime un codigo postal: ");
												codpos = Integer.parseInt(br.readLine());
											}while(codpos<0 || codpos>99999);
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(codpos);
										if (restaurantes==null){
											System.out.println("En este momento no podemos realizar la operacion");

										}else{
											System.out.println("Listado de restaurantes");
											for (i=0;i<restaurantes.size();i++)
												System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
											do{
												try{
													correcto = false;
													System.out.println("que restaurante quieres, escribe su codigo?");
													codigo = Integer.parseInt(br.readLine());
												} catch (NumberFormatException N) {
													System.out.println(N.getMessage());
												}
												catch (IOException e){
													System.out.println(e.getMessage());
													System.exit(0);
												}
												for(i=0;i<restaurantes.size();i++){
													if(restaurantes.get(i).getCod_restaurante()==codigo)
														correcto=true;
												}

											}while(correcto==false);
											Vector <Menu> menus = bdmenu.listarmenusXrestaurante(codigo);
											for(i=0;i<menus.size();i++)
												System.out.println(menus.get(i).toString());

											System.out.println("dime la direccion a la que quieres enviar el pedido");
											direccion = br.readLine();

											if(menus.size()==0){
												System.out.println("todavia no se han dado de alta menus");
												break;
											}

											do{
												try{
													System.out.println("1. agregar plato del menu");
													System.out.println("2. quitar plato del menu");
													System.out.println("3. confirmar pedido y finalizar");
													salida = Integer.parseInt(br.readLine());

												} catch (NumberFormatException N) {
													System.out.println(N.getMessage());
												}
												catch (IOException e){
													System.out.println(e.getMessage());
													System.exit(0);
												}

												switch(salida){

												case 1:
													do{
														try{
															correcto=false;
															System.out.println("que plato quieres? dime el codigo del plato");
															cod_plato = Integer.parseInt(br.readLine());
														} catch (NumberFormatException N) {
															System.out.println(N.getMessage());
														}
														catch (IOException e){
															System.out.println(e.getMessage());
															System.exit(0);
														}
														for(i=0;i<menus.size();i++){
															if(menus.get(i).getCod_plato()==cod_plato)
																correcto=true;
														}

													}while(correcto==false);
													try{
														System.out.println("cuantos platos quieres?");
														qPlatos = Integer.parseInt(br.readLine());
													} catch (NumberFormatException N) {
														System.out.println(N.getMessage());
													}
													catch (IOException e){
														System.out.println(e.getMessage());
														System.exit(0);
													}
													precio = bdmenu.devuelve_precio(cod_plato, qPlatos);
													System.out.println(precio+ " este es el precio del plato por cantidad");
													lPedido.add(new Linea_pedido(cod_plato, qPlatos, fechaActual, precio));

													break;

												case 2:

													for(i=0;i<lPedido.size();i++)
														System.out.println(i+1+" "+lPedido.get(i).toString());
													do{
														try{
															correcto=false;
															System.out.println("que platos quieres quitar? dime el codigo");
															pos = Integer.parseInt(br.readLine());
														} catch (NumberFormatException N) {
															System.out.println(N.getMessage());
														}
														catch (IOException e){
															System.out.println(e.getMessage());
															System.exit(0);
														}
														for(i=0;i<menus.size();i++){
															if(menus.get(i).getCod_plato()==cod_plato)
																correcto=true;
														}

													}while(correcto==false);
													lPedido.remove(pos);

													break;
												case 3:

													System.out.println("tu pedido");
													for(i=0;i<lPedido.size();i++)
														System.out.println(lPedido.get(i).toString());

													Pedido ped = new Pedido(fechaActual, cod_personal,codigo,direccion);
													bdped.nuevo_pedido(ped, lPedido);
													System.out.println("tu pedido ha sido realizado con exito");

													break;

												}
											}while(salida!=3);
										}}}}

							//TIPO ADMIN
							if(usuarior.getTipo().equals("admin")){

								do{
									try{
										System.out.println("Opciones:");
										System.out.println("1- Añadir nuevo restaurante.");
										System.out.println("2- Borrar restaurante.");
										System.out.println("3- Borrar usuario.");
										System.out.println("4- Dar de alta a un nuevo empleado+vehiculo.");
										System.out.println("5- Dar de baja a un empleado+vehiculo.");										
										System.out.println("6- Añadir una oferta.");
										System.out.println("7- Borrar una oferta.");
										System.out.println("8- Modificar una matricula.");
										System.out.println("9- Logout.");
										menu=Integer.parseInt(br.readLine());
									} catch (NumberFormatException N) {
										System.out.println(N.getMessage());
									}
									catch (IOException e){
										System.out.println(e.getMessage());
										System.exit(0);
									}
									switch(menu){
									case 1://opcion para aÃ±adir un nuevo restaurante a la BBDD

										System.out.println("Nombre del restaurante: ");
										nombre= br.readLine();
										System.out.println("Tipo de comida");
										String tipo_comida=br.readLine();
										System.out.println("Direccion");
										direccion=br.readLine();
										do{	
											do{
												System.out.println("Email: ");//hacer comprobacion de email.
												email=br.readLine();
												validar=validarEmail(email);
												if(validar==true)
													System.out.println("email en formato correcto");
												else
													System.out.println("email en formato incorrecto");
											}while(validar!=true);

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
										String cif=br.readLine();
										try{
											System.out.println("Codigo postal:");
											cod_postal=Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										try{
											System.out.println("Telefono:");
											telefono=Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										String tipo="resta";
										//primero genero un nuevo objeto restaurante y lo se lo paso al metodo para aÃ±adirlo a la BBDD
										Restaurante resta=new Restaurante(direccion,cod_postal,telefono,cif,nombre);
										filas=bdrest.añadir_Restaurante(resta);
										switch(filas){
										case 1://si  se aÃ±ade con exito le creo un usuario a ese restaurante para que pueda operar 
											Usu_Registrado usu=new Usu_Registrado(direccion, telefono,email, cod_postal, nombre, tipo_comida, contrasena, direccion, 1,tipo);
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
											System.out.println("Lo sentimos, ha ocurrido un problema durante el registro del restaurante. Vuelva a intentarlo.");
											break;							
										}

										break;
									case 2://opcion para borrar un restaurante mediante el codigo del mismo
										// listo todo los restaurantes y selecciono uno de ellos para borrarlo
										Vector <Restaurante> restaurantes=bdrest.listarRestaurantes();
										if (restaurantes==null){
											System.out.println("En este momento no podemos realizar la operacion");

										}else{
											System.out.println("Listado de restaurantes");
											for (i=0;i<restaurantes.size();i++)
												System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
										}
										try{
											System.out.println("Introduce el codigo de restaurante para borrarlo de la BBDD.");
											codres=Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
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
										do{
											System.out.println("Introduce el email del usuario que quieres eliminar:");
											email=br.readLine();
											validar=validarEmail(email);
											if(validar==true)
												System.out.println("email en formato correcto");
											else
												System.out.println("email en formato incorrecto");
										}while(validar!=true);

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
									case 4://dar de alta un nuevo empleado y su respectivo vehiculo

										System.out.println("Introduce nombre");
										nombre=br.readLine();
										System.out.println("Apellido");
										apellido=br.readLine();
										System.out.println("DNI");
										String dni=br.readLine();									

										Empleado emple=new Empleado(nombre,apellido,dni,fechaActual);
										filas=bdemp.altaEmpleado(emple);
										switch(filas){

										case 0:
											System.out.println("No se ha realizado el alta");
											break;
										case 1:
											System.out.println("Alta realizada con exito");														
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mas tarde.");
											break;
										}
										if (filas==1){

											System.out.println("Introduce la matricula del vehiculo que usara el empleado:");
											matricula=br.readLine();
											cod_emple=bdemp.buscar_empleXdni(dni);
											Vehiculo vehi=new Vehiculo(matricula,cod_emple);
											filas=bdveh.altaVehiculo(vehi);
											switch(filas){

											case 0:
												System.out.println("No se ha podido añadir el vehiculo.");
												break;
											case 1:
												System.out.println("Vehiculo añadido con exito");														
												break;
											case -1:
												System.out.println("Ha ocurrido un error, vuelva ha intentarlo mas tarde.");
												break;
											}
										}

										break;
									case 5://dar de baja a un empleado
										try{
											System.out.println("Introduce el codigo del empleado:");
											cod_emple=Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										
//meter el borrar la matricula y el metodo para borrarlo
										filas=bdemp.bajaEmpleado(cod_emple);
										switch(filas){

										case 0:
											System.out.println("No se ha realizado la baja");
											break;
										case 1:
											System.out.println("Baja realizada con exito");
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mas tarde.");
											break;
										}

										break;

									case 6:
										try{
											System.out.println("dime un codigo para la oferta");
											cod_oferta = Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										try{
											System.out.println("de cuanto es el descuento?");
											descuento = Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										System.out.println("dime una descripcion de la oferta");
										descripcion = br.readLine();

										Oferta ofer= new Oferta(cod_oferta,descuento,descripcion);
										filas=bdof.añadir_oferta(ofer);

										switch(filas){

										case 0:
											System.out.println("No se ha realizar el alta de la oferta");
											break;
										case 1:
											System.out.println("oferta aÃ±adida con Ã©xito");
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mÃ¡s tarde.");
											break;
										}

										break;

									case 7:
										try{
											System.out.println("dime el codigo de la oferta");
											cod_oferta=Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										filas=bdof.borrar_oferta(cod_oferta);

										switch(filas){

										case 0:
											System.out.println("No se ha podido borrar la oferta");
											break;
										case 1:
											System.out.println("Oferta borrada con Ã©xito");
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mÃ¡s tarde.");
											break;
										}

										break;

									case 8:

										try{
											System.out.println("dime el codigo del empleado para modificar la matricula");
											cod_emple = Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										System.out.println("dime la matricula nueva que quieres poner");
										matricula = br.readLine();

										filas = bdveh.modificarMatricula(matricula, cod_emple);

										switch(filas){

										case 0:
											System.out.println("No se ha realizado el cambio");
											break;
										case 1:
											System.out.println("Modificacion de matricula realizada con Ã©xito");
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mÃ¡s tarde.");
											break;
										}

										break;
									case 9:
										System.out.println("Hasta otra");
										break;
									default:
										System.out.println("En serio?? pon bien la opcion");
										break;
									}
								}while(menu!=9);
							}

							//TIPO RESTAURANTE
							if(usuarior.getTipo().equals("resta")){
								nombre=usuarior.getNombre();
								direccion = usuarior.getDireccion_habitual();
								codres=bdrest.buscar_codrestaurante(nombre, direccion);
								do{
									try{
										//con nombre y direccion obtener cod rest
										System.out.println("1. añadir menu");
										System.out.println("2. borrar menu");
										System.out.println("3. cambiar precio");
										System.out.println("4. logout");
										salida = Integer.parseInt(br.readLine());

									} catch (NumberFormatException N) {
										System.out.println(N.getMessage());
									}
									catch (IOException e){
										System.out.println(e.getMessage());
										System.exit(0);
									}

									switch(salida){
									case 1:

										do{
											try{
												System.out.println("dime el precio del plato");
												precio = Double.parseDouble(br.readLine());
											} catch (NumberFormatException N) {
												System.out.println(N.getMessage());
											}
											catch (NullPointerException n){
												System.out.println(n.getMessage());
												System.exit(0);
											}
										}while(precio<0);
										System.out.println("dime el nombre del plato");
										nombre = br.readLine();

										filas = bdmenu.añadir_menu(codres, precio, nombre);

										switch(filas){

										case 0:
											System.out.println("No se ha podido añadir el plato");
											break;
										case 1:
											System.out.println("añadido el plato con exito");
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mas tarde.");
											break;
										}

										break;

									case 2:


										bdmenu.listarmenusXrestaurante(codres);
										try{
											System.out.println("dime el codigo del menu");
											cod_plato = Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										filas = bdmenu.borrar_menu(cod_plato);

										switch(filas){

										case 0:
											System.out.println("No se ha podido borrar el plato");
											break;
										case 1:
											System.out.println("borrado el plato con exito");
											break;
										case -1:
											System.out.println("Ha ocurrido un error, vuelva ha intentarlo mas tarde.");
											break;
										}

										break;

									case 3:

										bdmenu.listarmenusXrestaurante(codres);
										try{
											System.out.println("dime el codigo del menu");
											cod_plato = Integer.parseInt(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (IOException e){
											System.out.println(e.getMessage());
											System.exit(0);
										}
										try{
											System.out.println("dime el nuevo precio del plato");
											precio = Double.parseDouble(br.readLine());
										} catch (NumberFormatException N) {
											System.out.println(N.getMessage());
										}
										catch (NullPointerException n){
											System.out.println(n.getMessage());
											System.exit(0);
										}
										filas = bdmenu.cambiar_precio(cod_plato, precio);

										break;

									case 4:

										System.out.println("Hasta luego");

										break;

									}}while(salida!=4);
							}}}

				//usuario no registrado
				if(regist.equals("NO")){
					try{
						do{
							System.out.println("dime un codigo postal: ");
							codpos = Integer.parseInt(br.readLine());
						}while(codpos<0 || codpos>99999);
					} catch (NumberFormatException N) {
						System.out.println(N.getMessage());
					}
					catch (IOException e){
						System.out.println(e.getMessage());
						System.exit(0);
					}
					System.out.println("dime la direccion en la que quieres entregar el pedido");
					direccion = br.readLine();
					Vector <Restaurante> restaurantes=bdrest.listarRestaurantesXzona(codpos);
					if (restaurantes==null){
						System.out.println("En este momento no podemos realizar la operacion");

					}else{
						System.out.println("Listado de restaurantes");
						for (i=0;i<restaurantes.size();i++)
							System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
						do{
							try{
								correcto=false;
								System.out.print("dime el restaurante que quieres");
								codres = Integer.parseInt(br.readLine());
							} catch (NumberFormatException N) {
								System.out.println(N.getMessage());
							}
							catch (IOException e){
								System.out.println(e.getMessage());
								System.exit(0);
							}
							for(i=0;i<restaurantes.size();i++){
								if(restaurantes.get(i).getCod_restaurante()==codres)
									correcto=true;
							}

						}while(correcto==false);
						Vector <Menu> menus = bdmenu.listarmenusXrestaurante(codigo);
						for(i=0;i<menus.size();i++)
							System.out.println(menus.get(i).toString());
						if(menus.size()==0){
							System.out.println("todavia no se han dado de alta menus");
							break;
						}

						do{
							try{
								System.out.println("1. agregar plato del menu");
								System.out.println("2. quitar plato del menu");
								System.out.println("3. confirmar pedido y finalizar");

								salida = Integer.parseInt(br.readLine());

							} catch (NumberFormatException N) {
								System.out.println(N.getMessage());
							}
							catch (IOException e){
								System.out.println(e.getMessage());
								System.exit(0);
							}

							switch(salida){

							case 1:
								do{
									try{
										correcto=false;
										System.out.println("que plato quieres? dime el codigo del plato");
										cod_plato = Integer.parseInt(br.readLine());
									} catch (NumberFormatException N) {
										System.out.println(N.getMessage());
									}
									catch (IOException e){
										System.out.println(e.getMessage());
										System.exit(0);
									}
									for(i=0;i<menus.size();i++){
										if(menus.get(i).getCod_plato()==cod_plato)
											correcto=true;
									}

								}while(correcto==false);
								try{
									System.out.println("cuantos platos quieres?");
									qPlatos = Integer.parseInt(br.readLine());
								} catch (NumberFormatException N) {
									System.out.println(N.getMessage());
								}
								catch (IOException e){
									System.out.println(e.getMessage());
									System.exit(0);
								}
								precio = bdmenu.devuelve_precio(cod_plato, qPlatos);
								System.out.println(precio+ " este es el precio del plato por cantidad");
								lPedido.add(new Linea_pedido(cod_plato, qPlatos, fechaActual, precio));

								break;

							case 2:

								for(i=0;i<lPedido.size();i++)
									System.out.println(i+1+" "+lPedido.get(i).toString());
								do{
									try{
										correcto=false;
										System.out.println("que platos quieres quitar? dime el codigo");
										pos = Integer.parseInt(br.readLine());
										for(i=0;i<menus.size();i++){
											if(menus.get(i).getCod_plato()==cod_plato)
												correcto=true;
										}
									} catch (NumberFormatException N) {
										System.out.println(N.getMessage());
									}
									catch (IOException e){
										System.out.println(e.getMessage());
										System.exit(0);
									}
								}while(correcto==false);
								lPedido.remove(pos);

								break;
							case 3:

								System.out.println("tu pedido");
								for(i=0;i<lPedido.size();i++)
									System.out.println(lPedido.get(i).toString());

								Pedido ped = new Pedido(fechaActual, cod_personal,codigo,direccion);
								bdped.nuevo_pedido(ped, lPedido);
								System.out.println("tu pedido ha sido realizado con exito");
								break;

							}
						}while(salida!=3);
					}
					break;
				}
				break;

			case 2:

				System.out.println("A continuacion te pediremos los datos para poder realizar el registro.");

				System.out.println("Cual es tu nombre?");
				nombre=br.readLine();
				System.out.println("Apellido?");
				apellido = br.readLine();
				do{
					do{
						System.out.println("Email: ");//hacer comprobacion de email.
						email=br.readLine();
						validar=validarEmail(email);
						if(validar==true)
							System.out.println("email en formato correcto");
						else
							System.out.println("email en formato incorrecto");
					}while(validar!=true);

					filas=bdusu.comprobar_email(email);
					switch(filas){
					case 0:
						System.out.println("Email disponible.");
						break;
					case 1:
						System.out.println("El email introducido ya pertenece a un usuario registrado");
						break;
					case -1:
						System.out.println("Lo sentimos, ha ocurrido un problema durante el registro. Vuelva a intentarlo.");
						break;							
					}
				}while(filas!=0);	
				System.out.println("Contraseña:");
				contrasena= br.readLine();
				System.out.println("Direccion:");
				direccion=br.readLine();
				try{
					do{
						System.out.println("Codigo postal");
						codpos=Integer.parseInt(br.readLine());
					}while(codpos<0 || codpos>99999);
				}catch(NumberFormatException e){
					System.out.println(e.getMessage());
				}
				try{
					System.out.println("Y por ultimo, telefono:");
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

				break;

			case 3:

				Vector <Restaurante> restaurantes=bdrest.listarRestaurantes();
				for (i=0;i<restaurantes.size();i++)
					System.out.println((i+1)+ ".- "+restaurantes.get(i).toString());
				break;
			}

		} while (menu != 4);
	}

	/**
	 * 
	 * @param email
	 * @author Alejandro
	 * @return email
	 */
	public static boolean validarEmail(String email){

		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
						+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(email);
		if (mather.find() == true) {
			System.out.println("El email ingresado es válido.");
			return true;
		} else {
			System.out.println("El email ingresado es inválido.");
			return false;
		}
	}	
}