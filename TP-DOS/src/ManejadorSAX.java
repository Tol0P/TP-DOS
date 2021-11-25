import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ManejadorSAX extends DefaultHandler{ //El DefaultHandler nos permite leer etiquetas de apertura, de cierre, texto y atributos del XML
	
	String parametro;
	int tabuladores=0;
	
	//Constructor
	public ManejadorSAX(String parametro) {
		this.parametro=parametro;
	}


	//Detecta la apertura del documento
	public void startDocument() throws SAXException {
		
	}
	
	//Detecta el cierre del documento
	public void endDocument() throws SAXException {
		System.out.print("\n");  // para que el indicador del sistema aparezca al principio de la consola
	}
	

	
	//Detecta la apertura de una etiqueta
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		System.out.print("\n");  // todos los elementos empiezan en una linea nueva
		tabular(tabuladores);	// saltamos tabs en funcion del numero de elementos abiertos y no cerrados	
		if (parametro.equals("/conEtiquetas")) {
			System.out.print(qName+": ");	
		} 
		
		// Sacamos los posibles atributos  Podria tener mas de uno
		if (attributes.getLength()>0) {
			for (int i=0; i<attributes.getLength(); i++) {
				System.out.print(attributes.getQName(i)+"="+attributes.getValue(i)+" ");
			}	
		} 
		tabuladores++;
	}

	
	//Detecta el cierre de una etiqueta
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		tabuladores--;
	}

	
	//Detecta el texto
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String texto=new String(ch, start, length);
		if (!texto.isBlank()) {   // Si el elemento contiene texto
			System.out.print(texto);			
		} 
	} 	

	private static void tabular(int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			System.out.print("\t");
		}
		
	}

}