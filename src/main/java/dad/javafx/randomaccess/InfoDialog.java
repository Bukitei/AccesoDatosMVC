package dad.javafx.randomaccess;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Control de contenido de una residencia
 * Información de una residencia
 * @author Borja David Gómez Alayón
 *
 */

public class InfoDialog extends Alert {

	public InfoDialog(Residencia residencia) {
		
		super(AlertType.INFORMATION);
		
		setTitle("Residencia");
		setHeaderText("Información de la residencia");
		
		GridPane root = new GridPane();
		
		Label idLabel = new Label("ID:");
		Label idTLabel = new Label(String.valueOf(residencia.getId()));
		root.addRow(0, idLabel, idTLabel);
		
		Label nombreLabel = new Label("Nombre:");
		Label nombreTLabel = new Label(residencia.getName());
		root.addRow(1, nombreLabel, nombreTLabel);
		
		Label uniLabel = new Label("Cod. universidad:");
		Label uniTLabael = new Label(residencia.getCodUniversidad());
		root.addRow(2, uniLabel, uniTLabael);
		
		Label precioLabel = new Label("Precio:");
		Label precioTLabel = new Label(String.valueOf(residencia.getPrecio()));
		root.addRow(3, precioLabel, precioTLabel);
		
		Label comedorLabel = new Label("Comedor:");
		Label comedorTLabel = new Label(residencia.getComedorStr());
		root.addRow(4, comedorLabel, comedorTLabel);
		
		root.setHgap(5);
		root.setVgap(5);
		
		getDialogPane().setContent(root);
	}
}
