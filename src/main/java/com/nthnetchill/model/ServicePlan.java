package com.nthnetchill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "service_plan")
public class ServicePlan extends Base{
    private String name;
    private String description;
    private BigDecimal price;
    private Date durationDate;

    @OneToMany(mappedBy = "servicePlan")
    Set<Payment> payments;
}
