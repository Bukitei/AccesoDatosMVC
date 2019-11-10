package dad.javafx.randomaccess;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.StringExpression;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Inserción de una residencia
 * Confirguración de la pestaña
 * @author Borja David Gómez Alayón
 *
 */

public class InsertDialog extends Dialog<Residencia> {

	private static class DialogCheckBinding extends BooleanBinding {

		private StringExpression id, nombre, codUni, precio;
		
		public DialogCheckBinding(StringExpression id, StringExpression nombre,
								  StringExpression codUni, StringExpression precio) {
			
			
			this.id = id;
			this.nombre = nombre;
			this.codUni = codUni;
			this.precio = precio;
			bind(this.id, this.nombre, this.codUni, this.precio);
			
		}
		
		private boolean checkValidFields() {
			
			boolean check;
			
			check = id.get().isEmpty();
			check |= nombre.get().isEmpty();
			check |= codUni.get().isEmpty();
			check |= precio.get().isEmpty();
			 
			return !check && checkIsNumeric(id.get(), precio.get());
		}
		

		private boolean checkIsNumeric(String id, String precio) {
			
			try {
				
				@SuppressWarnings("unused")
				int idVal = Integer.parseInt(id);
				@SuppressWarnings("unused")
				float precioVal = Float.parseFloat(precio);
				
				return true;
				
			} catch( NumberFormatException e) {
				// Devolvemos al final "false"
			}
			
			return false;
		}
		
		@Override
		protected boolean computeValue() {
			return checkValidFields();
		}
		
	}
	
	private ButtonType okButton, cancelButton;
	private TextField id, nombre, codUni, precio;
	private CheckBox comedor;
	private Node buttonInsert;
	
	public InsertDialog(int idResidencia) {
		
		// Los textos
		setTitle("Insertar residencia");
		setHeaderText("Rellenar datos de la residencia");
		setContentText("* Debe rellenar todos los campos\n* Los campos id y precio deben ser numéricos");
		
		// Un bonito icono
		ImageView resiIcon = new ImageView(getClass().getResource("/images/resiIcon.png").toString());
		resiIcon.setFitWidth(48.0f);
		resiIcon.setFitHeight(48.0f);
		setGraphic(resiIcon);
		
		// Los botones
		okButton = new ButtonType("Insertar", ButtonData.OK_DONE);
		cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
		
		// Ahora nuestra vista
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);
		
		Label infoLabel = new Label(getContentText());
		infoLabel.setWrapText(true);
		grid.addRow(0, infoLabel);
		GridPane.setColumnSpan(infoLabel, 2);
		
		Label idLbl = new Label("ID:");
		id = new TextField();
		id.setEditable(false);
		id.setDisable(true);
		id.setText( String.valueOf(idResidencia) );
		id.setPrefColumnCount(2);
		grid.addRow(1, idLbl , id);
		
		Label nombreLbl = new Label("Nombre:");
		nombre = new TextField();
		nombre.setPromptText("Nombre");
		grid.addRow(2,  nombreLbl, nombre);
		
		Label codUniLbl = new Label("Universidad:");
		codUni = new TextField();
		codUni.setPromptText("Código");
		grid.addRow(3,  codUniLbl, codUni);
		
		Label precioLbl = new Label("Precio:");
		precio = new TextField();
		precio.setPromptText("Precio");
		precio.setPrefColumnCount(3);
		grid.addRow(4,  precioLbl, precio);
		
		Label comedorLbl = new Label("Comedor");
		comedor = new CheckBox();
		grid.addRow(5,  comedorLbl, comedor);
		
		// Desactivamos el botón si no cumple con las condiciones
		buttonInsert = getDialogPane().lookupButton(okButton);
		buttonInsert.disableProperty().bind(new DialogCheckBinding(id.textProperty(), nombre.textProperty(), 
																   codUni.textProperty(), precio.textProperty()).not());

		getDialogPane().setContent(grid);
		setResultConverter( bt -> onActionPerformed(bt) );
	}


	private Residencia onActionPerformed(ButtonType bt) {
		
		if( bt == okButton ) {
			// Hemos garantizado que tienen que ser números
			float precioVal = Float.parseFloat(precio.getText());
			int idVal = Integer.parseInt(id.getText());
			
			return new Residencia(idVal, nombre.getText(),
								  codUni.getText(), precioVal,
								  comedor.isSelected());	
			
		}
		
		return null;
	}
	

}
