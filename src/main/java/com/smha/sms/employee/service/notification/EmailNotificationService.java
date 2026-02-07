package com.smha.sms.employee.service.notification;

import com.smha.sms.employee.model.entity.Employee;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailNotificationService implements NotificationService{

    @Override
    public void send (String message, Employee employee){
        System.out.println("Email send to" + employee.getName());
    }
}
