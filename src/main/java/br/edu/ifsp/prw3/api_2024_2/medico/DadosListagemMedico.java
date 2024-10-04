package br.edu.ifsp.prw3.api_2024_2.medico;

public record DadosListagemMedico(String nome, String email,
                                  String crm, Especialidade especialidade) {

    // Construtor que recebe um objeto Medico, pega só os dados necessários,
    // para criar este objeto DadosListagemMedico.

    // OBS: Você pode criar diversos construtores específicos,
    //      porém eles TEM QUE chamar o construtor padrão (this).

    public DadosListagemMedico(Medico medico) {

        this( medico.getNome(), medico.getEmail(),
                medico.getCrm(), medico.getEspecialidade() );
    }

}




