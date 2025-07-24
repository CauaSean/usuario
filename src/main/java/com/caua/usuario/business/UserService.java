package com.caua.usuario.business;

import com.caua.usuario.business.converter.UserConverter;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.entity.Usuario;
import com.caua.usuario.infrastructure.exceptions.ConflictException;
import com.caua.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.caua.usuario.infrastructure.repository.UserRepository;
import com.caua.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO saveUser(UsuarioDTO usuarioDTO){
        emailExists(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = userConverter.toUser(usuarioDTO);
        usuario = userRepository.save(usuario);
        return userConverter.toUserDTO(usuario);
    }

    public void emailExists(String email){
        try{
            boolean exist = verifyEmailExists(email);
            if(exist){
                throw new ConflictException("Email já cadastrado" + email);
            }
        }catch(ConflictException e){
            throw new ConflictException("Email já cadastrado" + e.getCause());
        }
    }
    public boolean verifyEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email nao encontrado" + email));
    }

    public void deletarUsuarioPorEmail(String email){userRepository.deleteByEmail(email);}

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
        // Busca o email do usuario atraves do token para tirar a obrigatoriedade do email
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        // Busca os dados do usuario no banco de dados
        Usuario usuarioEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email nao localizado"));

        // Mescla os dados recebidos na requisicao DTO com os dados do banco de dados
        Usuario usuario = userConverter.updateUsuario(usuarioDTO, usuarioEntity);

        // Salva os dados do usuario convertido e depois pega o retorno e converte para UsuarioDTO
        return userConverter.toUserDTO(userRepository.save(usuario));
    }
}
