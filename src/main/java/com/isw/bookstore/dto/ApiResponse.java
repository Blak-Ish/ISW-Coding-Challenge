package com.isw.bookstore.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String respCode;
    private String respDescription;
    private Object respBody;

    @JsonIgnore
    private HttpStatus httpStatus;



    public ApiResponse(String description, String code, HttpStatus status){
        respCode = code;
        httpStatus = status;
        respDescription = description;
    }

    @Override
    public String toString() {
        return "Response{" +
                "respCode='" + respCode + '\'' +
                ", respDescription='" + respDescription + '\'' +
                ", respBody=" + respBody +
                '}';
    }
}
