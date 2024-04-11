package com.isw.bookstore.controller;


import com.isw.bookstore.dto.ApiResponse;
import com.isw.bookstore.dto.BookDto;
import com.isw.bookstore.model.Genre;
import com.isw.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BooksApi {
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNewBook( @Validated @RequestBody BookDto request)  {
        return ResponseEntity.ok(bookService.addNewBook(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateBook(@RequestBody BookDto request)  {
        return ResponseEntity.ok(bookService.updateBook(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id)  {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchForBook(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author,
                                                     @RequestParam(required = false) String year,
                                                     @RequestParam(required = false) Genre genre,
                                                     @RequestParam (defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(bookService.searchForBook(title ,author , year , genre , paging));
    }
}
