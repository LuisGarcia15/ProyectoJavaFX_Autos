package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Auto;
import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import fes.aragon.modelo.TipoError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NuevoAutoController extends BaseController implements Initializable{
	private Tienda tienda;
	private String mensaje = "";
	
    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ChoiceBox<String> chcCantidad;

    @FXML
    private ChoiceBox<String> chcEstado;

    @FXML
    private ChoiceBox<String> chcTransmision;
    
    @FXML
    private ChoiceBox<String> chcAnio;
    
    @FXML
    private ChoiceBox<String> chcNumPuertas;
    
    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPrecio;
    
    @FXML
    private TextField txtKilometraje;
    
    @FXML
    private TextField txtColor;

    @FXML
    void cancelarAuto(ActionEvent event) {
    	BaseController.modificarAuto = true;
    	this.cerrarVentana(btnCancelar);
    }

    @FXML
    void nuevoAuto(ActionEvent event) {
    	if(this.verificar()) {
    		Auto auto = new Auto();
    		auto.setAnio(this.chcAnio.getValue());
    		auto.setEstado(this.chcEstado.getValue());
    		auto.setKilometraje(Double.parseDouble(this.txtKilometraje.getText()));
    		auto.setMarca(this.txtMarca.getText());
    		auto.setModelo(this.txtModelo.getText());
    		auto.setNumeroDePuertas(this.chcNumPuertas.getValue());
    		auto.setTransmision(this.chcTransmision.getValue());
    		auto.setPrecio(Double.parseDouble(this.txtKilometraje.getText()));
    		auto.setCantidad(this.chcCantidad.getValue());
    		auto.setColor(this.txtColor.getText());
    		tienda.getAutos().add(auto);
    		BaseController.modificarAuto = false;
    		this.cerrarVentana(btnAceptar);
    	}	else {
    		ventanaEmergente("Error", "Datos Incorrectos", this.mensaje);
    		mensaje = "";
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		this.verificarEntrada(txtPrecio, TipoError.PRECIO);
		this.verificarEntrada(txtKilometraje, TipoError.KM);
		
		this.chcAnio.getItems().addAll("Seleccione una opcion:", "2010","2011","2012"
				,"2013","2014", "2015", "2016", "2017", "2018", "2019", "2020",
				"2021", "2022","2023", "2024", "2025");
		this.chcEstado.getItems().addAll("Seleccione una opcion:", "Nuevo", "Seminuevo");
		this.chcTransmision.getItems().addAll("Seleccione una opcion: ", "Estandar",
				"Automático");
		this.chcCantidad.getItems().addAll("Seleccione una opcion:", "1","2","3","4","5","6","7","8","9","10");
		this.chcNumPuertas.getItems().addAll("Seleccione una opcion:", "1","2","3","4","5","6","7","8","9","10");
		
		this.chcAnio.getSelectionModel().select(0);
		this.chcEstado.getSelectionModel().select(0);
		this.chcTransmision.getSelectionModel().select(0);
		this.chcCantidad.getSelectionModel().select(0);
		this.chcNumPuertas.getSelectionModel().select(0);
		
			tienda = Tiendas.getObjeto().getGrupoTiendas().get(
					Tiendas.getObjeto().getGrupoTiendas().size()-1);
	}
	
	private boolean verificar() {
		boolean valido = true;
		if ((this.txtMarca.getText() == null)
				|| (this.txtMarca.getText() != null && this.txtMarca.getText().isEmpty())) {
			this.mensaje += "La marca no es valido, es vacio\n";
			valido = false;
		}
		
		if ((this.txtModelo.getText() == null)
				|| (this.txtModelo.getText() != null && this.txtModelo.getText().isEmpty())) {
			this.mensaje += "El modelo no es valido, es vacio\n";
			valido = false;
		}

		if ((this.chcAnio.getSelectionModel().getSelectedIndex() == 0)
				|| (this.chcAnio.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "Seleccione el anio de vehiculo\n";
			valido = false;
		}

		if ((this.chcEstado.getSelectionModel().getSelectedIndex() == 0)
				|| (this.chcEstado.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "Seleccione el estado del vehiculo\n";
			valido = false;
		}
		
		if ((this.chcNumPuertas.getSelectionModel().getSelectedIndex() == 0)
				|| (this.chcNumPuertas.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "Seleccione el numero de puertas del vehiculo\n";
			valido = false;
		}

		if ((this.chcTransmision.getSelectionModel().getSelectedIndex() == 0)
				|| (this.chcTransmision.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "Seleccione la transmision del vehiculo\n";
			valido = false;
		}
		
		if ((this.txtKilometraje.getText() == null)
				|| (this.txtKilometraje != null && this.txtKilometraje.getText().isEmpty())) {
			this.mensaje += "El kilometraje no es valido, es vacio\n";
			valido = false;
		}

		if (!this.kmValido) {
			this.mensaje += "El kilometraje no es valido, debe contener decimales\n";
			valido = false;
		}

		if ((this.txtPrecio.getText() == null) || (this.txtPrecio != null && this.txtPrecio.getText().isEmpty())) {
			this.mensaje += "El precio no es valido, es vacio\n";
			valido = false;
		}

		if (!this.precioValido) {
			this.mensaje += "El precio no es valido, debe contener decimales\n";
			valido = false;
		}
		if ((this.chcCantidad.getSelectionModel().getSelectedIndex() == 0)
				|| (this.chcCantidad.getSelectionModel().getSelectedIndex() == -1)) {
			this.mensaje += "Seleccione una cantidad\n";
			valido = false;
		}

		if ((this.txtColor.getText() == null)
				|| (this.txtColor.getText() != null && this.txtColor.getText().isEmpty())) {
			this.mensaje += "El color no es valido, es vacio\n";
			valido = false;
		}

		return valido;
	}
}
