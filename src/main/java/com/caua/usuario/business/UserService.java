package com.caua.usuario.business;

import com.caua.usuario.business.converter.UserConverter;
import com.caua.usuario.business.dto.EnderecoDTO;
import com.caua.usuario.business.dto.TelefoneDTO;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.entity.Endereco;
import com.caua.usuario.infrastructure.entity.Telefone;
import com.caua.usuario.infrastructure.entity.Usuario;
import com.caua.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.caua.usuario.infrastructure.exceptions.UnauthorizedException;
import com.caua.usuario.infrastructure.repository.AddressRepository;
import com.caua.usuario.infrastructure.repository.CellphoneRepository;
import com.caua.usuario.infrastructure.repository.UserRepository;
import com.caua.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AddressRepository addressRepository;
    private final CellphoneRepository cellphoneRepository;
    private final JwtUtil jwtUtil;

    public UsuarioDTO saveUser(UsuarioDTO usuarioDTO){

        Usuario usuario = userConverter.toUser(usuarioDTO);
        usuario = userRepository.save(usuario);
        return userConverter.toUserDTO(usuario);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        return userConverter.toUserDTO(
                userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado" + email)));
    }

    public void deletarUsuarioPorEmail(String email){userRepository.deleteByEmail(email);}

    public String login(UsuarioDTO usuarioDTO) {
        Usuario usuario = userRepository.findByEmail(usuarioDTO.getEmail()).orElseThrow(() ->
                new UnauthorizedException("Usuário ou senha inválidos"));
        if (!usuario.getSenha().equals(usuarioDTO.getSenha())) {
            throw new UnauthorizedException("Usuário ou senha inválidos");
        }
        return jwtUtil.generateToken(usuario.getEmail());
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){

        Endereco entity = addressRepository.findById(idEndereco).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + idEndereco));

        Endereco endereco = userConverter.updateEndereco(enderecoDTO, entity);

        return userConverter.toAddressDTO(addressRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){

        Telefone entity = cellphoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = userConverter.updateTelefone(dto, entity);

        return userConverter.toCellphoneDTO(cellphoneRepository.save(telefone));
    }
}
