package com.caua.usuario.business.converter;

import com.caua.usuario.business.dto.AddressDTO;
import com.caua.usuario.business.dto.CellphoneDTO;
import com.caua.usuario.business.dto.UserDTO;
import com.caua.usuario.infrastructure.entity.Address;
import com.caua.usuario.infrastructure.entity.Cellphone;
import com.caua.usuario.infrastructure.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    public User toUser(UserDTO userDTO){
        return User.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .addresses(toAddressList(userDTO.getEnderecos()))
                .cellphones(toCellphoneList(userDTO.getTelefones()))
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

    public UserDTO toUser(User userDTO){
        return UserDTO.builder()
                .nome(userDTO.getNome())
                .email(userDTO.getEmail())
                .senha(userDTO.getSenha())
                .addresses(toAddressList(userDTO.getEnderecos()))
                .cellphones(toCellphoneList(userDTO.getTelefones()))
                .build();
    }
    //criacao de uma lista de enderecos para cada endereco
    public List<AddressDTO> toAddressList(List<Address> addressDTOS){
        return addressDTOS.stream().map(this::toAddress).toList();
    }

    public AddressDTO toAddress(Address addressDTO){
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
    public List<CellphoneDTO> toCellphoneList(List<Cellphone> cellphoneDTOS){
        return cellphoneDTOS.stream().map(this::toCellphone).toList();
    }

    public CellphoneDTO toCellphone(Cellphone cellphoneDTO){
        return CellphoneDTO.builder()
                .numero(cellphoneDTO.getNumero())
                .ddd(cellphoneDTO.getDdd())
                .build();
    }
}
