package com.caua.usuario.controller;

import com.caua.usuario.business.UserService;
import com.caua.usuario.business.dto.EnderecoDTO;
import com.caua.usuario.business.dto.TelefoneDTO;
import com.caua.usuario.business.dto.UsuarioDTO;
import com.caua.usuario.infrastructure.security.JwtUtil;
import com.caua.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> saveUser(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(userService.saveUser(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return ResponseEntity.ok("Bearer " + jwtUtil.generateToken(authentication.getName()));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(userService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        userService.deletarUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizaDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                           @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(userService.atualizaDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizaEndereco(@RequestBody EnderecoDTO dto,
                                                        @RequestParam ("id") Long id){
        return ResponseEntity.ok(userService.atualizaEndereco(id, dto));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizaTelefone(@RequestBody TelefoneDTO dto,
                                                        @RequestParam ("id") Long id){
        return ResponseEntity.ok(userService.atualizaTelefone(id, dto));
    }
}