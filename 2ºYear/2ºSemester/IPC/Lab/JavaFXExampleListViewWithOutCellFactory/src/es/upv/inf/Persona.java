package es.upv.inf;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona {
	
	private final StringProperty Nombre = new SimpleStringProperty();
	private final StringProperty Apellidos = new SimpleStringProperty();
		
	public Persona(String nombre, String apellidos)
	{
		Nombre.setValue(nombre);
		Apellidos.setValue(apellidos);
	}
	
	public final StringProperty NombreProperty() {
		return this.Nombre;
	}
	public final java.lang.String getNombre() {
		return this.NombreProperty().get();
	}
	public final void setNombre(final java.lang.String Nombre) {
		this.NombreProperty().set(Nombre);
	}
	public final StringProperty ApellidosProperty() {
		return this.Apellidos;
	}
	public final java.lang.String getApellidos() {
		return this.ApellidosProperty().get();
	}
	public final void setApellidos(final java.lang.String Apellidos) {
		this.ApellidosProperty().set(Apellidos);
	}

}