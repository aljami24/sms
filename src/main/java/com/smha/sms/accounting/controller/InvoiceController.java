package com.smha.sms.accounting.controller;

import com.smha.sms.academic.model.entity.Year;
import com.smha.sms.accounting.model.dto.request.InvoiceGenerateRequest;
import com.smha.sms.accounting.model.dto.request.InvoiceItemRequest;
import com.smha.sms.accounting.model.entity.Invoice;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.accounting.model.enums.InvoicePaymentStatus;
import com.smha.sms.accounting.model.repository.InvoiceRepository;
import com.smha.sms.accounting.service.InvoiceService;
import com.smha.sms.student.model.entity.Student;
import com.smha.sms.student.model.entity.StudentAcademicRecord;
import com.smha.sms.student.service.StudentService;
import com.smha.sms.systemConfiguration.fee.model.entity.Fee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;

@Controller
@RequestMapping("/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final StudentService studentService;
    private final InvoiceRepository invoiceRepository;

    // =========================================================
    // CREATE INVOICE PAGE
    // =========================================================
    @GetMapping("/create/{studentId}")
    public String createInvoice(@PathVariable Long studentId,
                                @RequestParam(required = false) Long yearId,
                                Model model) {

        Student student = studentService.getStudent(studentId);
        model.addAttribute("showStudentDetail", student);

        List<Year> years = student.getStudentAcademicRecords()
                .stream()
                .map(StudentAcademicRecord::getYear)
                .distinct()
                .toList();

        model.addAttribute("years", years);

        // Default latest year
        if (yearId == null && !years.isEmpty()) {
            Year latestYear = years.stream()
                    .max(Comparator.comparing(Year::getName))
                    .orElse(null);

            if (latestYear != null) {
                yearId = latestYear.getId();
            }
        }

        InvoiceGenerateRequest invoiceRequest = new InvoiceGenerateRequest();
        invoiceRequest.setStudentId(studentId);

        if (yearId != null) {

            invoiceRequest.setYearId(yearId);

            Map<String, Object> data =
                    invoiceService.getStudentFeeDetails(studentId, yearId);

            StudentAcademicRecord record =
                    (StudentAcademicRecord) data.get("record");

            List<Fee> availableFees =
                    (List<Fee>) data.get("fees");

            if (record == null) {
                throw new RuntimeException("Academic record not found.");
            }

            Long cvsId = record.getClassroomVersionSection().getId();
            invoiceRequest.setClassroomVersionSectionId(cvsId);

            // =====================================================
            // CHECK UNPAID INVOICE (NO REDIRECT → SEND TO MODAL)
            // =====================================================
            Optional<Invoice> unpaid =
                    invoiceService.findUnpaidInvoice(studentId, yearId, cvsId);

            unpaid.ifPresent(invoice ->
                    model.addAttribute("unpaidInvoice", invoice)
            );

            // =====================================================
            // ALREADY PAID DATA
            // =====================================================
            Map<String, Object> paidData =
                    invoiceService.getAlreadyPaidData(studentId, yearId, cvsId);

            model.addAttribute("alreadyPaidFeeTypes",
                    paidData.get("paidFeeTypes"));

            model.addAttribute("alreadyPaidMonths",
                    paidData.get("paidMonths"));

            // =====================================================
            // PREVIOUS YEAR DUE CHECK
            // =====================================================
            Map<Year, List<Fee>> missingFees = invoiceService.findMissingFees(studentId, yearId);

            // =====================================================
            // BUILD REQUEST ITEMS
            // =====================================================
            for (Fee fee : availableFees) {

                InvoiceItemRequest item = new InvoiceItemRequest();
                item.setFeeType(fee.getFeeType());
                item.setPerFeeAmount(
                        BigDecimal.valueOf(fee.getFeesAmount())
                );
                item.setSelected(false);

                invoiceRequest.getItems().add(item);
            }

            model.addAttribute("availableFees", availableFees);
            model.addAttribute("months", Arrays.asList(Month.values()));
            model.addAttribute("selectedYearId", yearId);
            model.addAttribute("missingFees", missingFees);
        }

        model.addAttribute("invoiceRequest", invoiceRequest);

        return "account/studentInvoice";
    }


    // =========================================================
// REVIEW INVOICE (STEP 2)
// =========================================================
    @PostMapping("/review")
    public String reviewInvoice(@ModelAttribute InvoiceGenerateRequest request,
                                Model model) {

        // Filter only selected items
        request.setItems(
                request.getItems()
                        .stream()
                        .filter(item ->
                                item.isSelected()
                                        || (item.getMonths() != null
                                        && !item.getMonths().isEmpty())
                        )
                        .toList()
        );

        if (request.getItems().isEmpty()) {
            model.addAttribute("error",
                    "Please select at least one fee.");
            return "account/studentInvoice";
        }

        model.addAttribute("invoiceRequest", request);

        return "account/invoiceReview";
    }


    // =========================================================
    // SAVE INVOICE
    // =========================================================
    @PostMapping("/save")
    public String saveInvoice(@ModelAttribute InvoiceGenerateRequest request,
                              RedirectAttributes redirectAttributes) {

        try {

            Invoice savedInvoice =
                    invoiceService.saveInvoice(request);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Invoice saved successfully!"
            );

            return "redirect:/invoices/view/" + savedInvoice.getId();

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );

            return "redirect:/invoices/create/"
                    + request.getStudentId()
                    + "?yearId=" + request.getYearId();
        }
    }


    @GetMapping("/list")
    public String invoiceList(@RequestParam(required = false) String invoiceNo,
                              Model model) {

        List<Invoice> invoices;

        if (invoiceNo != null && !invoiceNo.isEmpty()) {
            invoices = invoiceService.invoiceList(invoiceNo);
        } else {
            invoices = invoiceRepository.findAll(Sort.by(Sort.Direction.DESC, "invoiceDate"));
        }

        model.addAttribute("invoices", invoices);
        model.addAttribute("invoiceNo", invoiceNo);

        return "account/invoiceList";
    }


    // =========================================================
    // VIEW INVOICE
    // =========================================================
    @GetMapping("/view/{id}")
    public String viewInvoice(@PathVariable Long id,
                              @RequestParam(required = false) String source,
                              Model model) {

        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);

        // 🔥 Title control
        if ("payment".equals(source)) {
            model.addAttribute("pageTitle", "STUDENT PAYMENT");
        } else {
            model.addAttribute("pageTitle", "STUDENT FEE INVOICE");
        }

        return "account/invoiceView";
    }


    // =========================================================
    // DELETE INVOICE (ONLY IF UNPAID)
    // =========================================================
    @PostMapping("/delete/{id}")
    public String deleteInvoice(@PathVariable Long id,
                                @RequestParam Long studentId,
                                RedirectAttributes redirectAttributes) {

        Invoice invoice = invoiceService.getInvoiceById(id);

        if (invoice.getInvoicePaymentStatus()
                != InvoicePaymentStatus.UNPAID) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Only unpaid invoices can be deleted."
            );

            return "redirect:/invoices/view/" + id;
        }

        invoiceService.invoiceDelete(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Invoice deleted successfully."
        );

        return "redirect:/invoices/create/" + studentId;
    }

}