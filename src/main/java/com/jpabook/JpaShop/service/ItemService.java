package com.jpabook.JpaShop.service;

import com.jpabook.JpaShop.controller.BookForm;
import com.jpabook.JpaShop.domain.item.Book;
import com.jpabook.JpaShop.domain.item.Item;
import com.jpabook.JpaShop.repository.ItemRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Long saveItem(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public Item fineOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Transactional
    public void updateBook(BookForm bookForm) {
        Book book = (Book) itemRepository.findOne(bookForm.getId());


        book.setName(bookForm.getName());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setPrice(bookForm.getPrice());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
    }
}
