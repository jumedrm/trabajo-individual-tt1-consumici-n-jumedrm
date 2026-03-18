package servicios;

import java.util.*;
import org.springframework.stereotype.Service;
import interfaces.InterfazContactoSim;
import modelo.DatosSolicitud;
import modelo.Entidad;
import modelo.DatosSimulation;

// imports de openapi
import com.tt1.trabajo.utilidades.api.SolicitudApi;
import com.tt1.trabajo.utilidades.model.Solicitud;
import com.tt1.trabajo.utilidades.model.SolicitudResponse;
import com.tt1.trabajo.utilidades.ApiClient;

@Service
public class ContactoSimServicio implements InterfazContactoSim {
    
    private final SolicitudApi solicitudApi;

    public ContactoSimServicio() {
        // 1. Creamos un RestTemplate básico
        org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
        
        // 2. Le instalamos el parche para JsonNullable
        restTemplate.getMessageConverters().stream()
            .filter(org.springframework.http.converter.json.MappingJackson2HttpMessageConverter.class::isInstance)
            .map(org.springframework.http.converter.json.MappingJackson2HttpMessageConverter.class::cast)
            .forEach(converter -> {
                converter.getObjectMapper().registerModule(new org.openapitools.jackson.nullable.JsonNullableModule());
            });

        // 3. Creamos el ApiClient pasándole el restTemplate ya configurado
        // Tu ApiClient tiene un constructor que acepta un RestTemplate (línea 86 de tu archivo)
        com.tt1.trabajo.utilidades.ApiClient apiClient = new com.tt1.trabajo.utilidades.ApiClient(restTemplate);
        apiClient.setBasePath("http://localhost:8080");

        this.solicitudApi = new SolicitudApi(apiClient);
    }

    @Override
    public int solicitarSimulation(DatosSolicitud sol) {
        try {
            List<Integer> cantidades = new ArrayList<>(sol.getNums().values());
            List<String> nombres = new ArrayList<>();
            for (int i = 0; i < cantidades.size(); i++) nombres.add("e_" + i);

            Map<String, Object> body = new HashMap<>();
            body.put("cantidadesIniciales", cantidades);
            body.put("nombreEntidades", nombres);

            ApiClient client = solicitudApi.getApiClient();
            
            // forzamos que el usuario vaya en los queryParams (en la url)
            org.springframework.util.MultiValueMap<String, String> queryParams = new org.springframework.util.LinkedMultiValueMap<>();
            queryParams.add("nombreUsuario", "jumedrm"); 

            var response = client.invokeAPI(
                "/Solicitud/Solicitar", 
                org.springframework.http.HttpMethod.POST, 
                Collections.emptyMap(), 
                queryParams, // aquí va el usuario
                body, 
                new org.springframework.http.HttpHeaders(), 
                new org.springframework.util.LinkedMultiValueMap<>(), 
                new org.springframework.util.LinkedMultiValueMap<>(), 
                List.of(org.springframework.http.MediaType.APPLICATION_JSON), 
                org.springframework.http.MediaType.APPLICATION_JSON, 
                new String[]{}, 
                new org.springframework.core.ParameterizedTypeReference<com.tt1.trabajo.utilidades.model.SolicitudResponse>() {}
            );

            return response.getBody().getTokenSolicitud();
        } catch (Exception e) {
            System.err.println("error solicitud: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public List<Entidad> getEntities() {
        List<Entidad> entidades = new ArrayList<>();
        entidades.add(new Entidad(1, " Perros"));
        entidades.add(new Entidad(2, " Gatos"));
        entidades.add(new Entidad(3, " Ratones"));
        return entidades;
    }

    @Override
    public boolean isValidEntityId() { return true; }

    @Override
    public DatosSimulation descargarDatos(int ticket) { return null; }
}