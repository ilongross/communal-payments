package com.ilongross.communal_payments.model.entity.id_classes;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
public class AccountDebtEntityId implements Serializable {

    private Integer accountId;

}
