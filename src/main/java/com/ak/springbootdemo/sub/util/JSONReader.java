package com.ak.springbootdemo.sub.util;

import com.ak.springbootdemo.sub.exceptions.SubsidiaryControllerException;
import com.ak.springbootdemo.sub.service.SubsidiaryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class JSONReader implements Reader {
    private final String fileName;

    public JSONReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<SubsidiaryDTO> getSubsidiaries() throws SubsidiaryControllerException {
        List<SubsidiaryDTO> subsidiariesFromJSON;
        try (final InputStream resourceAsStream = SubsidiaryService.class.getClassLoader().getResourceAsStream(fileName)) {
            subsidiariesFromJSON = new ObjectMapper().setVisibility(FIELD, ANY)
                    .readValue(resourceAsStream, new TypeReference<>() {
                    });
        } catch (IOException e) {
            throw new SubsidiaryControllerException("Unable get subsidiaries list from json file.");
        }
        return subsidiariesFromJSON;
    }
}
