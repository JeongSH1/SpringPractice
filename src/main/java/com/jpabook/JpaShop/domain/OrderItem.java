package com.jpabook.JpaShop.domain;

import com.jpabook.JpaShop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;

    private int count;

    public void cancel() {
        getItem().addStockQuantity(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public static OrderItem createOrderItem(Item item, int price, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(price);
        orderItem.setCount(count);

        item.removeStockQuantity(count);

        return orderItem;
    }
}
