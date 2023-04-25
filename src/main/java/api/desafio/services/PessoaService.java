package api.desafio.services;

import api.desafio.enums.NivelEnum;
import api.desafio.models.PessoaModel;
import api.desafio.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public PessoaModel save(PessoaModel pessoaModel) {
        return pessoaRepository.save(pessoaModel);
    }

    public Optional<PessoaModel> findById(UUID id) {
        return pessoaRepository.findById(id);
    }

    public List<PessoaModel> findAll() {
        return pessoaRepository.findAll();
    }

    public void delete(PessoaModel pessoaModel) {
        pessoaRepository.delete(pessoaModel);
    }

    public List<PessoaModel> findAllByNivel(NivelEnum nivel) {return pessoaRepository.findAllByNivel(nivel);
    }

}
