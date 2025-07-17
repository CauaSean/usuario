package com.caua.usuario.business;

import com.caua.usuario.business.converter.UserConverter;
import com.caua.usuario.business.dto.UserDTO;
import com.caua.usuario.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO saveUser(UserDTO userDTO){
        User user = userConverter.toUser(userDTO);
        user = userRepository.save(user);
        return userConverter
    }
}
