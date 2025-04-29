package com.techie.microservices.notification.service;


import com.techie.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;


    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got message from order-placed topic {}",orderPlacedEvent);
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("pathaksoham2003@gmail.com");
            messageHelper.setTo(orderPlacedEvent.getEmail().toString());
            messageHelper.setSubject(String.format("Your order with ordernumber %s is place successfully",orderPlacedEvent.getOrderNumber().toString()));
            messageHelper.setText(String.format("""
                    Hi
                    
                    Your order with order number %s is not placed successfully.
                    Best Regards,
                    Soham Pathak
                    
                    """,orderPlacedEvent.getOrderNumber().toString()));
        };

        try{
            javaMailSender.send(mimeMessagePreparator);
            log.info("Mail sent successfully");
        }catch(MailException e) {
            log.error("Exception occured ",e);
            throw new RuntimeException("Exception occured ",e);
        }
    }

}
