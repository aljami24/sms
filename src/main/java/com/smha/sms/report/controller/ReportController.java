package com.smha.sms.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @GetMapping("/all")
    public String report (){
        return "report/report";
    }


}
