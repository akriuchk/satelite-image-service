package com.akriuchk.imageservice.conf;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DtoMapper {

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(com.akriuchk.imageservice.model.Feature.class, com.akriuchk.imageservice.client.model.Feature.class)
                .addMappings(
                        mapper -> mapper.map(src -> src.getUuid(), com.akriuchk.imageservice.client.model.Feature::setId)
                );
        return modelMapper;
    }
}
