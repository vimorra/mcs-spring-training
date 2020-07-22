package com.mkyong;

import com.mkyong.error.BookNotFoundException;
import com.mkyong.error.BookUnSupportedFieldPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
public class BookController {

    @Autowired
    private Book book;

    private ArrayList<Book> books;

    public BookController(){
        books=new ArrayList<Book>();
        Book book1= new Book();
        book1.setId(1L);
        book1.setName("Dark Princess");
        book1.setAuthor("Cos");
        book1.setPrice(BigDecimal.valueOf(100));
        Book book2=new Book();
        book2.setId(2L);
        book2.setName("Design Thinking");
        book2.setAuthor("MCS");
        book2.setPrice(BigDecimal.valueOf(33));
        books.add(book1);
        books.add(book2);

    }

    // Find
    @GetMapping("/books")
    List<Book> findAll() {
        return books;
    }

    // Save
    @PostMapping("/books")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(@RequestBody Book newBook) {
        newBook.setId(new Random().nextLong());
        books.add(newBook);
        return newBook;
    }

    // Find
    @GetMapping("/books/{id}")
    Book findOne(@PathVariable Long id) {
        for (Book b : books) {
            if(b.getId()==id){
                return b;
            }
        }
        throw new BookNotFoundException(id);
    }

    // Save or update
    @PutMapping("/books/{id}")
    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

        for (Book b : books) {
            if(b.getId()==id){
                b.setName(newBook.getName());
                b.setAuthor(newBook.getAuthor());
                b.setPrice(newBook.getPrice());
                return b;
            }
        }
        newBook.setId(new Random().nextLong());
        books.add(newBook);
        return newBook;

    }

    @DeleteMapping("/books/{id}")
    void deleteBook(@PathVariable Long id) {
        for (Book b : books) {
            if(b.getId()==id){
                books.remove(b);
            }
        }
    }

}
