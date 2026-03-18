package com.tt1.trabajo.utilidades;

import java.util.Collections;
import java.util.List;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tt1.trabajo.utilidades.api.EmailApi;
import com.tt1.trabajo.utilidades.api.SolicitudApi;
import com.tt1.trabajo.utilidades.api.ResultadosApi;
import com.tt1.trabajo.utilidades.model.ResultsResponse;
import com.tt1.trabajo.utilidades.ApiClient;
import jakarta.annotation.PostConstruct;

@Service
public class ConsumidorServicio {

    private final EmailApi emailApi;
    private final SolicitudApi solicitudApi;
    private final ResultadosApi resultadosApi;

    public ConsumidorServicio() {
        // 1. preparamos el cliente con el parche para los errores de json
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().stream()
            .filter(MappingJackson2HttpMessageConverter.class::isInstance)
            .map(MappingJackson2HttpMessageConverter.class::cast)
            .forEach(converter -> {
                converter.getObjectMapper().registerModule(new JsonNullableModule());
            });

        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath("http://localhost:8080");

        // 2. inicializamos obligatoriamente las TRES apis para que no de error 'final'
        this.emailApi = new EmailApi(apiClient);
        this.solicitudApi = new SolicitudApi(apiClient);
        this.resultadosApi = new ResultadosApi(apiClient);
    }

    public ResultsResponse obtenerResultadosAmpliados(int token) {
        try {
            // usamos el nombre de usuario constante
            return resultadosApi.resultadosPost("jumedrm", token);
        } catch (Exception e) {
            System.err.println("error en consumidor: " + e.getMessage());
            return null;
        }
    }

    @PostConstruct
    public void alArrancar() {
        System.out.println("log: servicio consumidor listo y vinculado.");
    }
}