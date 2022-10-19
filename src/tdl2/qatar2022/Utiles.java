package tdl2.qatar2022;

import java.sql.*;
import java.util.Scanner;

public class Utiles {
	public Utiles() {
		super();
		// TODO Auto-generated constructor stub
	}
	static Scanner sc = new Scanner(System.in);
	// BUSCAR PAIS (funciona)
		public static Pais buscarPais(String nombre,Connection con) {
			Pais p=null;
			try{
				 Statement st = con.createStatement();
				 ResultSet rs= st.executeQuery("SELECT * FROM pais");
				 boolean valido = true;
				 while (rs.next() && valido){
						 if (rs.getString("nombre").equals(nombre)) {
							 p = new Pais();
							 p.setNombre(rs.getString("nombre"));
							 p.setIdioma(rs.getString("idioma"));
							 valido=false;
						 }
					 }
				 rs.close();
				 st.close();
			 } catch (java.sql.SQLException e) {
				 System.out.println("Error de SQL: "+e.getMessage());
			 }
			 return p;
		}
	// BUSCAR FUTBOLISTA
		public static Futbolista buscarFutbolista(int telefono,Connection con) {
			Futbolista f=null;
			try{
				 Statement st = con.createStatement();
				 ResultSet rs= st.executeQuery("SELECT * FROM futbolista");
				 boolean valido = true;
				 while (rs.next() && valido){
					 if (rs.getInt("telefono")==telefono) {
						f = new Futbolista();
						f.setNombre(rs.getString("nombre"));
						f.setApellido(rs.getString("apellido"));
						f.setDocId(rs.getInt("docIdentidad"));
						f.setTeléfono(rs.getInt("telefono"));
						f.setEmail(rs.getString("email"));
						int idpais = rs.getInt("idpais");
						PreparedStatement pst = con.prepareStatement("SELECT * FROM pais WHERE idpais = ?");
						pst.clearParameters();
						pst.setInt(1,idpais);
						ResultSet rs2 = pst.executeQuery();
						rs2.next();
						f.setPaís(rs2.getString("nombre"),rs2.getString("idioma"));
						valido = false;
						rs2.close();
						pst.close();
					}
				}
				 rs.close();
				 st.close();
			 } catch (java.sql.SQLException e) {
				 System.out.println("Error de SQL: "+e.getMessage());
			 }
			 return f;
		}
// BUSCAR SEDE
		public static Sede buscarSede(String nombre,Connection con) {
			Sede s=null;
			try{
				Statement st = con.createStatement();
				ResultSet rs= st.executeQuery("SELECT * FROM sede");
				boolean valido = true;
				while (rs.next() && valido){
						if (rs.getString("nombre").equals(nombre)) {
							s = new Sede();
							s.setNombre(rs.getString("nombre"));
							s.setCapacidad(rs.getInt("capacidad"));
							int idpais = rs.getInt("idpais");
							PreparedStatement pst = con.prepareStatement("SELECT * FROM pais WHERE idpais = ?");
							pst.clearParameters();
							pst.setInt(1,idpais);
							ResultSet rs2 = pst.executeQuery();
							rs2.next();
							s.setPais(rs2.getString("nombre"),rs2.getString("idioma"));
							valido=false;
							rs2.close();
						}
					}
				rs.close();
				st.close();
			} catch (java.sql.SQLException e) {
				System.out.println("Error de SQL: "+e.getMessage());
			}
			return s;
		}
	// INGRESAR PAIS	
		public static void ingresarPais(Connection con) {
			System.out.println("Ingresar pais: ");
			String nombre = sc.nextLine();
			if (buscarPais(nombre,con)!=null) System.out.println("El pais ingresado ya existe.");
			else {
				System.out.println("Ingresar idioma: ");
				String idioma = sc.nextLine();
				try{
					PreparedStatement pst = con.prepareStatement("INSERT INTO pais (nombre,idioma) VALUES( ? , ? )");
					pst.clearParameters();
					pst.setString(1,nombre);System.out.println();
					pst.setString(2,idioma);
					pst.executeUpdate();
					System.out.println("Agregado exitosamente");
					pst.close();
				} catch (java.sql.SQLException e) {
					System.out.println("Error de SQL: "+e.getMessage());
				}
			}
		}
	// INGRESAR FUTBOLISTA
		public static void ingresarFutbolista(Connection con) {
			// LEER DATOS
			System.out.println("Ingresar futbolista: ");
			String nombre = sc.nextLine();
			System.out.println("Ingresar apellido: ");
			String apellido = sc.nextLine();
			System.out.println("Ingresar telefono: ");
			int telefono = sc.nextInt();
			sc.nextLine();
			if (buscarFutbolista(telefono,con)!=null) System.out.println("El futbolista ingresado ya existe.");
			else {
				System.out.println("Ingresar documento de identidad: ");
				int docIdentidad = sc.nextInt();
				sc.nextLine();
				System.out.println("Ingresar mail: ");
				String mail = sc.nextLine();
					try{
						Statement st = con.createStatement();
						ResultSet rs= st.executeQuery("SELECT idpais,nombre FROM pais");
						System.out.println("Elija pais:");
						while (rs.next()){
							System.out.println(rs.getInt("idpais")+" : "+rs.getString("nombre"));
						}
						int paisID = sc.nextInt();
						sc.nextLine(); 
						PreparedStatement pst = con.prepareStatement("SELECT * FROM pais WHERE idpais = ?");
						pst.clearParameters();
						pst.setInt(1,paisID);
						ResultSet rs2 = pst.executeQuery();
						rs2.next();
						 
						System.out.println("¿Quiere agregar a "+nombre+" "+apellido+" a "+rs2.getString("nombre")+"? SI / NO");
						String opcion = sc.nextLine();
						
						while (!opcion.equals("SI") && !opcion.equals("NO")) {
							System.out.println("Opcion invalida, intente de nuevo");
							opcion = sc.nextLine();
						}
							 if (opcion.equals("SI")) {
								 // INSERTAR DATOS
								 pst = con.prepareStatement("INSERT INTO futbolista (nombre,apellido,docIdentidad,telefono,email,idpais) VALUES(?,?,?,?,?,?)");
								 pst.setString(1,nombre);
								 pst.setString(2,apellido);
								 pst.setInt(3,docIdentidad);
								 pst.setInt(4,telefono);
								 pst.setString(5, mail);
								 pst.setInt(6,paisID );
								 pst.executeUpdate();
								 pst.close();
								 System.out.println("Agregado exitosamente");
							 }
						rs2.close();
						rs.close();
						st.close();
						pst.close();
					 } catch (java.sql.SQLException e) {
						System.out.println("Error de SQL: "+e.getMessage());
					 }
			}
		}	
	// INGRESAR SEDE	
		public static void ingresarSede(Connection con) {
			System.out.println("Ingresar sede: ");
			String nombre = sc.nextLine();
			if (buscarSede(nombre,con)!=null) System.out.println("La sede ingresado ya existe.");
			else {
				try{
					System.out.println("Ingresar capacidad: ");
					int capacidad = sc.nextInt();
					sc.nextLine();
					System.out.println("Ingresar pais: ");
					String pais = sc.nextLine();
					PreparedStatement pst = con.prepareStatement("SELECT * FROM pais WHERE nombre = ?");
					pst.clearParameters();
					pst.setString(1,pais);
					ResultSet rs = pst.executeQuery();
					rs.next();
					pst = con.prepareStatement("INSERT INTO sede (nombre,capacidad,idpais) VALUES(?,?,?)");
					pst.clearParameters();
					pst.setString(1,nombre);
					pst.setInt(2,capacidad);
					pst.setInt(3,rs.getInt("idpais"));
					pst.executeUpdate();
					pst.close();
				 } catch (java.sql.SQLException e) {
					System.out.println("Error de SQL: "+e.getMessage());
				 }
			}
		}
		
	//EDITAR SEDE	
		public static void editarSede(Connection con) {
			System.out.println("Ingresar sede: ");
			String nombre = sc.nextLine();
				try{
					if (buscarSede(nombre,con)==null) System.out.println("La sede ingresada no existe.");
					else {
						System.out.println("Ingresar capacidad: ");
						int capacidad = sc.nextInt();
						sc.nextLine();
						System.out.println("Ingresar pais: ");
						String pais = sc.nextLine();
						if (buscarPais(nombre,con)==null) {
							System.out.println("El pais ingresado no existe.\nDesea agregarlo a la base de datos? SI / NO");
							String opcion = sc.nextLine();
							while (!opcion.equals("SI") && !opcion.equals("NO")) {
								System.out.println("Opcion invalida, intente de nuevo");
								opcion = sc.nextLine();
							}
							if (opcion.equals("SI")) ingresarPais(con);
							else {
								System.out.println("Elija un pais valido:");
								Statement st = con.createStatement();
								ResultSet rs= st.executeQuery("SELECT idpais,nombre FROM pais");
								while (rs.next()){
									System.out.println(rs.getInt("idpais")+" : "+rs.getString("nombre"));
								}
								int paisID = sc.nextInt();
								PreparedStatement pst = con.prepareStatement("SELECT nombre FROM pais WHERE idpais = ?");
								pst.clearParameters();
								pst.setInt(1,paisID);
								ResultSet rs2 = pst.executeQuery();
								rs2.next();
								pais = rs2.getString("nombre");
								pst.close();
								rs2.close();
							}
						}
						PreparedStatement pst = con.prepareStatement("SELECT * FROM pais WHERE nombre =?");
						pst.clearParameters();
						pst.setString(1,pais);
						ResultSet rs= pst.executeQuery();
						rs.next();
						pst = con.prepareStatement("UPDATE sede SET capacidad=? , idpais=? where nombre =?");
						pst.clearParameters();
						pst.setInt(1,capacidad);
						pst.setInt(2,rs.getInt("idpais"));
						pst.setString(3,nombre);
						pst.executeUpdate();
						rs.close();
						pst.close();
						System.out.println("Editado exitosamente.");
					}
				 } catch (java.sql.SQLException e) {
					 System.out.println("Error de SQL: "+e.getMessage());
				 }
		}
	//ELIMINAR SEDE	
		public static void eliminarSede(Connection con) {
			System.out.println("Ingresar sede: "); // DEBERIA CAMBIARSE POR UN LISTAR SEDES, Y QUE ELIJAN UNA
			String nombre = sc.nextLine();
				try{
					if (buscarSede(nombre,con)==null) System.out.println("La sede ingresada no existe.");
					else {
						PreparedStatement pst = con.prepareStatement("DELETE FROM sede WHERE nombre = ?");
						pst.clearParameters();
						pst.setString(1,nombre);
						pst.executeUpdate();
						System.out.println("Eliminado exitosamente");
						pst.close();
					}
				 } catch (java.sql.SQLException e) {
					 System.out.println("Error de SQL: "+e.getMessage());
				 }
		}
}
