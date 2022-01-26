package entidad;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
	
	public static final String NOMBRE_FICHERO = "coches.dat";

	public static void main(String[] args) throws ClassNotFoundException{
		
		List<Coche> listaCoches = new ArrayList<Coche>();
		
		File file = new File(NOMBRE_FICHERO);
		
		try (FileInputStream fis = new FileInputStream(file);
				 ObjectInputStream ois = new ObjectInputStream(fis);) {
				
				listaCoches = (List<Coche>) ois.readObject();
				
				if(listaCoches.size()>0) {
					System.out.println("Objeto leido\n");
					System.out.println("Imprimiendo coches:\n");
				
					for(Coche c : listaCoches){
						System.out.println("  - " + c);
					}
				} 
			} catch (FileNotFoundException e) {
				// Si nos da este error es porque el fichero no existe
				System.out.println("\nEl fichero coches.dat no se ha encontrado");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		Scanner sc = new Scanner(System.in);
		String opcion = "";
		
		
		boolean continuar = true;
		String id = "";
		
		do{	
			menu();
			opcion = sc.nextLine();
			
			switch(opcion) {
			
			case "1": 
				// Añadir coche
				Coche c = new Coche();
				
				System.out.println("Introduce el id");
				id = sc.nextLine();
				
				// REQUERIMIENTO 3
				// Si el id ya existe en la lista no nos dejará añadirlo
				for(Coche coche: listaCoches) {
					
					if(coche.getId() == Integer.parseInt(id)) {
						System.out.println("Ese ID ya está en la lista");
						System.out.println("Introduce un nuevo ID");
						id = sc.nextLine();
						break;
					}
				}
				
				// Una vez nos aseguramos de que no está repetido, lo guardamos
				c.setId(Integer.parseInt(id));
				
				// REQUERIMIENTO 3
				// Si la matricula existe, pedirá que introduzcamos otra
				String matricula = "";
				System.out.println("Introduce la matricula");
				matricula = sc.nextLine();
				
				for(Coche coche: listaCoches) {
					if(coche.getMatricula().equals(matricula)) {
						System.out.println("Esa matricula ya está en la lista");
						System.out.println("Introduce una nueva matrícula");
						matricula = sc.nextLine();
						break;
					}
				}
				
				// Una vez nos aseguramos de que no está repetido, lo guardamos
				c.setMatricula(matricula);
				
				System.out.println("Introduce la marca");
				c.setMarca(sc.nextLine());
				System.out.println("Introduce el modelo");
				c.setModelo(sc.nextLine());
				System.out.println("Introduce el color");
				c.setColor(sc.nextLine());
				
				listaCoches.add(c);
				System.out.println("Coche añadido");
				
				break;
			
			case "2":
				// Borrar coche por id
				System.out.println("Introduce el id del coche que deseas borrar");
				id = sc.nextLine();
				
				if(listaCoches.size()>0 ) {
					
					boolean encontrado = false;
					
					for(Coche coche: listaCoches){
						
						if(coche.getId() == (Integer.parseInt(id))) {
							listaCoches.remove(coche);
							encontrado = true;
							// Cuando lo encuentra se sale del for
							break;
						} 
					}
					
					if(encontrado) {
						System.out.println("Coche borrado");
					} else {
						System.out.println("Coche no encontrado");
					}
				} else {
					System.out.println("Lista de coches vacia");
				}
				
				break;
				
			case "3":
				// Consulta coche por id
				System.out.println("Introduce el id del coche que deseas consultar");
				id = sc.nextLine();
				boolean existe = false;
				
				for(Coche coche: listaCoches) {
					if (coche.getId() == Integer.parseInt(id)) {
						System.out.println(coche);
						existe = true; // una vez lo encuentra se sale
						break;
						
					}
					
				}
				
				if(existe == false) {
					System.out.println("Ese coche no se encuentra en la lista");
				}
				
				break;
			
			case "4":
				// Listado de coches
				
				if(listaCoches.size() != 0) {
					System.out.println("\nLa lista de coches es la siguiente: \n");
					for(Coche coche : listaCoches){
						System.out.println("  - " + coche);
					}
				} else {
					System.out.println("\nLa lista esta vacia");
				}
				break;
				
			case "5":
				System.out.println("\nHas elegido cerrar el programa");
				
				if(listaCoches.size() > 0) {
					try(FileOutputStream fos = new FileOutputStream(file);
							ObjectOutputStream oos = new ObjectOutputStream(fos);)
					{
						oos.writeObject(listaCoches);
						System.out.println("Objeto introducido");
					} catch (IOException e) {
						e.printStackTrace();
					}	
				} else {
					System.out.println("La lista estaba vacia, por lo que no se"
							+ " ha creado el fichero");
				}
				
				
				continuar = false;
				break;
			
			// REQUERIMIENTO 2
			case "6":
			
				System.out.println("Exportando coches a archivo de texto");

				try(FileWriter fw = new FileWriter("coches.txt");
						BufferedWriter pw = new BufferedWriter(fw);) {
					
					for(Coche coche: listaCoches) {
						
						String impresion = "Coche con id: " + coche.getId();
						impresion += ", matricula: " + coche.getMatricula();
						impresion += ", marca: " + coche.getMarca();
						impresion += ", modelo: " + coche.getModelo();
						impresion += ", color: " + coche.getColor() + "\n";
						pw.write(impresion);
						pw.newLine();
						pw.flush();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

				System.out.println("Fichero creado y rellenado");
				break;
				
			default:
				System.out.println("Opcion incorrecta");
				break;
			}
			
		}while(continuar);
		
		sc.close();
		System.out.println("\n*********** PROGRAMA CERRADO. Bye! :) ************");

	}
	
	public static void menu() {
		System.out.println("\n\t-----MENÚ DE OPCIONES-----");
		System.out.println("\n\t1. Añadir nuevo coche");
		System.out.println("\t2. Borrar coche por id");
		System.out.println("\t3. Consulta coche por id");
		System.out.println("\t4. Listado de coches");
		System.out.println("\t5. Terminar programa");
		System.out.println("\t6. Exportar coches a archivo de texto\n");
		
		System.out.println("Introduce el número de la opción elegida");
	}
	
	
}
