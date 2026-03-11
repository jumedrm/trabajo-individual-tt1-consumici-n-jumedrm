package servicios;

import java.util.*;
import org.springframework.stereotype.Service;
import interfaces.InterfazContactoSim;
import modelo.DatosSimulation;
import modelo.DatosSolicitud;
import modelo.Entidad;

@Service
public class ContactoSimServicio implements InterfazContactoSim {
    
	private final Map<Integer, DatosSolicitud> solicitudes = new HashMap<>();
    // nuevo mapa para guardar los "resultados" de esas solicitudes
    private final Map<Integer, DatosSimulation> resultados = new HashMap<>();
    private final Random random = new Random();

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        int token = random.nextInt(1000000);// genera token aleatorio
        solicitudes.put(token, sol);
        resultados.put(token, new DatosSimulation("simulación completada para el token " + token, random.nextDouble() * 100));        
        return token;
    }

    @Override
    public List<Entidad> getEntities() {
        // invento una lista de nombres para las entidades
        List<Entidad> entidades = new ArrayList<>();
        entidades.add(new Entidad(1, "servidor alfa"));
        entidades.add(new Entidad(2, "nodo central"));
        entidades.add(new Entidad(3, "clúster de cómputo"));
        return entidades;
    }

    @Override
    public boolean isValidEntityId() {
        return true; // por ahora acepto todas como válidas
    }

    @Override
    public DatosSimulation descargarDatos(int ticket) {
        // ahora el método busca en el mapa de resultados en lugar de devolver null
        return resultados.get(ticket);
    }
}