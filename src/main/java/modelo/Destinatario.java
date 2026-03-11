package modelo;

public class Destinatario {
    private String email;

    // constructor necesario para crear destinatarios con dirección
    public Destinatario(String email) {
        this.email = email;
    }

    // getter para que el servicio pueda leer la dirección
    public String getEmail() {
        return email;
    }

    // setter por si necesito cambiarla en el futuro
    public void setEmail(String email) {
        this.email = email;
    }
}