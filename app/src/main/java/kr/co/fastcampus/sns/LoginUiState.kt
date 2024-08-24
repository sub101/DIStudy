package kr.co.fastcampus.sns

/**
 * @author soohwan.ok
 * id, pw, 현재 로그인이 어떤 상태인지 갖는 enum class
 */
data class LoginUiState(
    val id:String,
    val pw:String,
    val userState:UserState = UserState.NONE
)