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

    public UsuarioDTO toUserDTO(Usuario user){
        return UsuarioDTO.builder()
                .nome(user.getNome())
                .email(user.getEmail())
                .senha(user.getSenha())
                .enderecos(toAddressListDTO(user.getEnderecos()))
                .telefones(toCellphoneListDTO(user.getTelefones()))
                .build();
    }
    //criacao de uma lista de enderecos para cada endereco
    public List<EnderecoDTO> toAddressListDTO(List<Endereco> addressDTOS){
        return addressDTOS.stream().map(this::toAddressDTO).toList();
    }

    public EnderecoDTO toAddressDTO(Endereco address){
        return EnderecoDTO.builder()
                .id(address.getId())
                .rua(address.getRua())
                .numero(address.getNumero())
                .cidade(address.getCidade())
                .complemento(address.getComplemento())
                .cep(address.getCep())
                .estado(address.getEstado())
                .build();
    }
    //criacao de uma lista de telefones para cada telefone
    public List<TelefoneDTO> toCellphoneListDTO(List<Telefone> cellphoneDTOS){
        return cellphoneDTOS.stream().map(this::toCellphoneDTO).toList();
    }

    public TelefoneDTO toCellphoneDTO(Telefone cellphone){
        return TelefoneDTO.builder()
                .id(cellphone.getId())
                .numero(cellphone.getNumero())
                .ddd(cellphone.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuario){
        return Usuario.builder()
                .id(usuario.getId())
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuario.getNome())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuario.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuario.getSenha())
                .enderecos(usuario.getEnderecos())
                .telefones(usuario.getTelefones())
                .build();
    }

    public Endereco updateEndereco(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }
}
