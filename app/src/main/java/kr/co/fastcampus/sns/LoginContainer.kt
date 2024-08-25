package kr.co.fastcampus.sns

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * LoginActivity에서 AppContainer가 아닌, LoginContainer를 참조하도록 수정함.
 * LoginContainer가 있어야할 위치: AppContainer
 */
class LoginContainer(private val appContainer: AppContainer) {

    // LoginContainer에서 UserLocalDataSource, UserRemoteDataSource를 참조하기 위해 AppContainer로 통로를 열어줌
    private val userDataRepository = UserDataRepository(
        localDataSource = appContainer.createUserLocalDataSource(),
        remoteDataSource = appContainer.createUserRemoteDataSource()
    )
    fun createLoginViewModelFactory(): AbstractSavedStateViewModelFactory {
        return object : AbstractSavedStateViewModelFactory() {
            override fun <T : ViewModel> create(
                key: String, modelClass: Class<T>, handle: SavedStateHandle
            ): T {
                return LoginViewModel(userDataRepository) as T
            }
        }
    }

}
