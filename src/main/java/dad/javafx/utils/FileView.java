package dad.javafx.utils;

import java.io.File;

/**
 * Clase de soporte para ver el fichero
 * Vista del fichero
 * @author Borja David Gómez Alayón
 *
 */

@SuppressWarnings("serial")
public class FileView extends File {



	
	public FileView(File f) {
		super(f.toString());
	}
	
	public FileView(String pathname) {
		super(pathname);
	}	

	
	@Override
	public String toString() {
		return this.getName();
	}

}
