package kr.co.fastcampus.sns

import android.content.Context
import androidx.activity.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * 1. LoginActivity에 정의했던 의존성 여기로 옮김
 * 2. 의존성을 제공하기 위한 메소드 생성
 *    -> 이렇게 함으로써 LoginActivity는 ViewModel만 의존하는 양상을 만들어낼 수 있음
 *
 * (Container를 어디에 위치시킬 것인가?)
 * : Application 클래스에 위치를 시킴
 *
 * <AppContainer로 의존성 관리시 문제>
 * - Application scope 내에 의존성들이 전부 바인딩 된다면,
 *   코드의 복잡도 증가, 싱글톤 형식으로 메모리상 의존성들이 계속 로드되면서 문제가 생길 수 있음.
 *   따라서, Activity/Fragment/Service 등 view 단위로 Container를 분리시켜주는 것이 중요.
 */
class AppContainer constructor(private val context: Context) {

    // Retrofit을 반환
    fun createRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/api/")
            .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

    fun createLoginRetrofitService(): LoginRetrofitService{
        return createRetrofit().create(LoginRetrofitService::class.java)
    }

    fun createUserLocalDataSource():UserLocalDataSource{
        return UserLocalDataSource(context)
    }

    fun createUserRemoteDataSource():UserRemoteDataSource{
        return UserRemoteDataSource(createLoginRetrofitService())
    }

    fun createUserDataRepository():UserDataRepository{
        return UserDataRepository(
            localDataSource = createUserLocalDataSource(),
            remoteDataSource = createUserRemoteDataSource()
        )
    }

    // viewModel은 AbstractSavedStateViewModelFactory라는 ViewModelFactory 사용
    fun createLoginViewModelFactory():AbstractSavedStateViewModelFactory{
        return object : AbstractSavedStateViewModelFactory() {
            // LoginViewModel을 만들 때 createUserDataRepository을 매번 호출하여 새로 생성하는 대신,
            // 하나의 userDataRepository를 공유하고자 함
            // 적어도 이 스코프 내에서는 UserDataRepository를 공유 가능
            val repository = createUserDataRepository()
            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle
            ): T {
                return LoginViewModel(repository) as T
            }
        }
    }

}
