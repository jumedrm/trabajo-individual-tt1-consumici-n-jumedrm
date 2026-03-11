package modelo;

public class Entidad {
	private int id;
	private String name;
	private String descripcion;
	
	// añado este constructor
    public Entidad(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // mantengo el constructor vacío por si Spring o Jackson lo necesitan
    public Entidad() {
    }
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
