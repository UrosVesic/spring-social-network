package rs.ac.bg.fon.springsocialnetwork.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.springsocialnetwork.exception.MyRuntimeException;
import rs.ac.bg.fon.springsocialnetwork.model.VerificationEmail;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(VerificationEmail verificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("springsocialnetwork@email.com");
            messageHelper.setTo(verificationEmail.getRecipient());
            messageHelper.setSubject(verificationEmail.getSubject());
            messageHelper.setText(verificationEmail.getContent());
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MyRuntimeException("Exception occurred when sending mail to " + verificationEmail.getRecipient()+" "+e.getMessage());
        }
    }
}
