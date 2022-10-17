package tdl2.qatar2022;

public class Futbolista {
	private String nombre;
	private String apellido;
	private int docId;
	private int tel�fono;
	private String email;
	private Pais pa�s;
	
	public Futbolista() {
		super();
	}
	public Futbolista(String nombre, String apellido, int docId, int tel�fono, String email, Pais pa�s) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.docId = docId;
		this.tel�fono = tel�fono;
		this.email = email;
		this.pa�s = pa�s;
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
	public int getTel�fono() {
		return tel�fono;
	}
	public void setTel�fono(int tel�fono) {
		this.tel�fono = tel�fono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Pais getPa�s() {
		return pa�s;
	}
	public void setPa�s(Pais pa�s) {
		this.pa�s = pa�s;
	}
	public void setPa�s(String nombre, String idioma) {
		Pais p = new Pais(nombre,idioma);
		this.pa�s = p;
	}
}
