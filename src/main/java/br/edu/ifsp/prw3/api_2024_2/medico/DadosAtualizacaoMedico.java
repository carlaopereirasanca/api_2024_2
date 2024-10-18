package br.edu.ifsp.prw3.api_2024_2.medico;

import br.edu.ifsp.prw3.api_2024_2.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereco) { }

