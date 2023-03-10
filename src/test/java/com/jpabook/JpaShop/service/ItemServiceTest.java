package com.jpabook.JpaShop.service;

import com.jpabook.JpaShop.domain.item.Book;
import com.jpabook.JpaShop.domain.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Test
    @Rollback(value = false)
    void saveTest() {
        Book book = new Book();
        book.setStockQuantity(10);
        book.setName("book");
        book.setPrice(10000);
        book.setIsbn("as");
        book.setAuthor("assa");

        Long saveId = itemService.saveItem(book);
        Assertions.assertThat(book).isEqualTo(itemService.fineOne(saveId));

    }


}