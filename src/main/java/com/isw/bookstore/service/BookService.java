package com.isw.bookstore.service;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.model.Genre;
import org.springframework.data.domain.Pageable;

/**
 * Interface for managing books in the bookstore.
 * This interface defines methods for adding, updating,
 * retrieving, and deleting books from the store.
 * Implementations of this interface should provide
 * functionality to interact with the book repository
 * and perform CRUD operations on book entities.
 */
public interface BookService {

    /**
     * Add new Book to the service , validation done to check existence of @ISBN
     * @param bookDto
     * @return ApiResponse
     */
    ApiResponse addNewBook(BookDto bookDto);

    /**
     * Update an existing book , using the provided ID in the dto
     * @param bookDto
     * @return ApiResponse
     */
    ApiResponse updateBook(BookDto bookDto);

    /**
     * Delete an existing book
     * @param id
     * @return ApiResponse
     */
    ApiResponse deleteBook(Long id);

    /**
     * Get an existing book using the ID provided
     * @param id
     * @return ApiResponse
     */
    ApiResponse getBook(Long id);

    /**
     * Search for a book using the parameters , returns a paginated record of the books
     * @param title
     * @param author
     * @param year
     * @param genre
     * @param pageable
     * @return ApiResponse
     */
    ApiResponse searchForBook(String title, String author, String year , Genre genre , Pageable pageable);


}
