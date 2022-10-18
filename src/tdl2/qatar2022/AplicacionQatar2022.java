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
					Utiles.eliminarSede(con);
					break;
				case 4:
					Utiles.editarSede(con);
					break;
				case 3: 
					Utiles.ingresarSede(con);
					break;
				case 2: 
					Utiles.ingresarFutbolista(con);
					break;
				case 1: 
					Utiles.ingresarPais(con);
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

}

