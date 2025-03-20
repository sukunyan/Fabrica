package Modelo;

import java.util.ArrayList;

public class SQL {
	
	private String Usuario;
	private String Correo;
	private String Contrasenia;
	private ArrayList<SQL> user = new ArrayList<>();
	
	/**
	 * @param usuario
	 * @param correo
	 * @param contraseña
	 */
	public SQL(String usuario, String correo, String contrasenia, ArrayList<SQL> user) {
		this.Usuario = usuario;
		this.Correo = correo;
		this.Contrasenia = contrasenia;
		this.user = user;
	}
	
	public SQL() {
		this.Usuario = "";
		this.Correo = "";
		this.Contrasenia = "";
		this.user = new ArrayList<>();
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return Usuario;
	}
	
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		Usuario = usuario;
	}
	
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return Correo;
	}
	
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		Correo = correo;
	}
	
	/**
	 * @return the contraseña
	 */
	public String getContrasenia() {
		return Contrasenia;
	}
	
	/**
	 * @param contraseña the contraseña to set
	 */
	public void setContrasenia(String contrasenia) {
		Contrasenia = contrasenia;
	}

	/**
	 * @return the user
	 */
	public ArrayList<SQL> getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(SQL User) {
		this.user.add(User);
	}
	
}
