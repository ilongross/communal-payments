package com.ilongross.communal_payments.controller;

import com.ilongross.communal_payments.model.dto.PaymentDto;
import com.ilongross.communal_payments.model.dto.ServiceTypeDto;
import com.ilongross.communal_payments.service.ServiceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/communal/service_type")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ServiceTypeDto>> getAllServiceType() {
       return ResponseEntity
               .status(HttpStatus.FOUND)
               .body(serviceTypeService.getAllServiceType());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTypeDto> getServiceTypeById(@PathVariable Integer id) {
        return ResponseEntity
                .ok()
                .body(serviceTypeService.getServiceTypeById(id));
    }

    @PostMapping("/update_tariff")
    public ResponseEntity<ServiceTypeDto> updateServiceTariff(@RequestBody ServiceTypeDto dto) {
        return ResponseEntity
                .ok()
                .body(serviceTypeService.updateServiceTariff(dto));
    }


}
