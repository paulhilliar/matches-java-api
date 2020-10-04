package com.matchesfashion.papi

import com.fasterxml.jackson.databind.ObjectMapper

class TestUtils {

    static final ObjectMapper objectMapper = new ObjectMapper()

    static <T> T unmarshal(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    static String marshal(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

}
