package com.ilongross.communal_payments.service;


import com.ilongross.communal_payments.model.dto.ServiceTypeDto;

import java.util.List;

public interface ServiceTypeService {

    List<ServiceTypeDto> getAllServiceType();
    ServiceTypeDto getServiceTypeById(Integer id);
    ServiceTypeDto saveNewServiceType(ServiceTypeDto dto);
    ServiceTypeDto updateServiceTariff(ServiceTypeDto dto);

}
