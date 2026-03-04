package com.smha.sms.accounting.controller;

import com.smha.sms.accounting.model.dto.request.PaymentRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.entity.Payment;
import com.smha.sms.accounting.service.InvoiceService;
import com.smha.sms.accounting.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final InvoiceService invoiceService;

    @PostMapping("/collect")
    public String collectPayment(@ModelAttribute PaymentRequest request,
                                 RedirectAttributes redirectAttributes) {

        Payment payment = paymentService.collects(request);

        redirectAttributes.addFlashAttribute("successMessage",
                "Payment Successful. Transaction No: " + payment.getTransactionNo());

        return "redirect:/invoices/view/" + request.getInvoiceId() + "?source=payment";
    }

    @GetMapping("/payment/{id}")
    public String invoicePaymentPage(@PathVariable Long id, Model model) {

        Invoice invoice = invoiceService.getInvoiceById(id);

        model.addAttribute("invoice", invoice);

        return "account/invoicePayment";
    }
    @GetMapping("/list")
    public String payment(@RequestParam(required = false) String transactionNo,
                          Model model){

        List<Payment> payments = paymentService.paymentList(transactionNo);

        model.addAttribute("payments", payments);
        model.addAttribute("transactionNo", transactionNo);

        return "account/paymentList";
    }

    @GetMapping("/print/{id}")
    public String printPayment(@PathVariable Long id, Model model) {

        Payment payment = paymentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        model.addAttribute("payment", payment);

        return "account/paymentPrint";
    }
}
