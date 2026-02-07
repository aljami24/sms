package com.smha.sms.employee.service.notification;

import com.smha.sms.employee.model.entity.Employee;
import org.springframework.stereotype.Service;

@Service("smsService")
public class SmsNotificationService implements NotificationService{

    @Override
    public void send(String message, Employee employee) {
        System.out.println("SMS Send to" + employee.getPhoneNumber());
    }
}
