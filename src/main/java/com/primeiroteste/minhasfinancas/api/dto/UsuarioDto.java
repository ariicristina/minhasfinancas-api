package com.primeiroteste.minhasfinancas.api.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private String email;
    private String nome;
    private String senha;
}
