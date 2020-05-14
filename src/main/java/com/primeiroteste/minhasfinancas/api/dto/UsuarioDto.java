package com.primeiroteste.minhasfinancas.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UsuarioDto {

    private String email;
    private String nome;
    private String senha;
}
