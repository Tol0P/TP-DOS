import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



//	Dudas sobre el programa:



public class Principal {
	
	public static Scanner sc=new Scanner (System.in);
	
	static boolean todoCorrecto1=false;
	static boolean todoCorrecto2=false;
	

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		
		//para que el do while sea infinito sin quitar el "salir"
		boolean infinito=false;
		
		
		
		//Variables
		String carpetaActual;
		
		String orden;
		
		String ordenDividida[]=new String [3];
		
		
		//Info para que quede wapo
		System.out.println("TP-DOS [Version 1.0]\n(c) TPascual Corporation. Todos los derechos reservados\n");
		
		
		do {
			
			carpetaActual=new java.io.File(".").getCanonicalPath();	//Para poner en que ruta estamos
			System.out.print(carpetaActual+">");
			
			//Pedir orden
			orden=sc.nextLine();

			ordenDividida=orden.split(" ");	//metemos todas las palabras en un array (hace lo que hicimos pero solo :') )
			
			/*
			//Pruebas
			if (!ordenDividida[0].equals("ERROR")) {
				for (int i = 0; i < ordenDividida.length; i++) {
					
					System.out.println(ordenDividida[i]);
				}
				
			}else {
				System.out.println("Comando incorrecto");
			}
			*/
			
			
			
			if (ordenDividida.length<=3) {
				switch (ordenDividida[0]) {
/*Funciona*/	case "mueve": //llamar al metodo mueve(ordenDividida[1], ordenDividida[2])
					if (ordenDividida.length==3) {
						mueve(ordenDividida[1], ordenDividida[2]);	//(usaremos un copia() junto a elimina())
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "copia": //llamar al metodo copia(ordenDividida[1], ordenDividida[2])
					if (ordenDividida.length==3) {
						copia(ordenDividida[1], ordenDividida[2], true);
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "elimina": //llamar al metodo elimina(ordenDividida[1])
					if (ordenDividida.length==2) {
						todoCorrecto1=true;
						elimina(ordenDividida[1], true); 
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "lista": //llamar al metodo lista(ordenDividida[1])
					if (ordenDividida.length==2) {
						lista(ordenDividida[1]);
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "listaArbol": //llamar al metodo listaArbol(ordenDividida[1])
					if (ordenDividida.length==2) {
						listaArbol(ordenDividida[1], true, 1); 
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "comparaTXT": //llamar al metodo comparaTXT(ordenDividida[1], ordenDividida[2])
					if (ordenDividida.length==3) {
						comparaTXT(ordenDividida[1], ordenDividida[2]);
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "muestraTXT": //llamar al metodo muestraTXT(ordenDividida[1])
					if (ordenDividida.length==2) {
						muestraTXT(ordenDividida[1]);
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

/*Funciona*/	case "muestraXML": //llamar al metodo muestraXML(ordenDividida[1], ordenDividida[2])
					if (ordenDividida.length==3) {
						muestraXML(ordenDividida[1], ordenDividida[2]); //(ni puta idea julio)
					}else {
						System.out.println("Numero de parametros incorrectos para el comando");
					}
					break;

				default:
					if (/*!orden.equals("salir")&&*/ !orden.equals("")) {
						
						System.out.println("\"" + ordenDividida[0] + "\" no se reconoce como un comando del sistema o demasiados parametros");
						
					}
					break;

				}
			}else {
				System.out.println("Comando inexistente o con demasiados parametros");
			}

		} while (!infinito		/*!orden.equals("salir")*/);	//descomentar para que salga al poner salir
																//descomentar tambien en el default
		//System.out.println("\nHasta pronto :)");

	}


	private static void muestraXML(String ruta, String parametro) throws ParserConfigurationException, SAXException, IOException {
		
		if(parametro.equals("/sinEtiquetas") || parametro.equals("/conEtiquetas")) {
		
			SAXParserFactory factory = SAXParserFactory.newInstance(); // Sirve para poder crear objetos de tipo
																		// SaxParser
			SAXParser lectorXML = factory.newSAXParser(); // Sirve para poder leer los ficheros de tipo XML
			
			if(ruta.substring(ruta.length()-4, ruta.length()).equals(".xml")) { //Sirve para saber si el final del fichero es .xml
				
				File ficheroXML = new File(ruta); // Creamos el File con la ruta

				if (ficheroXML.exists() && ficheroXML.isFile()) {

					ManejadorSAX manejador = new ManejadorSAX(parametro); // Este objeto sirve para poder leer los XML

					lectorXML.parse(ficheroXML, manejador); // Ejecuta la lectura del XML pasandole el fichero y el
															// manejador

				} else {
					System.out.println("Fichero inexistente");
				}
			}else {
				System.out.println("El fichero no es un .xml");
			}
			
		}else {
			System.out.println("Parametros incorrectos");
		}

	}


	private static void comparaTXT(String fichero1, String fichero2) {
				
		
		int contador=0;
		
		BufferedReader lectorBuffered1 = null;	//lector fichero1
		BufferedReader lectorBuffered2 = null;	//lector fichero2
		try {
			lectorBuffered1 = new BufferedReader(new FileReader(fichero1));
			lectorBuffered2 = new BufferedReader(new FileReader(fichero2));
			
			// Leer la primera línea y lo guardamos en la variable linea
			String linea1 = lectorBuffered1.readLine();
			String linea2 = lectorBuffered2.readLine();
			
			System.out.println("Lineas diferentes: ");
			
			// Repetir mientras no se llegue al final del fichero
			while (linea1 != null && linea2 != null) {
				
				contador++;	//saber que linea es
				
				// mostrar las lineas si no son iguales
				if (!linea1.equals(linea2)) {
					
					System.out.println("Linea: "+contador);
					
					System.out.println("Fichero1: "+linea1);
					System.out.println("Fichero2: "+linea2);
					System.out.println();
				}
				
				// Leer la siguiente línea
				linea1 = lectorBuffered1.readLine();
				linea2 = lectorBuffered2.readLine();
			}
			
			
			//Cuando el fichero 2 se acaba, sigue leyendo el fichero 1
			if(linea1 != null) {
				while (linea1!=null) {
					
					contador++;
					
					System.out.println("Linea: "+contador);
					
					System.out.println("Fichero1: "+linea1);
					System.out.println("Fichero2: ");
					System.out.println();
					
					linea1 = lectorBuffered1.readLine();
					
				}
			}
			
			//Cuando el fichero 1 se acaba, sigue leyendo el fichero 2
			if(linea2 != null) {
				while (linea2!=null) {
					
					contador++;
					
					System.out.println("Linea: "+contador);
					
					System.out.println("Fichero1: ");
					System.out.println("Fichero2: "+linea2);
					
					System.out.println();
					
					linea2 = lectorBuffered2.readLine();
					
				}
			}
			
			
		} catch (FileNotFoundException e) {
			System.out.println("Error: Fichero no encontrado");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println("Error de lectura del fichero");
			System.out.println(e.getMessage());
		} finally {
			try {
				if (lectorBuffered1 != null)
					lectorBuffered1.close();
				
				if (lectorBuffered2 != null)
					lectorBuffered2.close();
				
			} catch (Exception e) {
				System.out.println("Error al cerrar el fichero");
				System.out.println(e.getMessage());
			}
		}
		
	}

	
	private static void tabular(int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			System.out.print("\t");
		}
		
	}
	

	private static void listaArbol(String ruta, boolean verCabecera, int tabuladores) {
		File rutaFichero = new File (ruta);
		
		if (rutaFichero.exists() && rutaFichero.isDirectory()) {
			if (verCabecera == true) { //para poner la ruta entera
				System.out.println("ListaArbol " + ruta + "\n");

			} else { //salta los tabuladores necesarios y pone el nombre del directorio (para los subdirectorios)
				tabular(tabuladores - 1);
				System.out.println(rutaFichero.getName());
			}
			File ficheros[] = rutaFichero.listFiles();
			for (int i = 0; i < ficheros.length; i++) {

				if (ficheros[i].isDirectory()) { //Recursividad, si es un directorio llamamos a este mismo metodo con un tabulador mas (vaya lio)
					listaArbol(ficheros[i].getAbsolutePath(), false, tabuladores + 1);
				} else {
					tabular(tabuladores);
					System.out.println(ficheros[i].getName());
				}
			} 
		
		
		}else {
			System.out.println("Carpeta inexistente");
		}
	}


	private static void lista(String ruta) {
		
		
		
		File punteroList = new File(ruta);

		File[] listado = null;

		listado = punteroList.listFiles();

		System.out.println("Lista " + ruta);

		//Info de los directorios
		SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss");

		for (int i = 0; i < listado.length; i++) {
			File archivo = listado[i];
			
			if (listado[i].isFile()) {
				System.out.println(String.format("%s %s %s %s %s",
										formatoFechaHora.format(archivo.lastModified()),
										"\t",
										"\t",
										archivo.length()+"\t",
										archivo.getName()));
			}
			
			if (listado[i].isDirectory()) {
				System.out.println(String.format("%s %s %s %s %s %s",
										formatoFechaHora.format(archivo.lastModified()),
										"\t",
										"<DIR>",
										"\t",
										"\t",
										archivo.getName()));
			}
		}
		System.out.println();
					
	}


	private static void mueve(String rutaInicial, String rutaDestino) {
		boolean llamado=false;
		
		copia(rutaInicial, rutaDestino, llamado);
		elimina(rutaInicial, llamado);
		
		if (todoCorrecto1==true && todoCorrecto2==true) {
			//Este mensaje solo tiene mostraarse si todo ha ido bien
			System.out.println("El fichero se ha movido con exito");
		}
		
	}


	private static void copia(String rutaFicheroCopiada, String rutaFicheroPegar, boolean llamado) {
		
		//Files
		File ficheroLeido = null;
		File rutaDestino = null;
		File ficheroDestino = null;
				
		// Array
		int[] cadena = null;

		boolean correcto = true; // Para saber que todo va bien
		
		todoCorrecto1=false;

		// Comprobar si el fichero existe
		ficheroLeido = new File(rutaFicheroCopiada); // Para conocer las caracteristicas del fichero (hacer el getName)
		if (ficheroLeido.exists() == false) {
			correcto = false;

			System.out.println("Imposible abrir el fichero origen");
		}

		// Comprobar si la ruta destino existe
		rutaDestino = new File(rutaFicheroPegar); // Para saber si la carpeta existe
		if (rutaDestino.exists() && rutaDestino.isDirectory() && correcto==true) {
			
			if(rutaFicheroPegar.substring(rutaFicheroPegar.length()-1).equals("\\")) {
				
				rutaFicheroPegar = rutaFicheroPegar + ficheroLeido.getName();
				
			}else {
				rutaFicheroPegar = rutaFicheroPegar + File.separator + ficheroLeido.getName();
			}
			

		}else if (correcto == true) {
			// Comprobar si el fichero destino existe y preguntar si quiere sobreescribir
			
				
			ficheroDestino = new File(rutaFicheroPegar);
			if (ficheroDestino.exists()) {
				System.out.println("Fichero existente, desea sobreescribir? (s/n)");
				String confirmado = sc.nextLine();

				if (confirmado.equals("n")) {
					correcto = false;
				}
			}
		
		}else {
			correcto = false;
			System.out.println("Ruta de destino inexistente");
		}

		

		if (correcto == true) {
			try {
				FileInputStream punteroLectura = new FileInputStream(rutaFicheroCopiada); // Recorrer el fichero

				try {
					// Creamos el array con la longitud del fichero extraida con el available
					cadena = new int[punteroLectura.available()];

					// Comenzamos a leer el fichero y lo guardamos en el array

					for (int i = 0; i < cadena.length; i++) {

						cadena[i] = punteroLectura.read();
					}

					punteroLectura.close(); // cerramos el fichero lectura

					// Escribimos el array en el fichero
					try {
						FileOutputStream punteroEscritura = new FileOutputStream(rutaFicheroPegar);

						for (int i = 0; i < cadena.length; i++) {

							punteroEscritura.write(cadena[i]);
						}

						punteroEscritura.close();// Cerramos el fichero de escritura

						if (llamado==true) {
							// Le decimos que ha salido todo bien
							System.out.println("Fichero copiado correctamente");
						}
						
						todoCorrecto1=true;

					} catch (Exception e) {
						System.out.println("Error al crear el fichero, carpeta de destino inexistente");
					}

				} catch (IOException e) {
					System.out.println("Error al crear el Array");
				}

			} catch (FileNotFoundException e1) {
				System.out.println("Imposible abrir el fichero");	
			}
		}
	}


	private static void muestraTXT(String ruta) {

		if ((ruta.substring(ruta.length()-4, ruta.length()).equals(".txt"))) {
			try (FileReader fr = new FileReader(ruta)) {

				System.out.println("Contenido del documento:\n");

				//Leemos el fichero y lo mostramos por pantalla
				int valor = fr.read();
				while (valor != -1) {
					System.out.print((char) valor);
					valor = fr.read();
				}
				System.out.println("\n");

			} catch (IOException e) {
				System.out.println("Error al leer el fichero de la ruta: " + ruta);
			} 
		}else {
			System.out.println("El fichero no es un .txt");
		}
	}


	private static void elimina(String ruta, boolean llamado) {
		
		todoCorrecto2=false;
		
		if (todoCorrecto1==true) {
			File rutaFichero = new File(ruta);
			if (rutaFichero.exists()) {
				if (rutaFichero.delete() == true) { //que se ha borrado
					if (llamado == true) {
						System.out.println("Carpeta/fichero eliminado con exito");
					}
					todoCorrecto2 = true;

				} else {
					System.out.println("No se ha podido eliminar la carpeta/fichero");
				}

			} else {
				System.out.println("Carpeta/fichero inexistente");
			} 
		}
		
	}



	//Codigo que se tardó hora y media en hacer que funcione y despues se descubrió que hay un metodo aposta llamado split :'(
	/*
	private static String[] dividirOrden(String orden) {
		
		String palabras[] = new String [3];
		
		int espacio1;	//entre comando y primer parametro
		int espacio2;	//entre primer parametro y segundo parametro
		int espacio3;	//para saber si hay mas de 3 palabras
		
		
		espacio1=orden.indexOf(" ");
		if(espacio1==-1) {
			palabras[0]=orden;	//si solo tiene una palabra
		}else {
			palabras[0]=orden.substring(0, espacio1);	//si tiene minimo 2 palabras
			
			espacio2=orden.indexOf(" ", espacio1+1);	//buscamos si tiene 3 palabras
			
			if(espacio2==-1) {	//Si no hay 3 palabras
				
				palabras[1]=orden.substring(espacio1+1);	//la segunda palabra es desde el primer espacio hasta el final
				
			}else {	//sino es porque hay 3 palabras
				palabras[1]=orden.substring(espacio1+1, espacio2);	//metemos la segunda palabra

				palabras[2]=orden.substring(espacio2+1);	//metemos la tercera palabra
				
				//miramos que no tenga mas de 3 palabras
				espacio3=orden.indexOf(" ", espacio2+1);
				if(espacio3!=-1) {
					palabras[0]="ERROR";
				}
			}
		}
		
		
		return palabras;
	}
	*/

}
