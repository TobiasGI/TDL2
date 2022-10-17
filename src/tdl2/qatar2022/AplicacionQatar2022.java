package tdl2.qatar2022;
import java.sql.*;
import java.util.Scanner;


public class AplicacionQatar2022 {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/qatar2022","sr","pssw");
			int opcion = -1;
			while (opcion!=0) {
				System.out.println("Elija una opcion:\n1 : Ingresar pais.\n2 : Ingresar furbolista.\n3 : Ingresar sede.\n4 : Editar sede.\n5 : Eliminar sede.\n0 : Salir");
				opcion = sc.nextInt();
				switch (opcion) {
				case 5:
					eliminarSede(con);
					break;
				case 4:
					editarSede(con);
					break;
				case 3: 
					ingresarSede(con);
					break;
				case 2: 
					ingresarFutbolista(con);
					break;
				case 1: 
					ingresarPais(con);
					break;
				case 0: 
					System.out.println("Usted cerro el menu.");
					break;
				default: System.out.println("Error: Opcion invalida.");
				}
			}
		}catch(java.sql.SQLException e){
			System.out.println("Error de SQL: "+e.getMessage());
		}
	}
	
	public static Pais buscarPais(String nombre,Connection con) {
		Pais p=null;
		try{
			 Statement st = con.createStatement();
			 ResultSet rs= st.executeQuery("SELECT * FROM pais");
			 while (rs.next() && !(rs.getString("nombre").equals(nombre))){
				 }
			 if (rs.getString("nombre").equals(nombre)) {
				 p = new Pais();
				 p.setNombre(rs.getString("nombre"));
				 p.setIdioma(rs.getString("idioma"));
			 }
			 rs.close();
			 st.close();
			 con.close();
		 } catch (java.sql.SQLException e) {
			 System.out.println("Error de SQL: "+e.getMessage());
		 }
		 return p;
	}
// NO SE SI HAY Q CHECKEAR Q NO EXISTA, DEBERIA CHECKEAR CON TELEFONO MAS Q CON NOMBRE	
	public static Futbolista buscarFutbolista(String nombre,Connection con) {
		Futbolista f=null;
		try{
			 Statement st = con.createStatement();
			 ResultSet rs= st.executeQuery("SELECT nombre FROM futbolista");
			 while (rs.next() && !(rs.getString("nombre").equals(nombre))){
				 }
			 if (rs.getString("nombre").equals(nombre)) {
				 f = new Futbolista();
				 f.setNombre(rs.getString("nombre"));
				 f.setApellido(rs.getString("apellido"));
				 f.setDocId(rs.getInt("docIdentidad"));
				 f.setTel�fono(rs.getInt("telefono"));
				 f.setEmail(rs.getString("mail"));
			 }
			 rs.close();
			 st.close();
			 con.close();
		 } catch (java.sql.SQLException e) {
			 System.out.println("Error de SQL: "+e.getMessage());
		 }
		 return f;
	}
	public static Sede buscarSede(String nombre,Connection con) {
		
		return null;
	}
// INGRESAR PAIS	
	public static void ingresarPais(Connection con) {
		System.out.println("Ingresar pais: ");
		String nombre = sc.nextLine();
		if (buscarPais(nombre,con)!=null) System.out.println("El pais ingresado ya existe.");
		else {
			System.out.println("Ingresar idioma ");
			String idioma = sc.nextLine();
			try{
				 Statement st = con.createStatement();
				 int res=st.executeUpdate("INSERT INTO pais (nombre,idioma) VALUES('"+nombre+"','"+idioma+"')");
				 if (res==0) System.out.println("Agregado exitosamente");
				 else System.out.println("Ocurrio un error");
				 st.close();
				 con.close();
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
		System.out.println("Ingresar documento de identidad: ");
		int docIdentidad = sc.nextInt();
		sc.nextLine();
		System.out.println("Ingresar telefono: ");
		int telefono = sc.nextInt();
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
				 rs= st.executeQuery("SELECT nombre FROM pais WHERE idpais = "+paisID);
				 System.out.println("�Quiere agregar a "+nombre+" "+apellido+" a "+rs.getString("pais")+"? SI / NO");
				 String opcion = sc.nextLine();
				 boolean valido = false;
				 while (!valido) {
					 if (opcion.equals("SI")) {
						 valido = true;
						 // INSERTAR DATOS
						 int res=st.executeUpdate("INSERT INTO futbolista (nombre,apellido,docIdentidad,telefono,mail,idpais) VALUES('"+nombre+"','"+apellido+"','"+docIdentidad+"','"+telefono+"','"+mail+"','"+paisID+"')");
						 if (res==0) System.out.println("Agregado exitosamente");
						 else System.out.println("Ocurrio un error");
					 } else {
						 if (opcion.equals("NO")) {
							 valido = true;
						 } else {
							 System.out.println("Invalido");
							 System.out.println("�Quiere agregar a "+nombre+" "+apellido+" a "+rs.getString("pais")+"? SI / NO");
							 opcion = sc.nextLine();
						 }
					 }
				 }
				 rs.close();
				 st.close();
				 con.close();
			 } catch (java.sql.SQLException e) {
				 System.out.println("Error de SQL: "+e.getMessage());
			 }
	}	
// INGRESAR SEDE	
	public static void ingresarSede(Connection con) {
		
	}
//EDITAR SEDE	
	public static void editarSede(Connection con) {
		System.out.println("Ingresar sede: ");
		String nombre = sc.nextLine();
			try{
				if (buscarSede(nombre,con)==null) System.out.println("La sede ingresada no existe.");
				else {
					Statement st = con.createStatement();	
					System.out.println("Ingresar capacidad: ");
					String capacidad = sc.nextLine();
					System.out.println("Ingresar pais: ");
					String pais = sc.nextLine();
					ResultSet rs= st.executeQuery("SELECT idpais FROM pais WHERE nombre = "+pais);
					int res=st.executeUpdate("INSERT INTO pais (nombre,capacidad,idpais) VALUES('"+nombre+"','"+capacidad+"','"+rs.getInt("idpais")+"')");
					if (res==0) System.out.println("Editado exitosamente");
					else System.out.println("Ocurrio un error");
					rs.close();
					st.close();
					con.close();
				}
			 } catch (java.sql.SQLException e) {
				 System.out.println("Error de SQL: "+e.getMessage());
			 }
	}
//ELIMINAR SEDE	(NO SE COMO ELIMINAR, MAS QUE PONER TODA LA FILA EN BLANCO)
	public static void eliminarSede(Connection con) {
		
	}
}

