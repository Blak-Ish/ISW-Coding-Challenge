package com.isw.bookstore.service;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.model.Genre;
import org.springframework.data.domain.Pageable;

public interface BookService {

    ApiResponse addNewBook(BookDto bookDto);

    ApiResponse updateBook(BookDto bookDto);

    ApiResponse deleteBook(Long id);

    ApiResponse getBook(Long id);

    ApiResponse searchForBook(String title, String author, String year , Genre genre , Pageable pageable);


}
