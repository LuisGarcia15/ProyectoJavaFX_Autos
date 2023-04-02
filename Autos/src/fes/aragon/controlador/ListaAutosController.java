package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Auto;
import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import fes.aragon.modelo.UsuarioInvitado;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaAutosController extends BaseController implements Initializable{
	private Tienda tienda;
	
    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnMostrar;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnSalir;

    @FXML
    private TableColumn<Auto,String> clmAnio;

    @FXML
    private TableColumn<Auto, String> clmEstado;

    @FXML
    private TableColumn<Auto, String> clmKilometraje;

    @FXML
    private TableColumn<Auto, String> clmMarca;

    @FXML
    private TableColumn<Auto, String> clmModelo;

    @FXML
    private TableColumn<Auto, String> clmNumPuertas;

    @FXML
    private TableColumn<Auto, String> clmTrasmision;
    
    @FXML
    private TableColumn<Auto, String> clmColor;
    
    @FXML
    private TableColumn<Auto, String> clmCantidad;
    
    @FXML
    private TableColumn<Auto, String> clmPrecio;

    @FXML
    private TableView<Auto> tblTabla;

    @FXML
    void eliminarAuto(ActionEvent event) {
    	BaseController.indice = this.tblTabla.getSelectionModel().getSelectedIndex();
		BaseController.tabla = this.tblTabla;
    	BaseController.nombreVentana = "EliminarAuto";
    	BaseController.btn.add(this.btnEliminar);
    	this.nuevaVentana("ConfirmarAccion");
    }

    @FXML
    void modificarAuto(ActionEvent event) {
    	int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		Tiendas.getObjeto().setIndiceAuto(indice);
    		BaseController.nombreVentana = "ModificarAuto";
    		BaseController.btn.add(this.btnModificar);
    		this.nuevaVentana("ConfirmarAccion");
    	}else {
    		this.ventanaEmergente("Mensaje", "Error", "Por favor seleccione una fila.");
    	}
    }

    @FXML
    void mostrarInformacion(ActionEvent event) {
    int indice = this.tblTabla.getSelectionModel().getSelectedIndex();
    	if(indice >= 0) {
    		Tiendas.getObjeto().setIndiceAuto(indice);
    		this.nuevaVentana("DatosAutos");
    	}else {
    		this.ventanaEmergente("Mensaje", "Error", "Por favor seleccione una fila.");
    	}
    }

    @FXML
    void nuevoAuto(ActionEvent event) {
    	BaseController.nombreVentana = "AgregarNuevoAuto";
    	BaseController.btn.add(this.btnNuevo);
		this.nuevaVentana("ConfirmarAccion");
    }

    @FXML
    void salir(ActionEvent event) {
    	BaseController.btn.remove(BaseController.btn.size()-1);
    	this.cerrarVentana(btnSalir);
    }
    
    private void deshabilitar(boolean valor){
		this.btnEliminar.setDisable(valor);
		this.btnModificar.setDisable(valor);
		this.btnNuevo.setDisable(valor);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.tienda = Tiendas.getObjeto().getGrupoTiendas().get(
				Tiendas.getObjeto().getIndice());
		this.clmAnio.setCellValueFactory(new PropertyValueFactory<>("año"));
		this.clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		this.clmKilometraje.setCellValueFactory(new PropertyValueFactory<>("kilometraje"));
		this.clmMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		this.clmModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
		this.clmNumPuertas.setCellValueFactory(new PropertyValueFactory<>("numeroDePuertas"));
		this.clmTrasmision.setCellValueFactory(new PropertyValueFactory<>("transmision"));
		this.clmColor.setCellValueFactory(new PropertyValueFactory<>("color"));
		this.clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
		this.clmPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		this.tblTabla.setItems(tienda.getAutos());
		this.deshabilitar(UsuarioInvitado.isInvitado());
	}

}
