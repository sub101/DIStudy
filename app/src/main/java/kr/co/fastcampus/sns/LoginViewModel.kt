package kr.co.fastcampus.sns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @author soohwan.ok
 */
class LoginViewModel constructor(
    private val repository: UserDataRepository,
):ViewModel(){

    private val _uiState = MutableStateFlow(
        LoginUiState(id = "", pw = "")
    )
    val uiState = _uiState.asStateFlow()

    init {
        // ViewModel이 초기화 되자마자 repository로부터 로그인이 외었는지 확인
        viewModelScope.launch {
            if(repository.isLoggedIn()){
                val isLoggedInBefore = repository.isLoggedIn()
                if(isLoggedInBefore){
                    // 로그인이 되었다면 user 상태를 변경 - Activity가 collcet
                    _uiState.update { it.copy(userState = UserState.LOGGED_IN) }
                }
            }
        }
    }

    // 로그인 버튼을 눌렀을 때 타는 메소드
    fun login(){
        viewModelScope.launch(Dispatchers.IO) {
            val isLoggedIn = repository.login(
                _uiState.value.id,
                _uiState.value.pw
            )
            val token = repository.getCurrentToken()
            _uiState.update {
                it.copy(userState = if(isLoggedIn) UserState.LOGGED_IN else UserState.FAILED)
            }
        }
    }

    // id가 textField에 입력되었을 때 타는 메소드 - 상태를 변경
    fun onIdChange(value: String) {
        // LoginUiState로 상태 관리
        _uiState.update { it.copy(id = value) }

    }

    // pw가 textField에 입력되었을 때 타는 메소드 - 상태를 변경
    fun onPwChange(value: String) {
        _uiState.update { it.copy(pw = value) }
    }

}