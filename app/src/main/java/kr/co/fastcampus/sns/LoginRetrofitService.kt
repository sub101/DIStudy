package kr.co.fastcampus.sns

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author soohwan.ok
 * POST 메소드로 로그인(통신)을 할 수 있는 내용 정의
 */
interface LoginRetrofitService {

    @Headers("Content-Type:application/json;charset=UTF-8")
    @POST("users/login")
    suspend fun login(
        @Body requestBody:RequestBody
    ): NetworkResponse<String> // 응답값으로는 토큰을 받음
}