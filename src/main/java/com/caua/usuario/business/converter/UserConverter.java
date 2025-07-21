package com.caua.usuario.business.converter;

import com.caua.usuario.business.dto.EnderecoDTO;
import com.caua.usuario.business.dto.TelefoneDTO;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.entity.Endereco;
import com.caua.usuario.infrastructure.entity.Telefone;
import com.caua.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public Usuario toUser(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(toAddressList(usuarioDTO.getEnderecos()))
                .telefones(toCellphoneList(usuarioDTO.getTelefones()))
                .build();
    }
    //criacao de uma lista de enderecos para cada endereco
    public List<Endereco> toAddressList(List<EnderecoDTO> addressDTOS){
        return addressDTOS.stream().map(this::toAddress).toList();
    }

    public Endereco toAddress(EnderecoDTO addressDTO){
        return Endereco.builder()
                .rua(addressDTO.getRua())
                .numero(addressDTO.getNumero())
                .cidade(addressDTO.getCidade())
                .complemento(addressDTO.getComplemento())
                .cep(addressDTO.getCep())
                .estado(addressDTO.getEstado())
                .build();
    }
    //criacao de uma lista de telefones para cada telefone
    public List<Telefone> toCellphoneList(List<TelefoneDTO> cellphoneDTOS){
        return cellphoneDTOS.stream().map(this::toCellphone).toList();
    }

    public Telefone toCellphone(TelefoneDTO cellphoneDTO){
        return Telefone.builder()
                .numero(cellphoneDTO.getNumero())
                .ddd(cellphoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO toUserDTO(Usuario userDTO){
        return UsuarioDTO.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .enderecos(toAddressListDTO(userDTO.getEnderecos()))
                .telefones(toCellphoneListDTO(userDTO.getTelefones()))
                .build();
    }
    //criacao de uma lista de enderecos para cada endereco
    public List<EnderecoDTO> toAddressListDTO(List<Endereco> addressDTOS){
        return addressDTOS.stream().map(this::toAddressDTO).toList();
    }

    public EnderecoDTO toAddressDTO(Endereco addressDTO){
        return EnderecoDTO.builder()
                .rua(addressDTO.getRua())
                .numero(addressDTO.getNumero())
                .cidade(addressDTO.getCidade())
                .complemento(addressDTO.getComplemento())
                .cep(addressDTO.getCep())
                .estado(addressDTO.getEstado())
                .build();
    }
    //criacao de uma lista de telefones para cada telefone
    public List<TelefoneDTO> toCellphoneListDTO(List<Telefone> cellphoneDTOS){
        return cellphoneDTOS.stream().map(this::toCellphoneDTO).toList();
    }

    public TelefoneDTO toCellphoneDTO(Telefone cellphoneDTO){
        return TelefoneDTO.builder()
                .numero(cellphoneDTO.getNumero())
                .ddd(cellphoneDTO.getDdd())
                .build();
    }
}
