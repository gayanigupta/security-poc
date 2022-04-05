package com.user.security.poc.repository.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public interface IModelMapper<M, D> {

    D convertModelToDTO(ModelMapper modelMapper, M m, Class<D> destinationType);
    M convertDTOToModel(ModelMapper modelMapper, D d, Class<M> destinationType);
    D convertModelToDTO(ModelMapper modelMapper, M m, Class<D> destinationType, PropertyMap<M, D> propertyMap);
    M convertDTOToModel(ModelMapper modelMapper, D d, Class<M> destinationType, PropertyMap<D, M> propertyMap);
}
