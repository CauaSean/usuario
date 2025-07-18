package com.caua.usuario.business.converter;

import com.caua.usuario.business.dto.AddressDTO;
import com.caua.usuario.business.dto.CellphoneDTO;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.entity.Address;
import com.caua.usuario.infrastructure.entity.Cellphone;
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
                .addresses(toAddressList(usuarioDTO.getAddresses()))
                .cellphones(toCellphoneList(usuarioDTO.getCellphones()))
                .build();
    }
    //criacao de uma lista de enderecos para cada endereco
    public List<Address> toAddressList(List<AddressDTO> addressDTOS){
        return addressDTOS.stream().map(this::toAddress).toList();
    }

    public Address toAddress(AddressDTO addressDTO){
        return Address.builder()
                .rua(addressDTO.getRua())
                .numero(addressDTO.getNumero())
                .cidade(addressDTO.getCidade())
                .complemento(addressDTO.getComplemento())
                .cep(addressDTO.getCep())
                .estado(addressDTO.getEstado())
                .build();
    }
    //criacao de uma lista de telefones para cada telefone
    public List<Cellphone> toCellphoneList(List<CellphoneDTO> cellphoneDTOS){
        return cellphoneDTOS.stream().map(this::toCellphone).toList();
    }

    public Cellphone toCellphone(CellphoneDTO cellphoneDTO){
        return Cellphone.builder()
                .numero(cellphoneDTO.getNumero())
                .ddd(cellphoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO toUserDTO(Usuario userDTO){
        return UsuarioDTO.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .addresses(toAddressListDTO(userDTO.getAddresses()))
                .cellphones(toCellphoneListDTO(userDTO.getCellphones()))
                .build();
    }
    //criacao de uma lista de enderecos para cada endereco
    public List<AddressDTO> toAddressListDTO(List<Address> addressDTOS){
        return addressDTOS.stream().map(this::toAddressDTO).toList();
    }

    public AddressDTO toAddressDTO(Address addressDTO){
        return AddressDTO.builder()
                .rua(addressDTO.getRua())
                .numero(addressDTO.getNumero())
                .cidade(addressDTO.getCidade())
                .complemento(addressDTO.getComplemento())
                .cep(addressDTO.getCep())
                .estado(addressDTO.getEstado())
                .build();
    }
    //criacao de uma lista de telefones para cada telefone
    public List<CellphoneDTO> toCellphoneListDTO(List<Cellphone> cellphoneDTOS){
        return cellphoneDTOS.stream().map(this::toCellphoneDTO).toList();
    }

    public CellphoneDTO toCellphoneDTO(Cellphone cellphoneDTO){
        return CellphoneDTO.builder()
                .numero(cellphoneDTO.getNumero())
                .ddd(cellphoneDTO.getDdd())
                .build();
    }
}
