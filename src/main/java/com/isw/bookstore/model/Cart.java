package com.isw.bookstore.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isw.bookstore.security.credentials.Merchant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    //Persist items as large object , empty upon purchase payment
    @Lob
    private String items;


}
