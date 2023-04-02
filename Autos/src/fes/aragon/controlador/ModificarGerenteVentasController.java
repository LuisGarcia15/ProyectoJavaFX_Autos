package fes.aragon.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.Tienda;
import fes.aragon.modelo.Tiendas;
import fes.aragon.modelo.TipoError;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ModificarGerenteVentasController extends BaseController implements Initializable {
	private Tienda tienda = Tiendas.getObjeto().getGrupoTiendas().get(Tiendas.getObjeto().getIndice());
	private String mensaje = "";

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField txtApellidoMaterno;

	@FXML
	private TextField txtApellidoPaterno;

	@FXML
	private TextField txtCorreo;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtRfc;

	@FXML
	private TextField txtTelefono;

	@FXML
	private TextField txtEdad;

	@FXML
	void cancelarGerente(ActionEvent event) {
		this.cerrarVentana(btnCancelar);
	}

	@FXML
	void nuevoGerente(ActionEvent event) {
		if (this.verificar() && this.verificarDuplicado()) {
			tienda.getGerenteVentas().setApellidoMaterno(this.txtApellidoMaterno.getText());
			tienda.getGerenteVentas().setApellidoPaterno(this.txtApellidoPaterno.getText());
			tienda.getGerenteVentas().setCorreo(this.txtCorreo.getText());
			tienda.getGerenteVentas().setEdad(Integer.parseInt(this.txtEdad.getText()));
			tienda.getGerenteVentas().setNombre(this.txtNombre.getText());
			tienda.getGerenteVentas().setRfc(this.txtRfc.getText());
			tienda.getGerenteVentas().setTelefono(this.txtTelefono.getText());
			BaseController.modificacionValida = true;
			this.cerrarVentana(btnAceptar);
		} else {
			ventanaEmergente("Error", "Seleccione una fila", this.mensaje);
			this.mensaje = "";
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.verificarEntrada(txtRfc, TipoError.RFC);
		this.verificarEntrada(txtCorreo, TipoError.CORREO);
		this.verificarEntrada(txtTelefono, TipoError.TELEFONO);
		this.verificarEntrada(txtEdad, TipoError.EDAD);
		this.txtApellidoMaterno.setText(tienda.getGerenteVentas().getApellidoMaterno());
		this.txtApellidoPaterno.setText(tienda.getGerenteVentas().getApellidoPaterno());
		this.txtCorreo.setText(tienda.getGerenteVentas().getCorreo());
		this.txtNombre.setText(tienda.getGerenteVentas().getNombre());
		this.txtRfc.setText(tienda.getGerenteVentas().getRfc());
		this.txtTelefono.setText(tienda.getGerenteVentas().getTelefono());
		this.txtEdad.setText(String.valueOf(tienda.getGerenteVentas().getEdad()));

	}

	private boolean verificar() {
		boolean valido = true;
		if ((this.txtNombre.getText() == null)
				|| (this.txtNombre.getText() != null && this.txtNombre.getText().isEmpty())) {
			this.mensaje += "El nombre no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtApellidoPaterno.getText() == null)
				|| (this.txtApellidoPaterno.getText() != null && this.txtApellidoPaterno.getText().isEmpty())) {
			this.mensaje += "El apellido paterno no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtApellidoMaterno.getText() == null)
				|| (this.txtApellidoMaterno.getText() != null && this.txtApellidoMaterno.getText().isEmpty())) {
			this.mensaje += "El apellido materno no es valido, es vacio\n";
			valido = false;
		}

		if ((this.txtEdad.getText() == null) || (this.txtEdad.getText() != null && this.txtEdad.getText().isEmpty())) {
			this.mensaje += "El telefono no es valido, es vacio\n";
			valido = false;
		}

		if (!this.edadValido) {
			this.mensaje += "La edad es incorrecta, esta mal estructurada, debe de ser un numero de dos digitos.\n";
			valido = false;
		}

		try {
			if (Integer.parseInt(this.txtEdad.getText()) < 18 || Integer.parseInt(this.txtEdad.getText()) > 60) {
				this.mensaje += "La edad no es valida, no esta dentro del rango debe ser mayor a 18 y menor a 60 años.\n";
				valido = false;
			}
		} catch (NumberFormatException e) {
		}

		if ((this.txtRfc.getText() == null) || (this.txtRfc.getText() != null && this.txtRfc.getText().isEmpty())) {
			this.mensaje += "El RFC no es valido, es vacio\n";
			valido = false;
		}

		if (!this.rfcValido) {
			this.mensaje += "El RFC de Gerente no es valido, minimo 13, maximo 13 caracteres\n";
			valido = false;
		}

		if ((this.txtCorreo.getText() == null)
				|| (this.txtCorreo.getText() != null && this.txtCorreo.getText().isEmpty())) {
			this.mensaje += "El correo del Gerente no es valido, es vacio\n";
			valido = false;
		}

		if (!this.correoValido) {
			this.mensaje += "El correo de Gerente no es valido, esta mas estrucuturado\n";
			valido = false;
		}

		if ((this.txtTelefono.getText() == null)
				|| (this.txtTelefono.getText() != null && this.txtTelefono.getText().isEmpty())) {
			this.mensaje += "El telefono no es valido, es vacio\n";
			valido = false;
		}

		if (!this.telefonoValido) {
			this.mensaje += "El telefono del hotel no es válido, minimo 10, maximo 10 numeros\n";
			valido = false;
		}

		return valido;
	}

	private boolean verificarDuplicado() {
		boolean valido = true;
		boolean validoTelefono = true;
		boolean validoRFC = true;
		boolean validoCorreo = true;
		ObservableList<Tienda> tiendas = Tiendas.getObjeto().getGrupoTiendas();
		for (int i = 0; i < tiendas.size(); i++) {
			if (this.tienda == tiendas.get(i)) {
				continue;
			}

			if (tiendas.get(i).getGerenteVentas().getTelefono().equalsIgnoreCase(this.txtTelefono.getText())
					&& validoTelefono) {
				mensaje += "No es posible crear un gerente con el mismo teléfono.\n";
				valido = false;
				validoTelefono = false;
			}

			if (tiendas.get(i).getGerenteVentas().getRfc().equalsIgnoreCase(this.txtRfc.getText()) && validoRFC) {
				mensaje += "No es posible crear un gerente con el mismo RFC.\n";
				valido = false;
				validoRFC = false;
			}

			if (tiendas.get(i).getGerenteVentas().getCorreo().equalsIgnoreCase(this.txtCorreo.getText())
					&& validoCorreo) {
				mensaje += "No es posible crear un gerente con el mismo correo.\n";
				valido = false;
				validoCorreo = false;
			}
			if (validoCorreo == false && validoRFC == false && validoTelefono == false) {
				return valido;
			}
		}
		return valido;
	}
}
