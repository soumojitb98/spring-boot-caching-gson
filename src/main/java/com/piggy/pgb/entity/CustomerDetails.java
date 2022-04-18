package com.piggy.pgb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "CUSTOMER_NAME")
    private String name;

    @Column(nullable = false, name = "ACCOUNT_BALANCE")
    private Double balance;

    @Column(nullable = false, name = "IS_DELETED")
    private int isDeleted = 0;
}
