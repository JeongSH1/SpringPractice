package com.jpabook.JpaShop.service;

import com.jpabook.JpaShop.domain.Address;
import com.jpabook.JpaShop.domain.Member;
import com.jpabook.JpaShop.domain.Order;
import com.jpabook.JpaShop.domain.OrderStatus;
import com.jpabook.JpaShop.domain.item.Book;
import com.jpabook.JpaShop.domain.item.Item;
import com.jpabook.JpaShop.exception.NotEnoughStockQuantity;
import com.jpabook.JpaShop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @PersistenceContext EntityManager em;
    @Autowired
    OrderRepository orderRepository;
    @Autowired OrderService orderService;


    @Test
    void 주문_테스트() {
        //given
        Member member = createMember("정", "서울", "스트릿", "123-333");
        Item item = createItem("예쁜 책", 10000, 10);

        //when
        Long saveOrderId1 = orderService.order(member.getId(), item.getId(), 5);
        Long saveOrderId2 = orderService.order(member.getId(), item.getId(), 3);

        //then
        Order order = orderRepository.findOne(saveOrderId1);
        assertThat(order).isEqualTo(orderRepository.findOne(saveOrderId1));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(order.getTotalPrice()).isEqualTo(50000);
        assertThat(order.getOrderItems().size()).isEqualTo(1);
        assertThat(item.getStockQuantity()).isEqualTo(2);
        System.out.println("member.toString() = " + member.toString());

    }

    @Test
    void 상품부족예외_테스트() {
        //given
        Member member = createMember("jung", "수원", "집", "11-11");
        Item item = createItem("멋진 책", 10000, 2);

        //when

        //then
        assertThrows(NotEnoughStockQuantity.class, () -> {
            Long saveOrderId = orderService.order(member.getId(), item.getId(), 5);
        });

    }




    private Member createMember(String name, String city, String street, String zipcode) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address(city, street, zipcode));
        em.persist(member);
        return member;
    }

    private Item createItem(String name, int price, int stockQuantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
        em.persist(item);

        return item;
    }

}