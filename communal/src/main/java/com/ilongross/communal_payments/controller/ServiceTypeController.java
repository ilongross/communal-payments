package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.ServiceTypeDto;
import com.ilongross.communal_payments.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/communal/service_type")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String getMenu() {
        return "service_type_menu";
    }

    @GetMapping("/all")
    public String getAllServiceType(Model model) {
        var servicesList = serviceTypeService.getAllServiceType();
        model.addAttribute("servicesList", servicesList);
       return "services_list";
    }

    @GetMapping("/{id}")
    public String getServiceTypeById(@PathVariable Integer id, Model model) {
        var service = serviceTypeService.getServiceTypeById(id);
        model.addAttribute("service", service);
        return "service";
    }

    @PostMapping("/edit/{id}")
    public String updateServiceTypeForm(@ModelAttribute ServiceTypeDto serviceTypeDto, Model model) {
        model.addAttribute("service", serviceTypeDto);
        return "service_type_edit_form";
    }

    @PostMapping("/edit")
    public String updateServiceTypeResult(@ModelAttribute ServiceTypeDto dto, Model model) {
        var service = serviceTypeService.updateServiceTariff(dto);
        model.addAttribute("service", service);
        return "services_list";
    }

}
