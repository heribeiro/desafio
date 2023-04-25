package api.desafio.services;

import api.desafio.models.MailModel;
import api.desafio.repositories.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    MailRepository mailRepository;

    public List<MailModel> findAll() {
        return mailRepository.findAll();
    }

    public MailModel save(MailModel mailModel) {
        return  mailRepository.save(mailModel);
    }

}
