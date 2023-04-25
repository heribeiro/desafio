package api.desafio.models;

import api.desafio.enums.EspecieEnum;
import api.desafio.enums.NivelEnum;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@Table(name="TB_MUTANTES")
public class PessoaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false,  unique = true, length = 130)
    private String nome;
    @Column(nullable = false,  unique = true, length = 130)
    private String idade;
    @Column(nullable = false)
    private EspecieEnum especie;

    @Column(nullable = false,  unique = true, length = 130)
    private String poder;

    @Column(nullable = false)
    private NivelEnum nivel;



    @Column(nullable = false)
    private LocalDateTime DataEntrada;

    @Column(nullable = false)
    private LocalDateTime DataSaida;


    @Column(nullable = false, unique = true, length = 130)
    private String email;


}
