package com.isw.bookstore.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isw.bookstore.config.EnumValidator;
import com.isw.bookstore.model.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BookDto {
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Title must be provided")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Title can only contain letters and numbers")
    private String title;

    @EnumValidator(enumClass = Genre.class)
    private Genre genre;

    @NotEmpty(message = "ISBN must be provided")
    @Pattern(regexp = "^[0-9-]+$", message = "ISBN must contain only numbers and dashes")
    private String isbn;

    @NotEmpty(message = "Please provide author")
    private String author;

    @NotEmpty(message = "Year of publishing must be provided")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Invalid year format")
    private String yearOfPub;
}
