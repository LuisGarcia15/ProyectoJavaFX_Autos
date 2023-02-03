package fes.aragon.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fes.aragon.modelo.TipoError;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ConfirmarAccionController extends BaseController implements Initializable {
	private int errores = 3;
	private String mensaje = "";

	@FXML
	private Button btnAceptar;

	@FXML
	private Button btnCancelar;

	@FXML
	private TextField txtPin;

	@FXML
	void aceptar(ActionEvent event) {
		if (this.verificar()) {
			if (BaseController.nombreVentana.equals("ModificarTienda")) {
				this.cerrarVentana(btnAceptar);
				this.nuevaVentana(BaseController.nombreVentana);
			} else {
				if (BaseController.nombreVentana.equals("Tienda")) {
					this.cerrarVentana(btnAceptar);
					this.nuevaVentana(BaseController.nombreVentana);
				} else {
					if (BaseController.nombreVentana.equals("ModificarAuto")) {
						this.cerrarVentana(btnAceptar);
						this.nuevaVentana(BaseController.nombreVentana);
						BaseController.btn.remove(BaseController.btn.size()-1);
					}
				}
				if (BaseController.nombreVentana.equals("AgregarNuevoAuto")) {
					this.cerrarVentana(btnAceptar);
					this.nuevaVentana(BaseController.nombreVentana);
					BaseController.btn.remove(BaseController.btn.size()-1);
				} else {
					if (BaseController.nombreVentana.equals("EliminarAuto")) {
						this.eliminarFila(BaseController.tabla, BaseController.indice);
						this.cerrarVentana(btnAceptar);
						BaseController.btn.remove(BaseController.btn.size()-1);
						BaseController.modificacionValida = true;
					} else {
						if (BaseController.nombreVentana.equals("EliminarTienda")) {
							this.eliminarFila(BaseController.tabla, BaseController.indice);
							this.cerrarVentana(btnAceptar);
							BaseController.modificacionValida = true;
						}else {
							if (BaseController.nombreVentana.equals("GuardarTienda")) {
								try {
									this.cerrarVentana(btnAceptar);
						    		this.guardarArchivo(BaseController.btn.get(BaseController.btn.size()-1));
						    		BaseController.modificacionValida = false;
								} catch (IOException e) {
									this.ventanaEmergente("Mensaje", "Problema en el archivo", "Hubo un problema en el archivo, "
											+ "consulta al programador");
									e.printStackTrace();
								}
						    }
							}
						}
					}
				}
		}else {
			this.ventanaEmergente("Error", "Pin Erroneo", this.mensaje);
			this.mensaje = "";
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		this.cerrarVentana(btnCancelar);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.verificarEntrada(txtPin, TipoError.PIN);
	}

	private boolean verificar() {
		boolean valido = true;
		if ((this.txtPin.getText() == null) || (this.txtPin.getText() != null && this.txtPin.getText().isEmpty())) {
			this.mensaje += "La pin no es valido, es vacio\n";
			valido = false;
		} else {
			if (!this.pinValido) {
				this.mensaje += "El pin no es valido, debe contener 4 dígitos\n";
				valido = false;
			} else {
				if (!BaseController.usuarioInicioSesion.getPin().equals(this.txtPin.getText())) {
					if(this.errores == 1) {
						this.mensaje += "Error en el pin. \nUsuario: " + BaseController.usuarioInicioSesion.getUsuario() + ".\n";
						this.cerrarVentana(btnAceptar);
						for (Button boton : btn) {
							this.cerrarVentana(boton);
						}
						BaseController.btn.clear();
						this.nuevaVentana("Inicio");
						return false;
					}
					this.errores--;
					this.mensaje += "El pin no es correcto. Quedan " + this.errores + " intentos.\n";
					valido = false;
				}
			}
		}
		return valido;
	}

}
