package com.ilongross.communal_payments.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunalDto {

    private Integer id;
    private Integer userId;

    private String houseMaintenance;
    private String currentRepair;
    private String garbageRemove;
    private String elevatorMaintenance;
    private String electricity;
    private String coldWater;
    private String hotWater;

    private boolean houseMaintenanceOn;
    private boolean currentRepairOn;
    private boolean garbageRemoveOn;
    private boolean elevatorMaintenanceOn;
    private boolean electricityOn;
    private boolean coldWaterOn;
    private boolean hotWaterOn;

}
