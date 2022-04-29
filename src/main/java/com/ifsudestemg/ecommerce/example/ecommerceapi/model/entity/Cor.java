package com.ifsudestemg.ecommerce.example.ecommerceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCor;
    private String cor;
}
