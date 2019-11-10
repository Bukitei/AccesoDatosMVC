package dad.javafx.fileaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;

import java.io.IOException;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;
import java.util.Optional;



import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


/**
 * Pestaña de acceso a fichero
 * Controlador
 * @author Borja David Gómez Alayón
 *
 */

public class FileAccessController {

	private FileAccessModel model = new FileAccessModel();
	private FileAccessView view = new FileAccessView();
	
	private File rutaActual;
	
	public FileAccessController() {
		
		
		// Aún no sabemos como se interactúa, de momento bidireccional
		model.rutaProperty().bindBidirectional(view.getRutaTxt().textProperty());
		
	    
		// Por acción del botón, mostramos la lista de ficheros, luego es el root el que se modifica a partir del model, y el model a partir del evento
		view.getFileList().itemsProperty().bind(model.fileListProperty());
		
		// File List
	    model.fileProperty().bind(view.getFileList().getSelectionModel().selectedItemProperty());
	    view.getNombreFichTxt().textProperty().bindBidirectional(model.fileNameProperty());

		// En este caso, puesto que son los botones los que nos indican el mostrar el contenido y el modificarlo, es un bindeo hacia el modelo
		view.getContentArea().textProperty().bindBidirectional(model.contentProperty());
		
		view.getFolderBt().selectedProperty().bindBidirectional(model.isFolderProperty());
	    view.getFichBt().selectedProperty().bindBidirectional(model.isFileProperty());
		
		// Eventos de botones
		view.getFileList().getSelectionModel().selectedItemProperty().addListener((o, lv, nv) -> onFileSelectionChanged(nv));
		
		view.getViewBt().setOnAction( e -> onFolderViewAction(e)); 
		view.getCreateBt().setOnAction( e -> onCreateAction(e));
		view.getMoveBt().setOnAction(e -> onMoveAction(e));
		view.getRemoveBt().setOnAction(e -> onRemoveAction(e));
		view.getContentBt().setOnAction( e -> {
			try {
				onContentViewAction(e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}); 
		view.getModBt().setOnAction(e -> onModifyAction(e));
		
		// Temporalmente lo hacemos aquí para realizar pruebas
		rutaActual = new File(System.getProperty("user.home")+"\\Prueba_ficheros");
		model.setRuta(rutaActual.toString());
		
	}
	

	private void onFileSelectionChanged(File nv) {
		
		// Aquí tenemos que comprobar el fichero que se ha seleccionado
		if( nv != null ) {
			
			model.setFileName(nv.getName());
			model.setIsFile(nv.isFile());
			model.setIsFolder(nv.isDirectory());
		}
		
		else {
			model.setFileName("");
			model.setIsFile(false);
			model.setIsFolder(false);
		}
	}


	private void onMoveAction(ActionEvent e) {
		
		/**
		 * Obtenemos el fichero deseado y lo movemos 
		 * con un renameto de la clase File
		 */
		File file = model.getFile();
		File file2 = new File(model.getRuta() + "/" +model.getFileName());
		
		file.renameTo(file2);
	}

	private void onModifyAction(ActionEvent e){
		
		/**
		 * A la hora de modificar el fichero el se graba en un formato
		 * diferente al usado por Word, si se abre con este nos mostrará el
		 * texto sin espacios ni saltos de línea.
		 */
		FileOutputStream file = null;
		OutputStreamWriter output = null;
		BufferedWriter writer = null;
		
		try {
			file = new FileOutputStream(rutaActual);
			output = new OutputStreamWriter(file, StandardCharsets.UTF_8);
			writer = new BufferedWriter(output);
			
			writer.write(model.getContent() + "\n");
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Se ha producido un error");
			alert.setContentText("No se sabe el por qué");
			alert.showAndWait();
			e1.printStackTrace();
		}finally {
			try {
				if(writer != null) {
					writer.close();
				}
				if(output != null) {
					output.close();
				}
				if(file != null) {
					file.close();
				}
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Se ha producido un error");
				alert.setContentText("Error al cerrar la escritura");
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	private void onContentViewAction(ActionEvent e) throws IOException {
		
		//Visualización del contenido
		File file = model.getFile();
		rutaActual = file;
		
		FileReader fr = new FileReader (file);
        BufferedReader br = new BufferedReader(fr);
        
        String line;
        String str = "";
        
        while((line=br.readLine())!=null)
        	str += line + "\n";
        
        model.setContent(str);
        
        fr.close();
        br.close();
	}

	

	private void onRemoveAction(ActionEvent e) {
		
		/**
		 * A la hora de sacar los mensajes de alerta 
		 * comprobé si quería eliminar un fichero o 
		 * un directorio, sacando una alerta según sus acciones
		 */
		
		File file = model.getFile();
		
		if(file.isDirectory()) {
			//Si es directorio saca este, confirmando que desea hacerlo
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Confirmación");
			confirm.setHeaderText("¿Seguro/a que quieres eliminar la carpeta "+model.getFileName()+"?");
			confirm.setContentText("Si aceptas la carpeta será eliminada");
			
			Optional<ButtonType> result = confirm.showAndWait();
			if(result.get() == ButtonType.OK) {
				if(file.delete()) {
					//Si no hubo problemas con la eliminación lo notifica
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle("Eliminado");
					info.setHeaderText("La carpeta ha sido eliminada");
					info.setContentText("No ha habido problemas en la eliminación");
					info.showAndWait();
					onFolderViewAction(e);
				}else {
					/**
					 * Si falla es porque la carpeta tiene contenido,
					 * por ello sacamos una segunda confirmación de si quiere eliminarla
					 * con todo su contenido
					 */
					Alert confirm2 = new Alert(AlertType.CONFIRMATION);
					confirm2.setTitle("Confirmación");
					confirm2.setHeaderText("La carpeta parece tener contenido");
					confirm2.setContentText("¿Seguro que quieres eliminar la carpeta?");
					
					result = confirm2.showAndWait();
					if(result.get() == ButtonType.OK) {
						//Si aceptó lo elimina sin molestar más al usuario
						File[] file2 = file.listFiles();
						for(int i = 0; i < file2.length; i++) {
							file2[i].delete();
						}
						file.delete();
						onFolderViewAction(e);
					}else {
						//Notifica al usuario de que canceló la acción
						Alert info = new Alert(AlertType.INFORMATION);
						info.setTitle("Cancelado");
						info.setHeaderText("La carpeta no ha sido eliminada");
						info.setContentText("No han habido cambios");
						info.showAndWait();
					}
				}
			}
			
		}else {
			
			/**
			 * Si no era directorio es porque era archivo, así que procede a ejecutar
			 * el mismo proceso pero sin posibilidad de comprobar si hay algo dentro,
			 * aunque confirma antes de hacerlo por seguridad	
			 */
					
						Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setTitle("Confirmación");
				confirm.setHeaderText("¿Seguro/a que quieres eliminar el fichero "+model.getFileName()+"?");
				confirm.setContentText("Si aceptas el fichero será eliminado");
				
				Optional<ButtonType> result = confirm.showAndWait();
				if(result.get() == ButtonType.OK) {
					if(file.delete()) {
						Alert info = new Alert(AlertType.INFORMATION);
						info.setTitle("Eliminado");
						info.setHeaderText("El fichero ha sido eliminado");
						info.setContentText("No ha habido problemas en la eliminación");
						info.showAndWait();
						onFolderViewAction(e);
					}else {
						//Si ha habido un error significa que el fichero está en uso.
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("Error");
						error.setHeaderText("Ha habido un error");
						error.setContentText("No se pudo eliminar el fichero, comprueba que no está en uso y vuelvelo a intentar");
					}
				}else {
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle("Cancelado");
					info.setHeaderText("El fichero no ha sido eliminado");
					info.setContentText("No han habido cambios");
					info.showAndWait();
					onFolderViewAction(e);
				}
			}
	}

	private void onCreateAction(ActionEvent e) {
		
		// Creamos un fichero en la ruta actual
		File file = new File(model.getRuta() + "/" + model.getFileName());
		
		if(model.isFileProperty().get()) {
			try {
				file.createNewFile();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
		}else {
			file.mkdir();
		}
		
		
		onFolderViewAction(e);
	}

	private void onFolderViewAction(ActionEvent e) {

		// Aquí montamos la lista de ficheros y directorios
		model.getFileList().clear();
		
		rutaActual = new File(model.getRuta());
		
		File[] myFiles = rutaActual.listFiles();
		
		model.getFileList().addAll(myFiles);
	}

	public FileAccessView getRootView() {
		return view;
	}
}
