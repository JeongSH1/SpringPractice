package com.jpabook.JpaShop.domain.item;

import com.jpabook.JpaShop.domain.Category;
import com.jpabook.JpaShop.exception.NotEnoughStockQuantity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStockQuantity(int quantity) {

        int restQuantity = this.stockQuantity-quantity;

        if (restQuantity < 0) {
            throw new NotEnoughStockQuantity("need more stock");
        }
        this.stockQuantity = restQuantity;
    }
}
