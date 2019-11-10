package dad.javafx.main;

import dad.javafx.fileaccess.FileAccessController;
import dad.javafx.randomaccess.RandomAccessController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;

/**
 * Control de pestañas
 * Ejecución del programa
 * @author Borja David Gómez Alayón
 *
 */

public class App extends Application {
	
	private FileAccessController tab_controlAccess;
	private RandomAccessController tab_randomAccess; 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/**
		 * Ajustamos el TabPane para que muestre las vistas que preparamos anteriormente 
		 * Decidí no añadir la última pestaña como pestaña vacía
		 * ya que se quitó del ejercicio
		 */
		
		
		TabPane tab = new TabPane();
		
		tab_controlAccess = new FileAccessController();
		Tab tControl = new Tab("Acceso a ficheros");
		tControl.setContent(tab_controlAccess.getRootView());

		tab_randomAccess = new RandomAccessController();
		Tab tRAccess = new Tab("Acceso aleatorio");
		tRAccess.setContent(tab_randomAccess.getRootView());
		
		tab.getTabs().addAll(tControl, tRAccess);
		
		tab.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		Scene scene = new Scene(tab, 800, 600);
		
		primaryStage.setTitle("Acceso a datos");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

	}

}
