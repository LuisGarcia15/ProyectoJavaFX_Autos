package fes.aragon.modelo;

import java.io.Serializable;

public class Usuario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String correoElectronico;
	private String contraseña;
	private String pin;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", correoElectronico=" + correoElectronico + ", contraseña=" + contraseña
				+ ", pin=" + pin + "]\n";
	}
 
}
