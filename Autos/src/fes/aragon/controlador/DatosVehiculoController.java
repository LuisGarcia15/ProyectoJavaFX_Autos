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

public class DatosVehiculoController extends BaseController implements Initializable{
	private Tienda tienda;
	
	  @FXML
	    private Button btnSalir;

	    @FXML
	    private TextField txtAño;

	    @FXML
	    private TextField txtCantidad;

	    @FXML
	    private TextField txtEstado;

	    @FXML
	    private TextField txtKilometraje;

	    @FXML
	    private TextField txtMarca;

	    @FXML
	    private TextField txtModelo;

	    @FXML
	    private TextField txtNumeroPuertas;

	    @FXML
	    private TextField txtPrecio;

	    @FXML
	    private TextField txtTransmision;
	    
	    @FXML
	    private TextField txtColor;

    @FXML
    void salir(ActionEvent event) {
    	this.cerrarVentana(btnSalir);
    }
    
    private void deshabilitar(boolean valor){
		this.txtAño.setDisable(valor);
		this.txtCantidad.setDisable(valor);
		this.txtEstado.setDisable(valor);
		this.txtKilometraje.setDisable(valor);
		this.txtMarca.setDisable(valor);
		this.txtModelo.setDisable(valor);
		this.txtNumeroPuertas.setDisable(valor);
		this.txtPrecio.setDisable(valor);
		this.txtTransmision.setDisable(valor);	
		this.txtColor.setDisable(valor);	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tienda = Tiendas.getObjeto().getGrupoTiendas().get(
				Tiendas.getObjeto().getIndice());
		this.deshabilitar(true);
		this.txtAño.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getAño());
		this.txtCantidad.setText(String.valueOf(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getCantidad()));
		this.txtEstado.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getEstado());
		this.txtKilometraje.setText(String.valueOf(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getKilometraje()));
		this.txtMarca.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getMarca());
		this.txtModelo.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getModelo());
		this.txtNumeroPuertas.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getNumeroDePuertas());
		this.txtPrecio.setText(String.valueOf(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getPrecio()));
		this.txtTransmision.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getTransmision());
		this.txtColor.setText(tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto()).getColor());
	}

}
