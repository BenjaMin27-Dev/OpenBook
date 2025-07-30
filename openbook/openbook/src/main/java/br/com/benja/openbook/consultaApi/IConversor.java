package br.com.benja.openbook.consultaApi;


    public interface IConversor { <T> T obterData(String json, Class<T> anyClass);

    }
