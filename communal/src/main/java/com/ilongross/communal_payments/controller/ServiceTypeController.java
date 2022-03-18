package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.ServiceTypeDto;
import com.ilongross.communal_payments.service.ServiceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/communal/service_type")
@RequiredArgsConstructor
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

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

    @PostMapping("/update_tariff")
    public String updateServiceTariff(@RequestBody ServiceTypeDto dto, Model model) {
        var service = serviceTypeService.updateServiceTariff(dto);
        model.addAttribute("service", service);
        return "service";
    }

}
