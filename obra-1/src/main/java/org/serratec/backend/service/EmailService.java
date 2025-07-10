package org.serratec.backend.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String remetente;

    public void enviarEmailComTemplate(String para, String assunto, String templatePath, Map<String, String> variaveis) {
        try {
            String html = carregarTemplate(templatePath, variaveis);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(html, true);
            helper.setFrom(remetente);

            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Erro ao enviar e-mail com template: " + e.getMessage(), e);
        }
    }

    private String carregarTemplate(String caminho, Map<String, String> variaveis) throws IOException {
        var resource = new ClassPathResource(caminho);
        String conteudo = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        for (var entry : variaveis.entrySet()) {
            conteudo = conteudo.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return conteudo;
    }
}
