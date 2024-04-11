package com.isw.bookstore;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.service.BookService;
import com.isw.bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testAddBook(){
        BookDto book = BookDto.builder()
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build();

     ApiResponse response = bookService.addNewBook(book);

        assertTrue(response.getRespBody().toString().contains(book.getIsbn()));
    }

    @BeforeEach
    void setups(){
        when(bookRepository.save(Mockito.any(Book.class)))
                .thenAnswer(i -> i.getArguments()[0]);
    }
}
