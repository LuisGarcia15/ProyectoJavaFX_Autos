package fes.aragon.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tiendas {
	
	private static Tiendas objeto = new Tiendas();
	private ObservableList<Tienda> grupoTiendas = FXCollections.observableArrayList();
	private boolean modificarTienda = false;
	private int indice = -1;
	private int indiceAuto = -1;
	
	public Tiendas() {
		// TODO Auto-generated constructor stub
	}
	
	public ObservableList<Tienda> getGrupoTiendas() {
		return grupoTiendas;
	}
	public void setGrupoTiendas(ObservableList<Tienda> grupoTiendas) {
		this.grupoTiendas = grupoTiendas;
	}
	public boolean isModificarTienda() {
		return modificarTienda;
	}
	public void setModificarTienda(boolean modificarTienda) {
		this.modificarTienda = modificarTienda;
	}
	public int getIndice() {
		return indice;
	}
	public void setIndice(int indice) {
		this.indice = indice;
	}
	public int getIndiceAuto() {
		return indiceAuto;
	}
	public void setIndiceAuto(int indiceAuto) {
		this.indiceAuto = indiceAuto;
	}
	public static Tiendas getObjeto() {
		return objeto;
	}
	
	
}
