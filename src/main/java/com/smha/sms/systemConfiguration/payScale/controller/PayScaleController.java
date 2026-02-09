package com.smha.sms.systemConfiguration.payScale.controller;

import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.systemConfiguration.payScale.model.dto.request.PayScaleRequestDto;
import com.smha.sms.systemConfiguration.payScale.model.dto.response.PayScaleResponseDto;
import com.smha.sms.systemConfiguration.payScale.service.PayScaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/configer")
public class PayScaleController {

    private final YearRepository yearRepository;
    private final PayScaleService payScaleService;

    @GetMapping("/payScaleForm")
    public String payScaleForm(Model model) {
        model.addAttribute("payScaleForm", new PayScaleRequestDto());
        model.addAttribute("year", yearRepository.findAll());
        return "configuration/payScaleForm";
    }

    @PostMapping("/payScaleSave")
    public String savePayScale(PayScaleRequestDto payScaleRequestDto) {
        payScaleService.savePayScale(payScaleRequestDto);
        return "redirect:/configer/payScaleList";
    }

    @GetMapping("/payScaleList")
    public String payScaleList(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int pageSize,
                               Model model) {
        Page<PayScaleResponseDto> payScalePage = payScaleService.payScaleList(page, pageSize, "id", "DESC");
        model.addAttribute("payScaleList", payScalePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", payScalePage.getTotalPages());
        return "configuration/payScaleList";
    }

    @GetMapping("/payScaleEdit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        PayScaleRequestDto payScaleRequestDto = payScaleService.getPayScaleById(id);
        if (payScaleRequestDto == null) {
            return "redirect:/configer/payScaleList";
        }

        model.addAttribute("payScaleForm", payScaleRequestDto);
        model.addAttribute("payScaleId", id);
        model.addAttribute("year", yearRepository.findAll());
        model.addAttribute("isEdit", true);

        return "configuration/payScaleForm";
    }

    @PostMapping("/payScaleUpdate")
    public String updatePayScale(@RequestParam Long id, PayScaleRequestDto payScaleRequestDto) {
        payScaleService.updatePayScale(id, payScaleRequestDto);
        return "redirect:/configer/payScaleList";
    }

    @PostMapping("/payScaleDelete/{id}")
    public String deletePayScale(@PathVariable Long id) {
        payScaleService.deletePayScale(id);
        return "redirect:/configer/payScaleList";
    }
}
