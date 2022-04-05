package com.user.security.poc.service.user;

import com.user.security.poc.dto.UserTo;
import com.user.security.poc.exception.SecurityServiceErrorCode;
import com.user.security.poc.exception.SecurityServiceException;
import com.user.security.poc.repository.IUserRepository;
import com.user.security.poc.repository.mapper.SecurityModelMapper;
import com.user.security.poc.repository.model.IRolesRepository;
import com.user.security.poc.repository.model.Role;
import com.user.security.poc.repository.model.UserModel;
import com.user.security.poc.service.utils.validators.ValidateUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private ModelMapper userModelMapper = new ModelMapper();
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRolesRepository rolesRepository;


    @Autowired SecurityModelMapper<UserModel, UserTo> modelMapper;

    public UserTo createUser(UserTo userTo) {

        checkIfUserNameIsUnique(userTo.getUsername());
        UserModel userModel = modelMapper.convertDTOToModel(userModelMapper, userTo, UserModel.class);

        Set<Role> existingRoles = new HashSet<>();
        for(Role role : userModel.getRoles()){
            existingRoles.add(rolesRepository.findByName(role.getName()));
        }
        userModel.setRoles(existingRoles);
        ValidateUser.validateModel(userModel);
        userModel = userRepository.save(userModel);
        return modelMapper.convertModelToDTO(userModelMapper, userModel, UserTo.class);
    }

    public UserTo getUserById(Long userId) {
        checkIfUserExists(userId);
        UserModel userModel = userRepository.getOne(userId);
        return modelMapper.convertModelToDTO(userModelMapper, userModel, UserTo.class);
    }

    public List<UserTo> getAllUsers(Pageable pageable) {
        Page<UserModel> userModels = userRepository.findAll(pageable);
        return userModels.stream()
                .map(userModel -> modelMapper.convertModelToDTO(userModelMapper, userModel, UserTo.class)).collect(
                        Collectors.toList());
    }

    private  void checkIfUserExists(Long userId){
        if(!userRepository.findById(userId).isPresent()){
            String[] errorKeys = {String.valueOf(userId)};
            throw new SecurityServiceException(SecurityServiceErrorCode.NO_USER_FOUND_WITH_ID, HttpStatus.BAD_REQUEST.value(), errorKeys, errorKeys);
        }
    }
    private  void checkIfUserNameIsUnique(String userName) {
        if (userRepository.findByUsername(userName) != null) {
            String[] errorKeys = {String.valueOf(userName)};
            throw new SecurityServiceException(SecurityServiceErrorCode.DUPLICATE_USER_NAME,
                    HttpStatus.BAD_REQUEST.value(), errorKeys, errorKeys);
        }
    }

    public UserTo updateUser(Long useId, UserTo userTo) {
        UserModel userModel = userRepository.getOne(useId);
        userModel.setUsername(userTo.getUsername());
        userModel.setPassword(userTo.getPassword());
        userModel =  userRepository.save(userModel);
        return modelMapper.convertModelToDTO(userModelMapper, userModel, UserTo.class);
    }
}
