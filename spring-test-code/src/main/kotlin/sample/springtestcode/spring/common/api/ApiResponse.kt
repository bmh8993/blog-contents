package sample.springtestcode.spring.common.api

import org.springframework.http.HttpStatus

data class ApiResponse<T> private constructor( // copy에 의해서 기본생성자가 노출될 수 있음.
    val code: Int,
    val status: HttpStatus,
    val message: String,
    val data: T
) {
    companion object {
        private fun <T> of(status: HttpStatus, message: String, data: T): ApiResponse<T> {
            return ApiResponse(
                code = status.value(),
                status = status,
                message = message,
                data = data
            )
        }

        fun error(httpStatus: HttpStatus, message: String): ApiResponse<String> = of(httpStatus, message, "")

        fun <T> created(data: T): ApiResponse<T> = of(HttpStatus.CREATED, "", data)

        fun <T> ok(data: T): ApiResponse<T> = of(HttpStatus.OK, "", data)
    }
}