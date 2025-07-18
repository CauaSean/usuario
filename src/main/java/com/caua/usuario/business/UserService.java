package com.caua.usuario.business;

import com.caua.usuario.business.converter.UserConverter;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.entity.Usuario;
import com.caua.usuario.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UsuarioDTO saveUser(UsuarioDTO usuarioDTO){
        Usuario usuario = userConverter.toUser(usuarioDTO);
        usuario = userRepository.save(usuario);
        return userConverter.toUserDTO(usuario);
    }
}
