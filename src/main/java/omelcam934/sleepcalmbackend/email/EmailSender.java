package omelcam934.sleepcalmbackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import omelcam934.sleepcalmbackend.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;

@Service
public class EmailSender {

    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    public EmailSender(@Autowired JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailConNuevaContrasenya(User usuario, String nuevaContrasenya) throws UnsupportedEncodingException, MessagingException {

        String senderName = "SleepCalm";
        String subject = "Cambio de contraseña";
        String content = "Estimado [[name]],<br>"
                + "Se ha recibido una petición para cambio de contraseña.<br/>" +
                  "Su nueva contraseña es:<br/>"
                + "<h3>[[pass]]</h3><br/>" +
                  "Por favor cambiela cuanto antes.<br/>"
                + "Sleepcalm App.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(email, senderName);
        helper.setTo(usuario.getEmail());
        helper.setSubject(subject);

        content = content.replace("[[name]]", usuario.getUsername());
        content = content.replace("[[pass]]", nuevaContrasenya);
        helper.setText(content, true);

        mailSender.send(message);
    }

    public void enviarEmailCambioDeContrasenya(User user) throws MessagingException, UnsupportedEncodingException {
        String senderName = "SleepCalm";
        String subject = "Cambio de contraseña correcto";
        String content = "Estimado [[name]],<br>"
                + "Se ha cambiado su contraseña correctamente.<br/>"
                + "Sleepcalm App.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(email, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        helper.setText(content, true);

        mailSender.send(message);
    }

}
