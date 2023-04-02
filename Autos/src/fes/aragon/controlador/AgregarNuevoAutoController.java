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

public class AgregarNuevoAutoController extends BaseController implements Initializable {
	private Tienda tienda;
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
	private TextField txtColor;

	@FXML
	private TextField txtKilometraje;

	@FXML
	private TextField txtMarca;

	@FXML
	private TextField txtModelo;

	@FXML
	private TextField txtPrecio;

	@FXML
	private TextField txtTitulo;

	@FXML
	void cancelarAuto(ActionEvent event) {
		this.cerrarVentana(btnCancelar);
	}

	@FXML
	void nuevoAuto(ActionEvent event) {
		if (this.verificar()) {
			Auto auto = new Auto();
			auto.setAnio(this.chcAnio.getValue());
			auto.setCantidad(this.chcCantidad.getValue());
			auto.setEstado(this.chcEstado.getValue());
			auto.setMarca(this.txtMarca.getText());
			auto.setModelo(this.txtModelo.getText());
			auto.setNumeroDePuertas(this.chcNumPuertas.getValue());
			auto.setTransmision(this.chcTransmision.getValue());
			auto.setColor(this.txtColor.getText());
			auto.setPrecio(Double.parseDouble(this.txtPrecio.getText()));
			auto.setKilometraje(Double.parseDouble(this.txtKilometraje.getText()));
			if (this.verificarDuplicado(auto)) {
				tienda.getAutos().add(auto);
				BaseController.modificacionValida = true;
				this.cerrarVentana(btnAceptar);
			} else {
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
		this.chcEstado.getItems().addAll("Seleccione una opcionn:", "Nuevo", "Seminuevo");
		this.chcTransmision.getItems().addAll("Seleccione una opcion:", "Estandar", "Automatico");
		this.chcCantidad.getItems().addAll("Seleccione una opcion:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		this.chcNumPuertas.getItems().addAll("Seleccione una opcion:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

		this.chcAnio.getSelectionModel().select(0);
		this.chcEstado.getSelectionModel().select(0);
		this.chcTransmision.getSelectionModel().select(0);
		this.chcCantidad.getSelectionModel().select(0);
		this.chcNumPuertas.getSelectionModel().select(0);

		this.tienda = Tiendas.getObjeto().getGrupoTiendas().get(Tiendas.getObjeto().getIndice());
		this.txtTitulo.setDisable(true);
		this.txtTitulo.setText(tienda.getNombre());
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
			this.mensaje += "Seleccione el a�o de vehiculo\n";
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
			this.mensaje += "Seleccione la transmisionn del vehiculo\n";
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
		boolean valido = true;
		ObservableList<Auto> auto = tienda.getAutos();
		for (int i = 0; i < auto.size(); i++) {
				if (auto.get(i).getColor().equalsIgnoreCase(this.txtColor.getText())
						&& auto.get(i).getModelo().equalsIgnoreCase(this.txtModelo.getText())
						&& auto.get(i).getEstado().equalsIgnoreCase(this.chcEstado.getValue())
						&& auto.get(i).getMarca().equalsIgnoreCase(this.txtMarca.getText())
						&& auto.get(i).getTransmision().equalsIgnoreCase(this.chcTransmision.getValue())
						&& auto.get(i).getAnio().equalsIgnoreCase(this.chcAnio.getValue())) {
					this.mensaje += "No es posible crear un auto con los mismos datos de marca, modelo, Anio, trasnsmision, estado y color.\n";
					valido = false;
					return valido;
					
				}
			}
		return valido;
	}

}
