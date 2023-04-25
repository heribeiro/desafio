package api.desafio.dtos;


import api.desafio.enums.EspecieEnum;
import api.desafio.enums.NivelEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PessoaDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String idade;
    @NotBlank
    private EspecieEnum especie;

    @NotBlank
    private String poder;

    @NotBlank
    private NivelEnum nivel;

    @NotBlank
    @Email
    private String email;
}
