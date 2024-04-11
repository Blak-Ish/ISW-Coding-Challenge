package com.isw.bookstore.service.impl;

import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.exceptions.BookExistsException;
import com.isw.bookstore.exceptions.NotFoundException;
import com.isw.bookstore.model.Book;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.repository.BookRepository;
import com.isw.bookstore.service.BookService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final EntityManager em;
    @Override
    public ApiResponse addNewBook(BookDto bookDto) {

        //TODO validate bookdto request

        if(bookRepository.existsByIsbn(bookDto.getIsbn())){
            throw  new BookExistsException("Book with ISBN ["+bookDto.getIsbn()+"] already exists");
        }

        Book book = Book.builder()
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .genre(bookDto.getGenre())
                .title(bookDto.getTitle())
                .yearOfPub(bookDto.getYearOfPub())
                .build();

        book = bookRepository.save(book);

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Book added successfully")
                .respBody(book)
                .build();
    }

    @Override
    public ApiResponse updateBook(BookDto bookDto) {

        Optional<Book> fetch = bookRepository.findById(bookDto.getId());

        if(fetch.isEmpty()){
            throw new NotFoundException("Book with Id supplied not found");
        }

        Book book = fetch.get();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setYearOfPub(bookDto.getYearOfPub());

       book = bookRepository.save(book);

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Book updated successfully")
                .respBody(book)
                .build();
    }

    @Override
    public ApiResponse deleteBook(Long id) {
        Optional<Book> fetch = bookRepository.findById(id);

        if(fetch.isEmpty()){
            throw new NotFoundException("Book with Id supplied not found");
        }

        bookRepository.deleteById(id);

        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Book deleted successfully")
                .build();
    }

    @Override
    public ApiResponse getBook(Long id) {
        Optional<Book> fetch = bookRepository.findById(id);

        if(fetch.isEmpty()){
            throw new NotFoundException("Book with Id supplied not found");
        }
        return ApiResponse.builder()
                .respCode("00")
                .respDescription("Book fetched successfully")
                .respBody(fetch.get())
                .build();
    }

    @Override
    public ApiResponse searchForBook(String title, String author, String year, Genre genre,Pageable pageable) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        int totalRows;
        TypedQuery<Book> query;

        Root<Book> bookRoot = cq.from(Book.class);
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(title)) {
            predicates.add(cb.like(bookRoot.get("title"), '%' + title + '%'));
        }

        if (StringUtils.isNotEmpty(author)) {
            predicates.add(cb.like(bookRoot.get("author"), '%' + author + '%'));
        }

        if (StringUtils.isNotEmpty(year)) {
            predicates.add(cb.equal(bookRoot.get("yearOfPub"), year));
        }

        if (Objects.nonNull(genre)) {
            predicates.add(cb.equal(bookRoot.get("genre"), genre));
        }

        cq.where(predicates.toArray(new Predicate[]{}));
        cq.orderBy(cb.desc(bookRoot.get("id")));

        query = em.createQuery(cq);
        totalRows = query.getResultList().size();

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        Page<Book> fetched = new PageImpl<>(query.getResultList(), pageable, totalRows);


        Map<String, Object> pagDetails = new HashMap<>();
        pagDetails.put("size", fetched.getSize());
        pagDetails.put("numberElements", fetched.getNumberOfElements());
        pagDetails.put("totalElements", fetched.getTotalElements());
        pagDetails.put("totalPages", fetched.getTotalPages());
        pagDetails.put("number", fetched.getNumber());

        Map<String , Object> data = new HashMap<>();
        data.put("records", fetched.getContent());
        data.put("pageDetails", pagDetails);

        return ApiResponse.builder()
                .respCode("00")
                .respBody(data)
                .build();
    }

}
