package tdl2.qatar2022;

public class Pais {
	private String nombre;
	private String idioma;
	
	public Pais() {
		super();
	}
	public Pais(String nombre, String idioma) {
		super();
		this.nombre = nombre;
		this.idioma = idioma;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
}
