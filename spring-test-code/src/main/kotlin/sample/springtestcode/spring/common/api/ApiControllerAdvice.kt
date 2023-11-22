package sample.springtestcode.spring.common.api

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice // ControllerAdvice와 차이?
class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    fun bindException(e: BindException): ApiResponse<String> {
        val errorMessage: String = e.bindingResult.allErrors[0].defaultMessage ?: ""
        return ApiResponse.error(HttpStatus.BAD_REQUEST, errorMessage)
    }
}