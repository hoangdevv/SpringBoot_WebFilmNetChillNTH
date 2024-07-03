package com.nthnetchill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment extends Base{
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "servicePlan_id")
    ServicePlan servicePlan;

    @ManyToOne
    @JoinColumn(name = "paymentMethod_id")
    PaymentMethod paymentMethod;

    private BigDecimal totalPrice;
    private Date paymentDate;
    private String status;

    private String message;
    private String url;
}
