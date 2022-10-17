package tdl2.qatar2022;

public class Futbolista {
	private String nombre;
	private String apellido;
	private int docId;
	private int teléfono;
	private String email;
	private Pais país;
	
	public Futbolista() {
		super();
	}
	public Futbolista(String nombre, String apellido, int docId, int teléfono, String email, Pais país) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.docId = docId;
		this.teléfono = teléfono;
		this.email = email;
		this.país = país;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public int getTeléfono() {
		return teléfono;
	}
	public void setTeléfono(int teléfono) {
		this.teléfono = teléfono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Pais getPaís() {
		return país;
	}
	public void setPaís(Pais país) {
		this.país = país;
	}
	public void setPaís(String nombre, String idioma) {
		Pais p = new Pais(nombre,idioma);
		this.país = p;
	}
}
