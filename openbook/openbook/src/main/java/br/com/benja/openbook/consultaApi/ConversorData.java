package br.com.benja.openbook.consultaApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConversorData implements IConversor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obterData(String json, Class<T> classe) {
        try {
            return objectMapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar JSON.", e);
        }
    }
}