package kr.co.fastcampus.sns

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * LoginRetrofitService를 가지고 실제로 통신을 함
 */
class UserRemoteDataSource constructor(
    private val service:LoginRetrofitService
) {
    suspend fun login(id:String, pw:String):String?{
        return try {
            // id,pw를 담아서 LoginParam Entity를 만듦
            val param = Json.encodeToString(LoginParam(id, pw)) // Entity를 직렬화
            service.login(param.toRequestBody()).data // 직렬화한 Entity를 requestBody로 만들어서 통신
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }
}