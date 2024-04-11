package com.isw.bookstore.repository;

import com.isw.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book , Long> {

    Boolean existsByIsbn(String isbn);


}
