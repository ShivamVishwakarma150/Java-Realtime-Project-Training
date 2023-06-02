Spring Boot provides a convenient way to send emails using the Spring Framework's `JavaMailSender` interface. To use email functionality in your Spring Boot application, you need to follow these steps:

Step 1: Add Spring Boot Starter Mail dependency
In your `pom.xml` file (Maven) or `build.gradle` file (Gradle), add the following dependency:

Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

Gradle:
```groovy
implementation 'org.springframework.boot:spring-boot-starter-mail'
```

Step 2: Configure email properties
In your `application.properties` file, configure the email properties for your SMTP server. Here's an example configuration for a Gmail SMTP server:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Make sure to replace `your-email@gmail.com` with your actual Gmail address and `your-email-password` with your Gmail password. If you're using a different SMTP server, adjust the properties accordingly.

Step 3: Send an email
You can now use the `JavaMailSender` interface to send emails. Here's an example of sending a simple email:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
```

In your service or controller class, autowire the `EmailService` and use the `sendEmail` method to send emails:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
    }
}
```

In the example above, `EmailRequest` is a simple DTO (Data Transfer Object) class that holds the email details like `to`, `subject`, and `body`. You can create your own DTO class according to your needs.

That's it! You have now set up email functionality in your Spring Boot application using Spring's `JavaMailSender` interface. You can use this approach to send emails for various use cases in your application.

<br/>

If you want to send more complex emails with attachments, HTML content, or customized headers, you can use the `MimeMessage` and `MimeMessageHelper` classes provided by the JavaMail API. Here's an example of sending an email using `MimeMessage` and `MimeMessageHelper` in a Spring Boot application:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        // Add attachment
        FileSystemResource attachment = new FileSystemResource(new File(attachmentPath));
        helper.addAttachment(attachment.getFilename(), attachment);

        mailSender.send(message);
    }
}
```

In this example, we have added a `sendEmailWithAttachment` method that takes an additional parameter `attachmentPath`, which represents the file path of the attachment to be included in the email. The `MimeMessageHelper` class provides methods to add attachments, inline images, and set other properties of the email.

To send an email with attachments, you can call the `sendEmailWithAttachment` method from your service or controller class:

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public void sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        emailService.sendEmailWithAttachment(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody(), emailRequest.getAttachmentPath());
    }
}
```

Note that in the example above, the `sendEmailWithAttachment` method throws a `MessagingException` which you can handle according to your application's needs.

You can customize the `MimeMessage` and `MimeMessageHelper` objects to suit your specific email requirements, such as adding HTML content, setting custom headers, or including inline images. The Spring Framework provides various methods to accomplish these tasks using the `MimeMessageHelper` class.

<br/>
<br/>
<br/>

```java
    @Component
    public class EmailUtils {
        
        @Autowired
        private JavaMailSender mailSender;
        
        public boolean sendEmail(String to,String subject,String body) {
            boolean isMailSent = false;
            
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(body,true);
                
                mailSender.send(mimeMessage);
                isMailSent=true;
                
                }catch(Exception e) {
                    e.printStackTrace();
                }
            
            return isMailSent;
        }
    }

```

The code you provided is a `EmailUtils` class in Spring Boot that utilizes `JavaMailSender` and `MimeMessageHelper` to send emails. It is annotated with `@Component`, indicating that it should be automatically detected and registered as a Spring bean.

The `sendEmail` method takes three parameters: `to` (email recipient), `subject` (email subject), and `body` (email body). It creates a `MimeMessage` using the `JavaMailSender` and initializes a `MimeMessageHelper` instance with the created `MimeMessage`.

Inside the `MimeMessageHelper`, the `setTo`, `setSubject`, and `setText` methods are used to set the recipient, subject, and body of the email, respectively. The `setText` method's second parameter is set to `true`, indicating that the email body contains HTML content.

Finally, the `MimeMessage` is sent using the `mailSender` by invoking the `send` method. If an exception occurs during the process, it will be printed and the method will return `false`. Otherwise, the method returns `true` to indicate that the email was successfully sent.

You can use this `EmailUtils` class in your Spring Boot application to send emails by autowiring it and calling the `sendEmail` method with the appropriate parameters.