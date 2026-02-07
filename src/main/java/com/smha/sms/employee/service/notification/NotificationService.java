package com.smha.sms.employee.service.notification;

import com.smha.sms.employee.model.entity.Employee;
import org.springframework.stereotype.Service;


public interface NotificationService {
    void send (String message, Employee employee);
}
