package api.desafio.controllers;

import api.desafio.dtos.PessoaDto;
import api.desafio.enums.NivelEnum;
import api.desafio.enums.StatusEmail;
import api.desafio.models.MailModel;
import api.desafio.models.PessoaModel;
import api.desafio.services.MailService;
import api.desafio.services.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pessoa")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PessoaController {


    @Autowired
    private PessoaService pessoaService;


    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping
    public ResponseEntity<Object> savePessoa(@RequestBody @Valid PessoaDto pessoaDto) {
        PessoaModel pessoaModel = new PessoaModel();

        BeanUtils.copyProperties(pessoaDto, pessoaModel);


        var mailModel = new MailModel();

        String subject;
        String text;

        switch (pessoaModel.getEspecie()) {
            case HOMOSAPIENS:
            case HOMOPEREGRINO:
                subject = "Atenção";
                text = pessoaModel.getNome() + " entrou na mansão de mutantes!";
                mailModel.setEmailFrom("helena.correa84@gmail.com");
                mailModel.setEmailTo("charlesxavier@gamil.com");
                break;

            case HOMOSUPERIOR:
                subject = "Boas Vindas!";
                text = "Bem vindo," + pessoaModel.getNome();
                mailModel.setEmailFrom("charlesxavier@gamil.com");
                mailModel.setEmailTo(pessoaModel.getEmail());
                break;

            default:
                subject = "Atenção!";
                text = "Pessoa desconhecida";
                mailModel.setEmailFrom("helena.correa84@gmail.com");
                mailModel.setEmailTo("charlesxavier@gamil.com");
                break;
        }
        mailModel.setSubject(subject);
        mailModel.setText(text);
        mailModel.setSendDateEmail(LocalDateTime.now());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailModel.getEmailFrom());
        message.setTo(mailModel.getEmailTo());
        message.setSubject(mailModel.getSubject());
        message.setText(mailModel.getText());
        mailSender.send(message);

        try {
            mailSender.send(message);
            mailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            mailModel.setStatusEmail(StatusEmail.ERROR);
        }

        mailService.save(mailModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoaModel));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updatePessoaById(@PathVariable(value = "id") UUID id, @RequestBody PessoaDto pessoaDto) {
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);

        if (pessoaModelOptional.isPresent()) {
            PessoaModel pessoaModel = pessoaModelOptional.get();

            BeanUtils.copyProperties(pessoaDto, pessoaModel);

            pessoaModel.setId(pessoaModelOptional.get().getId());

            return ResponseEntity.status(HttpStatus.OK).body(pessoaService.save(pessoaModel));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontramos nenhuma pessoa com esse ID");
        }
    }
    @GetMapping
    public ResponseEntity<List<PessoaModel>> getPessoas(){

        var emailModel = new MailModel();

        emailModel.setEmailFrom("defcon@xmen.org");
        emailModel.setEmailTo("charlesXavier@gmail.com");
        emailModel.setSendDateEmail(LocalDateTime.now());
        emailModel.setSubject("Relatório de acesso");
        emailModel.setText("Bom dia Dr. Charles Xavier. O relatório de defesa segue em anexo: " + pessoaService.findAll());

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(emailModel.getEmailFrom());
        message.setTo(emailModel.getEmailTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getText());

        try {
            mailSender.send(message);
            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }

        mailService.save(emailModel);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAll());
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable(value = "id") UUID id){
        Optional<PessoaModel> pessoaModelOptional =  pessoaService.findById(id);

        if(!pessoaModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID nao foi encontrado!!!!!");
        }else{
            return  ResponseEntity.status(HttpStatus.OK).body(pessoaModelOptional.get());
        }
    }

    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<PessoaModel>> getPessoaByNivel(@PathVariable(value = "nivel") NivelEnum nivel){
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAllByNivel(nivel));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteByPessoaId(@PathVariable(value = "id") UUID id){
        Optional<PessoaModel> pessoaModelOptional =  pessoaService.findById(id);

        if(!pessoaModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O ID nao foi encontrado!!!!!");
        }else{
            pessoaService.delete(pessoaModelOptional.get());
            return  ResponseEntity.status(HttpStatus.OK).body("Cadastro da pessoa deletado com sucesso");
        }
    }
}
