package com.tt1.trabajo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.mockito.Mockito;
import servicios.EnviarEmailsServicio;
import modelo.Destinatario;

class EnviarEmailsServicioTest {
    @Test
    void testEnviarEmailRetornaTrue() {
        Logger mockLogger = Mockito.mock(Logger.class);
        EnviarEmailsServicio servicio = new EnviarEmailsServicio(mockLogger);
        Destinatario d = new Destinatario("test@test.com");
        
        assertTrue(servicio.enviarEmail(d, "hola"));
    }
}