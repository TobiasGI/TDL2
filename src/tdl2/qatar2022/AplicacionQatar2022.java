package tdl2.qatar2022;
import java.sql.*;
import java.util.Scanner;


public class AplicacionQatar2022 {
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		try{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mundial_futbol_2022","root","");
			int opcion = -1;
			while (opcion!=0) {
				System.out.println("Elija una opcion:\n1 : Ingresar pais.\n2 : Ingresar futbolista.\n3 : Ingresar sede.\n4 : Editar sede.\n5 : Eliminar sede.\n0 : Salir");
				opcion = sc.nextInt();
				sc.nextLine();
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
// NO SE SI HAY Q CHECKEAR Q NO EXISTA, DEBERIA CHECKEAR CON TELEFONO MAS Q CON NOMBRE	
	public static Futbolista buscarFutbolista(int telefono,Connection con) {
		Futbolista f=null;
		try{
			 Statement st = con.createStatement();
			 ResultSet rs= st.executeQuery("SELECT * FROM futbolista");
			 while (rs.next() && !(rs.getInt("telefono")==telefono)){
				 }
			 if (rs.getInt("telefono")==telefono) {
				 f = new Futbolista();
				 f.setNombre(rs.getString("nombre"));
				 f.setApellido(rs.getString("apellido"));
				 f.setDocId(rs.getInt("docIdentidad"));
				 f.setTeléfono(rs.getInt("telefono"));
				 f.setEmail(rs.getString("mail"));
				 int idpais = rs.getInt("idpais");
				 rs= st.executeQuery("SELECT * FROM pais WHERE idpais = "+idpais); 
				 f.setPaís(rs.getString("nombre"),rs.getString("idioma"));
			 }
			 rs.close();
			 st.close();
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
			System.out.println("Ingresar idioma: ");
			String idioma = sc.nextLine();
			try{
				 Statement st = con.createStatement();
				 st.executeUpdate("INSERT INTO pais (nombre,idioma) VALUES('"+nombre+"','"+idioma+"')");
				 System.out.println("Agregado exitosamente");
				 st.close();
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
					 rs= st.executeQuery("SELECT nombre FROM pais WHERE idpais = "+paisID);
					 System.out.println("¿Quiere agregar a "+nombre+" "+apellido+" a "+rs.getString("pais")+"? SI / NO");
					 String opcion = sc.nextLine();
					 boolean valido = false;
					 while (!valido) {
						 if (opcion.equals("SI")) {
							 valido = true;
							 // INSERTAR DATOS
							 st.executeUpdate("INSERT INTO futbolista (nombre,apellido,docIdentidad,telefono,mail,idpais) VALUES('"+nombre+"','"+apellido+"','"+docIdentidad+"','"+telefono+"','"+mail+"','"+paisID+"')");
							 System.out.println("Agregado exitosamente");

						 } else {
							 if (opcion.equals("NO")) {
								 valido = true;
							 } else {
								 System.out.println("Invalido");
								 System.out.println("¿Quiere agregar a "+nombre+" "+apellido+" en "+rs.getString("pais")+"? SI / NO");
								 opcion = sc.nextLine();
							 }
						 }
					 }
					 rs.close();
					 st.close();
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
				Statement st = con.createStatement();
				System.out.println("Ingresar capacidad: ");
				int capacidad = sc.nextInt();
				sc.nextLine();
				System.out.println("Ingresar pais: ");
				String pais = sc.nextLine();
				ResultSet rs= st.executeQuery("SELECT * FROM pais WHERE nombre = "+pais);
				int res=st.executeUpdate("INSERT INTO sede (nombre,capacidad,idpais) VALUES('"+nombre+"','"+capacidad+"','"+rs.getInt("idpais")+"')");
				if (res==0) System.out.println("Agregado exitosamente");
				else System.out.println("Ocurrio un error");
				st.close();
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
					Statement st = con.createStatement();	
					System.out.println("Ingresar capacidad: ");
					int capacidad = sc.nextInt();
					sc.nextLine();
					System.out.println("Ingresar pais: ");
					String pais = sc.nextLine();
					ResultSet rs= st.executeQuery("SELECT * FROM pais WHERE nombre = "+pais);
					int res=st.executeUpdate("UPDATE sede (nombre,capacidad,idpais) VALUES('"+nombre+"','"+capacidad+"','"+rs.getInt("idpais")+"') where nombre ="+nombre);
					if (res==0) System.out.println("Editado exitosamente");
					else System.out.println("Ocurrio un error");
					rs.close();
					st.close();
				}
			 } catch (java.sql.SQLException e) {
				 System.out.println("Error de SQL: "+e.getMessage());
			 }
	}
//ELIMINAR SEDE	
	public static void eliminarSede(Connection con) {
		System.out.println("Ingresar sede: ");
		String nombre = sc.nextLine();
			try{
				if (buscarSede(nombre,con)==null) System.out.println("La sede ingresada no existe.");
				else {
					Statement st = con.createStatement();	
					st.executeUpdate("DELETE FROM sede WHERE nombre = "+nombre);
					System.out.println("Eliminado exitosamente");
					st.close();
				}
			 } catch (java.sql.SQLException e) {
				 System.out.println("Error de SQL: "+e.getMessage());
			 }
	}
}

