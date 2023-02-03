package fes.aragon.modelo;

public class UsuarioInvitado {
	private  static boolean invitado;

	public static boolean isInvitado() {
		return invitado;
	}

	public static void setInvitado(boolean invitado) {
		UsuarioInvitado.invitado = invitado;
	}
	
}
