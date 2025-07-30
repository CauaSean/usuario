package com.caua.usuario.business;

import com.caua.usuario.business.converter.UserConverter;
import com.caua.usuario.business.dto.EnderecoDTO;
import com.caua.usuario.business.dto.TelefoneDTO;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.entity.Endereco;
import com.caua.usuario.infrastructure.entity.Telefone;
import com.caua.usuario.infrastructure.entity.Usuario;
import com.caua.usuario.infrastructure.exceptions.ConflictException;
import com.caua.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.caua.usuario.infrastructure.repository.AddressRepository;
import com.caua.usuario.infrastructure.repository.CellphoneRepository;
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
    private final AddressRepository addressRepository;
    private final CellphoneRepository cellphoneRepository;

    public UsuarioDTO saveUser(UsuarioDTO usuarioDTO){
        emailExists(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = userConverter.toUser(usuarioDTO);
        usuario = userRepository.save(usuario);
        return userConverter.toUserDTO(usuario);
    }

    public void emailExists(String email) {
        if (verifyEmailExists(email)) {
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verifyEmailExists(String email){
        return userRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try{
            return userConverter.toUserDTO(
                    userRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado" + email)));
    } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado" + email);
        }

    }

    public void deletarUsuarioPorEmail(String email){userRepository.deleteByEmail(email);}

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO){
        // Busca o email do usuario atraves do token para tirar a obrigatoriedade do email
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        // Busca os dados do usuario no banco de dados
        Usuario usuarioEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));

        // Mescla os dados recebidos na requisicao DTO com os dados do banco de dados
        Usuario usuario = userConverter.updateUsuario(usuarioDTO, usuarioEntity);

        // Salva os dados do usuario convertido e depois pega o retorno e converte para UsuarioDTO
        return userConverter.toUserDTO(userRepository.save(usuario));
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
