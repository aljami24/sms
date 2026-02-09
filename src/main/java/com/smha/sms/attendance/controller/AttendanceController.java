package com.smha.sms.attendance.controller;

import com.smha.sms.attendance.model.entity.Attendance;
import com.smha.sms.attendance.model.repository.AttendanceRepository;
import com.smha.sms.attendance.service.AttendanceService;
import com.smha.sms.common.enums.AttendanceStatus;
import com.smha.sms.common.enums.EmployeeType;
import com.smha.sms.employee.model.dto.EmployeeFilter;
import com.smha.sms.employee.model.dto.response.EmployeeResponseDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceRepository attendanceRepository;


    @GetMapping("/employee")
    public String attendanceForm(@ModelAttribute("filter") EmployeeFilter filter, Model model) {
        List<EmployeeResponseDto> employees = attendanceService.getAllActiveEmployeesForAttendance(filter);

        model.addAttribute("employees", employees);
        model.addAttribute("employeeTypes", EmployeeType.values());
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("title", "Employee Attendance");
        return "attendance/attendanceMark";
    }

    @PostMapping("/employee/save")
    public String saveAttendance(
            @RequestParam(value = "allDisplayedIds", required = false) List<Long> allDisplayedIds,
            @RequestParam Map<String, String> allParams) {

        if (allDisplayedIds != null) {
            attendanceService.processEmployeeAttendance(allDisplayedIds, allParams);
        }

        return "redirect:/attendance/list?success";
    }

    @GetMapping("/list")
    public String attendanceReport(
            @RequestParam(required = false) EmployeeType type,
            @RequestParam(required = false) String date,
            Model model
    ) {

        // যদি date না দেয় → আজকের তারিখ
        LocalDate selectedDate =
                (date == null || date.isBlank())
                        ? LocalDate.now()
                        : LocalDate.parse(date);

        // নির্দিষ্ট দিনের সব attendance বের করা
        List<Attendance> attendanceList =
                attendanceRepository.findAllByDate(selectedDate);

        // type filter (optional)
        if (type != null) {
            attendanceList = attendanceList.stream()
                    .filter(a -> a.getEmployeeId().getEmployeeType() == type)
                    .toList();
        }

        model.addAttribute("today", LocalDate.now());
        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("employeeTypes", EmployeeType.values());
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("selectedType", type);

        return "attendance/attendanceList"; // তোমার report page
    }

//    @PostMapping("/status/toggle/{id}")
//    public String toggleStatus(@PathVariable Long id, @RequestParam(required = false) String date) {
//        attendanceService.toggleAttendanceStatus(id);
//
//        // এডিট শেষে আবার ওই তারিখের লিস্টেই ফেরত যাবে
//        return "redirect:/attendance/list?date=" + date;
//    }

    // ১. ইডিট পেজ দেখানো
    @GetMapping("/edit/{id}")
    public String editAttendancePage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));

        // আজকের তারিখ এবং হাজিরার তারিখ চেক করা
        if (!attendance.getDate().equals(LocalDate.now())) {
            // যদি তারিখ না মিলে তবে এরর মেসেজ দিয়ে লিস্টে ফেরত পাঠাবে
            redirectAttributes.addFlashAttribute("error", "আপনি শুধুমাত্র আজকের হাজিরা এডিট করতে পারবেন।");
            return "redirect:/attendance/list";
        }

        model.addAttribute("attendance", attendance);
        model.addAttribute("title", "Edit Attendance");
        return "attendance/attendanceEdit"; // নতুন HTML পেজ
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam AttendanceStatus status,
                               @RequestParam String date,
                               RedirectAttributes redirectAttributes) {

        Attendance attendance = attendanceRepository.findById(id).orElseThrow();

        // পুনরায় তারিখ চেক করা (নিরাপত্তার জন্য)
        if (!attendance.getDate().equals(LocalDate.now())) {
            redirectAttributes.addFlashAttribute("error", "অতীতের হাজিরা পরিবর্তন করা সম্ভব নয়।");
            return "redirect:/attendance/list";
        }

        attendance.setStatus(status);
        attendanceRepository.save(attendance);

        return "redirect:/attendance/list?date=" + date + "&success";
    }
}
