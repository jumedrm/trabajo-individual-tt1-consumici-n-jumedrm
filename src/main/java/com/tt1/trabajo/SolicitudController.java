package com.tt1.trabajo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import interfaces.InterfazContactoSim;
import modelo.DatosSolicitud;
import com.tt1.trabajo.utilidades.ConsumidorServicio;
import com.tt1.trabajo.utilidades.model.ResultsResponse;

/**
 * controlador principal para gestionar las solicitudes de simulación
 * y la visualización de resultados ampliados desde la máquina virtual.
 */
@Controller
public class SolicitudController {
	
	private final InterfazContactoSim ics;
	private final Logger logger;
	private final ConsumidorServicio consumidorServicio;
	
	/**
	 * constructor con inyección de dependencias.
	 * ahora incluye el servicio consumidor para la api de la vm.
	 */
	public SolicitudController(InterfazContactoSim ics, Logger logger, ConsumidorServicio consumidorServicio) {
		this.ics = ics;
		this.logger = logger;
		this.consumidorServicio = consumidorServicio;
	}

    /**
     * muestra el formulario inicial de solicitud.
     */
    @GetMapping("/solicitud")
    public String solicitud(Model model) {
        model.addAttribute("entities", ics.getEntities());
        return "solicitud";
    }
    
    /**
     * procesa el envío del formulario y solicita la simulación.
     */
    @PostMapping("/solicitud")
    public String handleSolicitud(@RequestParam Map<String, String> formData, Model model) {
    	Map<Integer, Integer> validData = new HashMap<>();
        List<String> errors = new ArrayList<>();

        formData.forEach((key, value) -> {
            try {
                int num = Integer.parseInt(value);
                if (num < 0) {
                    errors.add(key + " no puede ser negativo");
                }
                int id = Integer.parseInt(key);
                if (ics.isValidEntityId()) {
                	validData.put(id, num);
                } else {
                	errors.add(key + " no se corresponde con una entidad");
                }
            } catch (NumberFormatException e) {
                errors.add(key + " debe ser un número entero");
            }
        });

        if(!errors.isEmpty()) {
        	model.addAttribute("errors", errors);
        	logger.warn("atendida petición con errores");
        } else {
        	logger.info("atendida petición");
        	DatosSolicitud ds = new DatosSolicitud(validData);
        	int tok = ics.solicitarSimulation(ds);
        	if(tok != -1) {
        		model.addAttribute("token", tok);
        	} else {
        		logger.error("error en comunicación con servidor de simulación");
        	}
        }
        return "formResult";
    }

    /**
     * nueva ruta para mostrar el grid de resultados.
     * consume la interfaz ampliada que hace uso del servicio de la máquina virtual.
     */
    @GetMapping("/grid")
    public String mostrargrid(@RequestParam("tok") int tok, Model model) {
    	ResultsResponse datos = consumidorServicio.obtenerResultadosAmpliados(tok);
        
        // pasamos el token de la url para que no salga el -1
        model.addAttribute("tok", tok);
        // pasamos el objeto completo de la vm
        model.addAttribute("datos", datos);
        
        return "grid"; 
    }

    
}