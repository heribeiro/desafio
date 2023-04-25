package api.desafio.repositories;

import api.desafio.enums.NivelEnum;
import api.desafio.models.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, UUID> {
   @Query("SELECT p FROM PessoaModel p WHERE p.nivel = ?1")
    List<PessoaModel> findAllByNivel(NivelEnum nivel);
}
