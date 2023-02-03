package fes.aragon.modelo.archivo;

import java.io.Serializable;
import java.util.ArrayList;

import fes.aragon.modelo.Auto;
import fes.aragon.modelo.GerenteVentas;

public class TiendaArchivo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String direccion;
	private String estado;
	private String telefono;
	private String correo;
	private GerenteVentas gerenteVentas = new GerenteVentas();
	private ArrayList<Auto> autos= new ArrayList<>();
	
	 public TiendaArchivo() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String ubicacion) {
		this.direccion = ubicacion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public GerenteVentas getGerenteVentas() {
		return gerenteVentas;
	}

	public void setGerenteVentas(GerenteVentas gerente) {
		this.gerenteVentas = gerente;
	}

	public ArrayList<Auto> getAutos() {
		return autos;
	}

	public void setAutos(ArrayList<Auto> autos) {
		this.autos = autos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
		 
}
