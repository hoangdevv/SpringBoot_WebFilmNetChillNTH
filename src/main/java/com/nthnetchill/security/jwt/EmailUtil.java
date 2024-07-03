package com.nthnetchill.security.jwt;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    public static void sendOTPEmail(String toEmail, String otp) throws MessagingException {
        // Cấu hình thông tin mail server
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "hoangpubg286@gmail.com";
        String password = "vhel joua rqjf gyne";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Tạo đối tượng Session để kết nối với mail server
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Xác thực OTP");

            // Nội dung email HTML
            String htmlContent = "<h1 style='color: #2e6c80;'>Xác thực OTP</h1>"
                    + "<p>Xin chào,</p>"
                    + "<p>Cảm ơn bạn đã đăng ký WEB XEM PHIM NTH NET CHILL của chúng tôi.</p>"
                    + "<p>Mã OTP của bạn là: <strong>" + otp + "</strong></p>"
                    + "<p>Vui lòng không chia sẻ mã OTP này với bất kỳ ai.</p>"
                    + "<p>Trân trọng,</p>"
                    + "<p><strong>Đội ngũ hỗ trợ</strong></p>";

            // Đặt nội dung email dạng HTML
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Gửi email
            Transport.send(message);
            System.out.println("Gửi email thành công đến " + toEmail);
        } catch (MessagingException e) {
            System.out.println("Gửi email thất bại đến " + toEmail);
            throw e;
        }
    }
}
