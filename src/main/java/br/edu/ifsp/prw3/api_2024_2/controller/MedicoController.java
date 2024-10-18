package br.edu.ifsp.prw3.api_2024_2.controller;

import br.edu.ifsp.prw3.api_2024_2.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico  dados,
                                    UriComponentsBuilder uriBuilder) {

        // ANTES: repository.save( new Medico(dados) );

        // AGORA:

        // Criando o objeto Medico, com os dados recebidos na requisição:
        var medico = new Medico(dados);

        // Salvando no banco:
        repository.save( medico );

        // Gerar automaticamente a URL para o novo recurso criado:
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        // Vamos aqui usar o DTO que criamos para o método atualizar:
        return ResponseEntity.created(uri).body( new DadosDetalhamentoMedico(medico) );
    }

    @GetMapping
    public List<Medico> listar() {

        return repository.findAll();
    }

    @GetMapping
    @RequestMapping("algunsdados")
    public Page<DadosListagemMedico> listarAlgunsDados(
            @PageableDefault( size=10, sort={"nome","crm"} ) Pageable paginacao ) {

        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
    }


    @GetMapping("/{id}")
    public ResponseEntity getMedicoById(@PathVariable Long id) {

        Optional<Medico> medicoOptional = repository.findById(id);

        if (medicoOptional.isPresent()) {
            Medico medico = medicoOptional.get();
            return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {

        Medico medico = repository.getReferenceById( dados.id() );
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok( new DadosDetalhamentoMedico(medico) );

    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {

        // Já vimos esta linha na funcionalidade de alterar:
        Medico medico = repository.getReferenceById(id);

        // Chama método na classe Medico que coloca
        // o atributo 'ativo' como false:
        medico.excluir();

        // Lembrando, não precisamos regravar o objeto no BD.
        // A JPA automaticamente atualiza o objeto no BD.

        return ResponseEntity.noContent().build();
    }

}

