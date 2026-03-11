package servicios;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import interfaces.InterfazEnviarEmails;
import modelo.Destinatario;

@Service
public class EnviarEmailsServicio implements InterfazEnviarEmails {
    private final Logger logger;

    // spring inyecta el logger automáticamente
    public EnviarEmailsServicio(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean enviarEmail(Destinatario dest, String mensaje) {
        logger.info("enviando email a: {} | mensaje: {}", dest.getEmail(), mensaje);
        return true;
    }
}