package com.huitai.core.message.utils;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * description: MailService <br>
 * date: 2020/5/7 11:25 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Service
public class MailService {

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String from;

    // 发送人昵称
    @Value("${spring.mail.nickname}")
    private String nickname;

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * description: 发送邮件 <br>
     * version: 1.0 <br>
     * date: 2020/5/7 14:29 <br>
     * author: XJM <br>
     */
    public void sendEmail(MessageConverter messageConverter) throws UnsupportedEncodingException, MessagingException {
        logger.info("发送邮件: to={},subject={},content={}，filePath={}", messageConverter.getTo(), messageConverter.getTitle(), messageConverter.getContent(), JSON.toJSON(messageConverter.getFilePaths() == null ? "" : messageConverter.getFilePaths()));
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        message.setFrom(new InternetAddress(from, nickname, "UTF-8"));
        helper = new MimeMessageHelper(message, true);
        helper.setTo(messageConverter.getTo());
        helper.setSubject(messageConverter.getTitle());
        helper.setText(messageConverter.getContent(), true);
        List<String> filePaths = messageConverter.getFilePaths();
        if (filePaths != null && filePaths.size() > 0) {
            for (String filePath : filePaths) {
                //读取文件
                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = file.getFilename();
                helper.addAttachment(fileName, file);
            }
        }
        javaMailSender.send(message);
        logger.info("发送邮件成功");

    }
}
