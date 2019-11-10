package dad.javafx.randomaccess;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase de referencia Residencia
 * Clase para tabla
 * @author Borja David Gómez Alayón
 *
 */

public class Residencia {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty codUniversidad = new SimpleStringProperty();
	private FloatProperty precio = new SimpleFloatProperty();
	private StringProperty comedorStr = new SimpleStringProperty();
	private BooleanProperty comedor = new SimpleBooleanProperty();
	
	public Residencia(int id, String name, String codUniversidad, float precio, boolean comedor ) {
		
		setId(id);
		setName(name);
		setCodUniversidad(codUniversidad);
		setPrecio(precio);
		setComedor(comedor);
		
		// En la tabla el texto no se verá bien si no lo ajustamos, por lo que dependiendo
		//de lo que ponga en comedor ajustamos la respuesta a un si o un no
		comedorStr.bind( Bindings
				.when(this.comedor)
				.then(new SimpleStringProperty("Si"))
				.otherwise(new SimpleStringProperty("No"))
				);
	}
	
	public final IntegerProperty idProperty() {
		return this.id;
	}
	
	public final int getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	
	public final StringProperty nameProperty() {
		return this.name;
	}
	
	public final String getName() {
		return this.nameProperty().get();
	}
	
	public final void setName(final String name) {
		this.nameProperty().set(name);
	}
	
	public final StringProperty codUniversidadProperty() {
		return this.codUniversidad;
	}
	
	public final String getCodUniversidad() {
		return this.codUniversidadProperty().get();
	}
	
	public final void setCodUniversidad(final String codUniversidad) {
		this.codUniversidadProperty().set(codUniversidad);
	}
	
	public final FloatProperty precioProperty() {
		return this.precio;
	}
	
	public final float getPrecio() {
		return this.precioProperty().get();
	}
	
	public final void setPrecio(final float precio) {
		this.precioProperty().set(precio);
	}
	
	public final BooleanProperty comedorProperty() {
		return this.comedor;
	}
	
	public final boolean isComedor() {
		return this.comedorProperty().get();
	}
	
	public final void setComedor(final boolean comedor) {
		this.comedorProperty().set(comedor);
	}

	public final StringProperty comedorStrProperty() {
		return this.comedorStr;
	}
	

	public final String getComedorStr() {
		return this.comedorStrProperty().get();
	}
	

	public final void setComedorStr(final String comedorStr) {
		this.comedorStrProperty().set(comedorStr);
	}
	
	
	
}
