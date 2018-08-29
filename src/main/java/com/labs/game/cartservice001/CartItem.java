package com.labs.game.cartservice001;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "cartitems")
public class CartItem {
    @Id
    private Integer id;
    @Column
    private String customer;
    @Column
    private String product;
    @Column
    private int quantity;
}