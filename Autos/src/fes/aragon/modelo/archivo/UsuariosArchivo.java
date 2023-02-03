package fes.aragon.modelo.archivo;

import java.io.Serializable;
import java.util.ArrayList;

import fes.aragon.modelo.Usuario;

public class UsuariosArchivo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static UsuariosArchivo usuarios = new UsuariosArchivo();
	private ArrayList <Usuario> listaUsuarios = new ArrayList<>();
	
	public ArrayList<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public static UsuariosArchivo getUsuarios() {
		return usuarios;
	}
	
	
}
