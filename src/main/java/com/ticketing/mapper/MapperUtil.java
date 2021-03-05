package com.ticketing.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class MapperUtil {

    private ModelMapper modelMapper;

    public MapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

//    public <T> T convertToEntity (Object objectToBeConverted, T convertedToObject){
//        return modelMapper.map(objectToBeConverted, (Type) convertedToObject.getClass());
//    }
//
//    public <T> T convertToDTO(Object objectToBeConverted, T convertedObject){
//        return modelMapper.map(objectToBeConverted,(Type) convertedObject.getClass());
//    }

    public <T> T convert(Object objectToBeConverted, T convertedObject){
        return modelMapper.map(objectToBeConverted,(Type) convertedObject.getClass());
    }

}
