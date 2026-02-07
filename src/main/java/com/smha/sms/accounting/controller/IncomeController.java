package com.smha.sms.accounting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/income")
public class IncomeController {

    @GetMapping("/list")
    public String list(){
        return "account/incomeList";
    }

    @GetMapping("/form")
    public String form(){
        return "account/incomeForm";
    }
}
