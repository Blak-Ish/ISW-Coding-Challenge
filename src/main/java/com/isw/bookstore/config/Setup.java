package com.isw.bookstore.config;

import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.security.credentials.Merchant;
import com.isw.bookstore.security.credentials.MerchantRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class Setup implements CommandLineRunner {
    @Value("${app.default.username}")
    private String defaultUser;
    @Value("${app.default.password}")
    private String defaultPassword;
    @Value("${app.default.book.title}")
    private String defaultBookTitle;
    @Value("${app.default.book.genre}")
    private String defaultBookGenre;
    @Value("${app.default.book.isbn}")
    private String defaultBookIsbn;
    @Value("${app.default.book.author}")
    private String defaultBookAuthor;
    @Value("${app.default.book.year}")
    private String defaultBookYear;

    private final BookRepository bookRepository;
    private final MerchantRepo merchantRepo;
    private final PasswordEncoder encoder;
    @Override
    public void run(String... args) throws Exception {

        Merchant merchant = Merchant.builder()
                .username(defaultUser)
                .password(encoder.encode(defaultPassword))
                .build();

        merchantRepo.save(merchant);
        log.info("default merchant ["+defaultUser+"] created ..");

        Book book = Book.builder()
                .isbn(defaultBookIsbn)
                .title(defaultBookTitle)
                .author(defaultBookAuthor)
                .genre(Genre.valueOf(defaultBookGenre))
                .yearOfPub(defaultBookYear)
                .build();

        bookRepository.save(book);

        log.info("default book ["+defaultBookTitle+"] created ..");


    }


}
