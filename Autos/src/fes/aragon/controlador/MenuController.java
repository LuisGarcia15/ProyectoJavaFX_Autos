package fes.aragon.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fes.aragon.modelo.GerenteVentas;
import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import fes.aragon.modelo.UsuarioInvitado;
import fes.aragon.modelo.archivo.TiendaArchivo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MenuController extends BaseController implements Initializable {

	@FXML
	private Button btnAbrir;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnGuardar;

	@FXML
	private Button btnModificar;

	@FXML
	private Button btnMostrar;

	@FXML
	private Button btnNuevo;

	@FXML
	private Button btnSalir;

	@FXML
	private TableColumn<Tienda, String> clmCorreo;

	@FXML
	private TableColumn<Tienda, GerenteVentas> clmGerente;

	@FXML
	private TableColumn<Tienda, String> clmDireccion;

	@FXML
	private TableColumn<Tienda, String> clmEstado;

	@FXML
	private TableColumn<Tienda, String> clmNombre;

	@FXML
	private TableColumn<Tienda, String> clmTelefono;

	@FXML
	private TableColumn<GerenteVentas, String> clmRFC;

	@FXML
	private TableView<Tienda> tblTabla;

	@FXML
	void abrirTienda(ActionEvent event) {
		try {
			this.abrirArchivo(btnAbrir);
		} catch (ClassNotFoundException | IOException e) {
			this.ventanaEmergente("Mensaje", "Problema en el archivo",
					"Hubo un problema en el archivo, " + "consulta al programador");
			e.printStackTrace();
		}
	}

	@FXML
	void eliminarTienda(ActionEvent event) {
		int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
		if (indice >= 0) {
			BaseController.indice = indice;
			BaseController.tabla = this.tblTabla;
			BaseController.nombreVentana = "EliminarTienda";
			BaseController.btn.add(this.btnEliminar);
			this.nuevaVentana("ConfirmarAccion");
		} else {
			ventanaEmergente("Error", "Seleccione una fila", "Seleccione una fila valida para borrar.");
		}
	}

	@FXML
	void guardarTienda(ActionEvent event) throws IOException {
		BaseController.nombreVentana = "GuardarTienda";
		BaseController.btn.add(this.btnGuardar);
		this.nuevaVentana("ConfirmarAccion");
	}

	@FXML
	void modificarTienda(ActionEvent event) {
		int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
		if (indice >= 0) {
			Tiendas.getObjeto().setModificarTienda(true);
			Tiendas.getObjeto().setIndice(indice);
			BaseController.btn.add(this.btnModificar);
			BaseController.nombreVentana = "ModificarTienda";
			this.nuevaVentana("ConfirmarAccion");
		} else {
			ventanaEmergente("Error", "Seleccione una fila", "Seleccione una fila valida para modificar.");
		}
	}

	@FXML
	void mostrarInformacion(ActionEvent event) {
		int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
		if (indice >= 0) {
			Tiendas.getObjeto().setIndice(indice);
			BaseController.btn.add(this.btnMostrar);
			this.nuevaVentana("DatosTienda");
		} else {
			ventanaEmergente("Error", "Seleccione una fila", "Seleccione una fila valida para modificar.");
		}
	}

	@FXML
	void nuevaTienda(ActionEvent event) {
		Tiendas.getObjeto().setIndice(-1);
		Tiendas.getObjeto().setModificarTienda(false);
		Tiendas.getObjeto().setIndiceAuto(-1);
		Tiendas.getObjeto().getGrupoTiendas().add(new Tienda());
		BaseController.btn.add(this.btnNuevo);
		BaseController.nombreVentana = "Tienda";
		this.nuevaVentana("ConfirmarAccion");
	}

	@FXML
	void salir(ActionEvent event) {
		if (BaseController.modificacionValida) {
			BaseController.btn.add(this.btnEliminar);
			this.nuevaVentana("AvisoGuardado");
		} else {
			UsuarioInvitado.setInvitado(false);
			BaseController.btn.clear();
			BaseController.modificacionValida = false;
			this.cerrarVentana(btnSalir);
			this.nuevaVentana("Inicio");
			if (BaseController.usuarioInicioSesion != null) {
				this.ventanaEmergente("Salida", "Hasta Luego",
						"Vuelve pronto usuario: " + BaseController.usuarioInicioSesion.getUsuario());
				BaseController.usuarioInicioSesion = null;
			} else {
				this.ventanaEmergente("Salida", "Hasta Luego", "Vuelve pronto usuario: Invitado");
			}
		}
	}

	private void deshabilitar(boolean valor) {
		this.btnAbrir.setDisable(valor);
		this.btnEliminar.setDisable(valor);
		this.btnGuardar.setDisable(valor);
		this.btnModificar.setDisable(valor);
		this.btnNuevo.setDisable(valor);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File f = new File(System.getProperty("user.dir") + "/Tiendas/tiendas.fes");
		try {
			FileInputStream fi = new FileInputStream(f);
			ObjectInputStream lectura = new ObjectInputStream(fi);
			@SuppressWarnings("unchecked")
			ArrayList<TiendaArchivo> tiendaAr = (ArrayList<TiendaArchivo>) lectura.readObject();
			Tiendas.getObjeto().getGrupoTiendas().clear();
			for (TiendaArchivo tiendaArchivo : tiendaAr) {
				Tienda tienda = new Tienda();
				tienda.setAutos(FXCollections.observableArrayList(tiendaArchivo.getAutos()));
				tienda.setCorreo(tiendaArchivo.getCorreo());
				tienda.setDireccion(tiendaArchivo.getDireccion());
				tienda.setEstado(tiendaArchivo.getEstado());
				tienda.setGerenteVentas(tiendaArchivo.getGerenteVentas());
				tienda.setNombre(tiendaArchivo.getNombre());
				tienda.setTelefono(tiendaArchivo.getTelefono());
				Tiendas.getObjeto().getGrupoTiendas().add(tienda);
			}
			this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			this.clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
			this.clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
			this.clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
			this.clmGerente.setCellValueFactory(new PropertyValueFactory<>("gerenteVentas"));
			this.clmRFC.setCellValueFactory(new PropertyValueFactory<>("Rfc"));
			this.clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			this.tblTabla.setItems(Tiendas.getObjeto().getGrupoTiendas());
			this.deshabilitar(UsuarioInvitado.isInvitado());
			lectura.close();
			fi.close();
		} catch (FileNotFoundException e) {
			this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			this.clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
			this.clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
			this.clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
			this.clmGerente.setCellValueFactory(new PropertyValueFactory<>("gerenteVentas"));
			this.clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			this.clmRFC.setCellValueFactory(new PropertyValueFactory<>("Rfc"));
			this.tblTabla.setItems(Tiendas.getObjeto().getGrupoTiendas());
			this.deshabilitar(UsuarioInvitado.isInvitado());
		} catch (IOException e) {
			this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			this.clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
			this.clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
			this.clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
			this.clmGerente.setCellValueFactory(new PropertyValueFactory<>("gerenteVentas"));
			this.clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			this.clmRFC.setCellValueFactory(new PropertyValueFactory<>("Rfc"));
			this.tblTabla.setItems(Tiendas.getObjeto().getGrupoTiendas());
			this.deshabilitar(UsuarioInvitado.isInvitado());
		} catch (ClassNotFoundException e) {
			this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
			this.clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
			this.clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
			this.clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
			this.clmGerente.setCellValueFactory(new PropertyValueFactory<>("gerenteVentas"));
			this.clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
			this.clmRFC.setCellValueFactory(new PropertyValueFactory<>("Rfc"));
			this.tblTabla.setItems(Tiendas.getObjeto().getGrupoTiendas());
			this.deshabilitar(UsuarioInvitado.isInvitado());
		}

	}

}
