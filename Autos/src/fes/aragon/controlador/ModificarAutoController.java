package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Auto;
import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import fes.aragon.modelo.TipoError;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class ModificarAutoController extends BaseController implements Initializable {
	private Tienda tienda = Tiendas.getObjeto().getGrupoTiendas().get(Tiendas.getObjeto().getIndice());
	private Auto auto = tienda.getAutos().get(Tiendas.getObjeto().getIndiceAuto());
	private String mensaje = "";

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCancelar;

	@FXML
	private ChoiceBox<String> chcAnio;

	@FXML
	private ChoiceBox<String> chcCantidad;

	@FXML
	private ChoiceBox<String> chcEstado;

	@FXML
	private ChoiceBox<String> chcNumPuertas;

	@FXML
	private ChoiceBox<String> chcTransmision;

	@FXML
	private TextField txtKilometraje;

	@FXML
	private TextField txtMarca;

	@FXML
	private TextField txtModelo;

	@FXML
	private TextField txtPrecio;

	@FXML
	private TextField txtColor;

	@FXML
	void cancelarAuto(ActionEvent event) {
		this.cerrarVentana(btnCancelar);
	}

	@FXML
	void nuevoAuto(ActionEvent event) {
		if (this.verificar()) {
			this.auto.setAnio(this.chcAnio.getValue());
			this.auto.setCantidad(this.chcCantidad.getValue());
			this.auto.setEstado(this.chcEstado.getValue());
			this.auto.setMarca(this.txtMarca.getText());
			this.auto.setModelo(this.txtModelo.getText());
			this.auto.setNumeroDePuertas(this.chcNumPuertas.getValue());
			this.auto.setTransmision(this.chcTransmision.getValue());
			this.auto.setColor(this.txtColor.getText());
			this.auto.setPrecio(Double.parseDouble(this.txtPrecio.getText()));
			this.auto.setKilometraje(Double.parseDouble(this.txtKilometraje.getText()));
			if (this.verificarDuplicado(auto)) {
				tienda.getAutos().set(Tiendas.getObjeto().getIndiceAuto(), auto);
				BaseController.modificacionValida = true;
				this.cerrarVentana(btnAceptar);
			}else {
				this.ventanaEmergente("Mensaje", "Datos Incorrectos", this.mensaje);
				this.mensaje = "";
			}
		} else {
			this.ventanaEmergente("Mensaje", "Datos Incorrectos", this.mensaje);
			this.mensaje = "";
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.verificarEntrada(txtPrecio, TipoError.PRECIO);
		this.verificarEntrada(txtKilometraje, TipoError.KM);

		this.chcAnio.getItems().addAll("Seleccione una opcion:", "2010", "2011", "2012", "2013", "2014", "2015", "2016",
				"2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025");
		this.chcEstado.getItems().addAll("Seleccione una opcion:", "Nuevo", "Seminuevo");
		this.chcTransmision.getItems().addAll("Seleccione una opcion:", "Estandar", "Automotico");
		this.chcCantidad.getItems().addAll("Seleccione una opcion:","1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		this.chcNumPuertas.getItems().addAll("Seleccione una opcion:","1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		
		this.chcAnio.getSelectionModel().select(0);
		this.chcEstado.getSelectionModel().select(0);
		this.chcTransmision.getSelectionModel().select(0);
		this.chcCantidad.getSelectionModel().select(0);
		this.chcNumPuertas.getSelectionModel().select(0);

		this.chcAnio.setValue(auto.getAnio());
		this.chcCantidad.setValue(auto.getCantidad());
		this.chcEstado.setValue(auto.getEstado());
		this.chcNumPuertas.setValue(auto.getNumeroDePuertas());
		this.chcTransmision.setValue(auto.getTransmision());
		this.txtKilometraje.setText(String.valueOf(auto.getKilometraje()));
		this.txtMarca.setText(auto.getMarca());
		this.txtModelo.setText(auto.getModelo());
		this.txtPrecio.setText(String.valueOf(auto.getPrecio()));
		this.txtColor.setText(auto.getColor());
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

	private boolean verificarDuplicado(Auto autos) {
		boolean valido  = true;
		boolean validoColor = true;
		boolean validoModelo = true;
		boolean validoEstado = true;
		boolean validoMarca = true;
		boolean validoTrasmision = true;
		boolean validoAño = true;
		ObservableList<Auto> auto= tienda.getAutos();
		for(int i = 0; i < auto.size(); i++) {
			if(auto.get(i) == autos) {
				continue;
			}
			
			if(auto.get(i).getColor().equalsIgnoreCase(this.txtColor.getText()) 
			&& auto.get(i).getModelo().equalsIgnoreCase(this.txtModelo.getText())
			&& auto.get(i).getEstado().equalsIgnoreCase(this.chcEstado.getValue()) 
			&& auto.get(i).getMarca().equalsIgnoreCase(this.txtMarca.getText())
			&& auto.get(i).getTransmision().equalsIgnoreCase(this.chcTransmision.getValue())
			&& auto.get(i).getAnio().equalsIgnoreCase(this.chcAnio.getValue())) {
				this.mensaje += "No es posible modificar un auto con los mismos datos de marca, modelo, Año, trasnsmisión, estado y color.\n";
				validoColor = false;
				validoEstado = false;
				validoModelo = false;
				validoMarca = false;
				validoTrasmision = false;
				validoAño = false;
				valido = false;
				}
			
			if(validoColor == false && validoModelo == false && validoEstado == false && validoMarca == false 
			&& validoTrasmision == false && validoAño == false) {
				break;
				}else {
					validoColor = true;
					validoEstado = true;
					validoModelo = true;
					validoMarca = true;
					validoTrasmision = true;
					valido = true;
				}
		}
		return valido;
	}
}
