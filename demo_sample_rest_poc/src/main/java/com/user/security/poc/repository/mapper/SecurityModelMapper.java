package com.user.security.poc.repository.mapper;

import com.user.security.poc.exception.SecurityServiceErrorCode;
import com.user.security.poc.exception.SecurityServiceException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SecurityModelMapper<M, D> implements IModelMapper<M, D> {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityModelMapper.class);
    @Override
    public D convertModelToDTO(ModelMapper modelMapper, M m, Class<D> destinationType) {
        return modelMapper.map(m, destinationType);
    }

    @Override
    public M convertDTOToModel(ModelMapper modelMapper, D d, Class<M> destinationType) {
        M model = modelMapper.map(d, destinationType);
        //Validate model before returning
        return model;
    }

    @Override
    public D convertModelToDTO(ModelMapper modelMapper, M m, Class<D> destinationType, PropertyMap<M, D> propertyMap) {
        try {
            if (propertyMap != null) {
                modelMapper.addMappings(propertyMap);
            }
            return modelMapper.map(m, destinationType);
        } catch (Exception ex) {
            LOG.error("Throwing Exception from convertModelToDTO method", ex);
            throw new SecurityServiceException(SecurityServiceErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
        }

    }

    @Override
    public M convertDTOToModel(ModelMapper modelMapper, D d, Class<M> destinationType, PropertyMap<D, M> propertyMap) {
        try {
            if (propertyMap != null) {
                modelMapper.addMappings(propertyMap);
            }
            M model = modelMapper.map(d, destinationType);
            //Validate model before returning
            return model;
        } catch (Exception ex) {
            LOG.error("Throwing Exception from convertDTOToModel method", ex);
            throw new SecurityServiceException(SecurityServiceErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
        }
    }
}

