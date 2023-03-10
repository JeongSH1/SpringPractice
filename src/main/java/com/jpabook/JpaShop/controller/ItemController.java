package com.jpabook.JpaShop.controller;

import com.jpabook.JpaShop.domain.item.Book;
import com.jpabook.JpaShop.domain.item.Item;
import com.jpabook.JpaShop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm bookForm, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        Book book = new Book();
        book.setPrice(bookForm.getPrice());
        book.setName(bookForm.getName());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setIsbn(bookForm.getIsbn());
        book.setAuthor(bookForm.getAuthor());

        itemService.saveItem(book);
        return "redirect:/";

    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book) itemService.fineOne(itemId);
        BookForm form = new BookForm();

        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long ItemId, @ModelAttribute("form") BookForm bookForm) {

//        Book book = new Book();
//        book.setId(bookForm.getId());
//        book.setName(bookForm.getName());
//        book.setPrice(bookForm.getPrice());
//        book.setStockQuantity(bookForm.getStockQuantity());
//        book.setAuthor(bookForm.getAuthor());
//        book.setIsbn(bookForm.getIsbn());
        itemService.updateBook(bookForm);
        return "redirect:/items";

    }
}
