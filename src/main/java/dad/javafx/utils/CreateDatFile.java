package dad.javafx.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Creación en caso de no tener archivo
 * Si no hay archivo lo crea
 * @author Borja David Gómez Alayón
 *
 */
public class CreateDatFile {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		CreateDatFile datFile = new CreateDatFile();
	}
	public CreateDatFile() {
		
		RandomAccessFile file = null;
		
		try {
			file = new RandomAccessFile("miFichero.dat", "rw");
			
			file.setLength(0);
			// Escribimos uno de jemplo
			file.writeInt(1);
			file.writeChar(',');
			file.writeChars("NuevaResi ");
			file.writeChar(',');
			file.writeChars("000001");
			file.writeChar(',');
			file.writeFloat(1200);
			file.writeChar(',');
			file.writeBoolean(true);
			file.writeChar(',');
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if( file != null ) {
					file.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
