package com.tt1.trabajo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import servicios.ContactoSimServicio;
import modelo.DatosSolicitud;
import modelo.DatosSimulation;
import java.util.HashMap;

class ContactoSimServicioTest {

    private ContactoSimServicio servicio;

    @BeforeEach
    void setUp() {
        servicio = new ContactoSimServicio();
    }
    @Test
    void testGetEntitiesNoEsNuloYTieneDatos() {
        // comprobamos que la lista existe y tiene las entidades que inventamos
        assertNotNull(servicio.getEntities(), "la lista de entidades no debe ser nula");
        assertFalse(servicio.getEntities().isEmpty(), "la lista de entidades debe tener nombres inventados");
    } 
    @Test
    void testSolicitarSimulationGeneraTokenValido() {
        // act
        int token = servicio.solicitarSimulation(new DatosSolicitud(new HashMap<>()));
        
        // assert
        assertTrue(token >= 0, "el token debe ser un número positivo generado aleatoriamente");
    }
    @Test
    void testDescargarDatosNoEsNuloTrasSolicitud() {
        // arrange
        int token = servicio.solicitarSimulation(new DatosSolicitud(new HashMap<>()));        
        
        // act
        DatosSimulation resultado = servicio.descargarDatos(token);        
        
        // assert
        assertNotNull(resultado, "el resultado no debería ser nulo tras una solicitud válida");
        // verificamos que el mensaje del resultado contiene el token (la lógica que pusimos)
        assertTrue(resultado.getResultado().contains(String.valueOf(token)), 
            "el resultado debería mencionar el token generado");
    }
}