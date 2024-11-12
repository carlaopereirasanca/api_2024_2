package br.edu.ifsp.prw3.api_2024_2.usuario;

import jakarta.validation.constraints.NotBlank;

public record dadosAutenticacao(

        @NotBlank
        String login,

        @NotBlank
        String senha) {

}


