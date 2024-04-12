package com.isw.bookstore;

import com.google.gson.Gson;
import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.service.BookService;
import com.isw.bookstore.service.impl.BookServiceImpl;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testAddBook() {

        when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            book.setId(1L);
            return book;
        });

        BookDto book = BookDto.builder()
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build();

        ApiResponse response = bookService.addNewBook(book);

        assertTrue(response.getRespBody().toString().contains(book.getIsbn()));
    }

    @Test
    void testUpdateBook() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(Book.builder()
                .id(1L)
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build()));
        when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> {
            Book book = invocation.getArgument(0);
            book.setId(1L);
            return book;
        });
        Gson gson = new Gson();
        BookDto book = BookDto.builder()
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build();

        ApiResponse response = bookService.addNewBook(book);

        BookDto dto = gson.fromJson(gson.toJson(response.getRespBody()), BookDto.class);

        dto.setTitle("UPDATEDTITLE");

        ApiResponse apiResponse = bookService.updateBook(dto);

        assertTrue(apiResponse.getRespBody().toString().contains("UPDATEDTITLE"));
    }

    @Test
    void testDeleteBook() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(Book.builder()
                .id(1L)
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build()));

        ApiResponse response = bookService.deleteBook(1L);

        assertEquals("00", response.getRespCode());
    }

    @Test
    void testGetBook() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(Book.builder()
                .id(1L)
                .title("testbook")
                .yearOfPub("2024")
                .isbn("1000-1000")
                .genre(Genre.MYSTERY)
                .build()));

        ApiResponse response = bookService.getBook(1L);

        assertTrue(response.getRespBody().toString().contains("testbook"));
    }


}
