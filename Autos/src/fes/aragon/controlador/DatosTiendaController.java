package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DatosTiendaController extends BaseController implements Initializable{
	private Tienda tienda;
	
    @FXML
    private Button btnListaAutos;

    @FXML
    private Button btnSalir;

    @FXML
    private TextField txtCorreoGerente;

    @FXML
    private TextField txtCorreoTienda;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtEdad;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtNombreCompletoGerente;

    @FXML
    private TextField txtNombreTienda;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtTelefonoGerente;

    @FXML
    private TextField txtrFCGerente;

    @FXML
    void abrirListaAutos(ActionEvent event) {
    	BaseController.btn.add(this.btnListaAutos);
    	this.nuevaVentana("ListaAutos");
    }

    @FXML
    void salir(ActionEvent event) {
    	BaseController.btn.remove(BaseController.btn.size()-1);
    	this.cerrarVentana(btnSalir);
    }
    
    private void deshabilitar(boolean valor){
		this.txtNombreTienda.setDisable(valor);
		this.txtCorreoGerente.setDisable(valor);
		this.txtCorreoTienda.setDisable(valor);
		this.txtDireccion.setDisable(valor);
		this.txtEdad.setDisable(valor);
		this.txtEstado.setDisable(valor);
		this.txtNombreCompletoGerente.setDisable(valor);
		this.txtNombreTienda.setDisable(valor);
		this.txtrFCGerente.setDisable(valor);
		this.txtTelefono.setDisable(valor);
		this.txtTelefonoGerente.setDisable(valor);
		
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		deshabilitar(true);
		tienda = Tiendas.getObjeto().getGrupoTiendas().get(
				Tiendas.getObjeto().getIndice());
		txtNombreTienda.setText(tienda.getNombre());
		txtCorreoGerente.setText(tienda.getGerenteVentas().getCorreo());
		txtCorreoTienda.setText(tienda.getCorreo());
		txtDireccion.setText(tienda.getDireccion());
		txtEdad.setText(String.valueOf(tienda.getGerenteVentas().getEdad()));
		txtEstado.setText(tienda.getEstado());
		txtNombreCompletoGerente.setText(
				tienda.getGerenteVentas().getNombre() + " " + 
				tienda.getGerenteVentas().getApellidoPaterno() + " " +
				tienda.getGerenteVentas().getApellidoMaterno()		
				);
		txtNombreTienda.setText(tienda.getNombre());
		txtrFCGerente.setText(tienda.getGerenteVentas().getRfc());
		txtTelefono.setText(tienda.getTelefono());
		txtTelefonoGerente.setText(tienda.getGerenteVentas().getTelefono());
	}

}
