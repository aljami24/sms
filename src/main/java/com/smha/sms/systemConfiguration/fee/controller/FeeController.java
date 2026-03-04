package com.smha.sms.systemConfiguration.fee.controller;

import com.smha.sms.academic.model.repository.ClassRoomRepository;
import com.smha.sms.academic.model.repository.SectionRepository;
import com.smha.sms.academic.model.repository.VersionRepository;
import com.smha.sms.academic.model.repository.YearRepository;
import com.smha.sms.accounting.model.enums.FeeType;
import com.smha.sms.accounting.model.repository.PaymentTypeRepository;
import com.smha.sms.systemConfiguration.fee.model.dto.request.FeeRequestDto;
import com.smha.sms.systemConfiguration.fee.model.dto.response.FeeResponseDto;
import com.smha.sms.systemConfiguration.fee.service.FeeService;
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
public class FeeController {
    private final ClassRoomRepository classRoomRepository;
    private final VersionRepository versionRepository;
    private final SectionRepository sectionRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final FeeService feeService;
    private final YearRepository yearRepository;

    @GetMapping("/feeForm")
    public String feeForm(Model model) {
        FeeRequestDto feeRequestDto = new FeeRequestDto();

        model.addAttribute("feeForm", feeRequestDto);
        model.addAttribute("studentClass", classRoomRepository.findAll());
        model.addAttribute("version", versionRepository.findAll());
        model.addAttribute("section", sectionRepository.findAll());
        model.addAttribute("feeType", FeeType.values());
        model.addAttribute("year", yearRepository.findAll());

        return "configuration/feesForm";
    }

    @PostMapping("/feeSave")
    public String saveFee(FeeRequestDto feeRequestDto) {
        feeService.saveFee(feeRequestDto);
        return "redirect:/configer/feelist";
    }

    @GetMapping("/feelist")
    public String feeList(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int pageSize,
                          Model model) {
        Page<FeeResponseDto> feePage = feeService.feeList(page, pageSize, "id", "DESC");
        model.addAttribute("feeList", feePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", feePage.getTotalPages());
        return "configuration/feesList";
    }

    @GetMapping("/feeEdit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FeeRequestDto feeRequestDto = feeService.getFeeById(id);
        if (feeRequestDto == null) {
            return "redirect:/configer/feelist";
        }

        model.addAttribute("feeForm", feeRequestDto);
        model.addAttribute("feeId", id);
        model.addAttribute("studentClass", classRoomRepository.findAll());
        model.addAttribute("version", versionRepository.findAll());
        model.addAttribute("section", sectionRepository.findAll());
        model.addAttribute("feeType",FeeType.values());
        model.addAttribute("year", yearRepository.findAll());
        model.addAttribute("isEdit", true);

        return "configuration/feesForm";
    }

    @PostMapping("/feeUpdate")
    public String updateFee(@RequestParam Long id, FeeRequestDto feeRequestDto) {
        feeService.updateFee(id, feeRequestDto);
        return "redirect:/configer/feelist";
    }

    @PostMapping("/feeDelete/{id}")
    public String deleteFee(@PathVariable Long id) {
        feeService.deleteFee(id);
        return "redirect:/configer/feelist";
    }
}
