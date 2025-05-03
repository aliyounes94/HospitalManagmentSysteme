package com.hms.dao;
import java.io.IOException;
import java.util.List;
import java.io.File;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.hms.model.*;
public class PatientDAO {
    public static List<Patient> readPatients(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), new TypeReference<List<Patient>>(){});
    }

    public static void writePatients(String path, List<Patient> patients) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(path), patients);
    }
}