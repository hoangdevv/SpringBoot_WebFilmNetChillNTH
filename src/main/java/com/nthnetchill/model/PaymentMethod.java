package com.nthnetchill.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "payment_method")
public class PaymentMethod extends Base{
    private String name;

    @OneToMany(mappedBy = "paymentMethod")
    Set<Payment> payments;
}
