package kr.co.fastcampus.sns

/**
 * @author soohwan.ok
 */
enum class UserState {
    NONE, // 아무것도 안했을 때
    FAILED, // 로그인 실패
    LOGGED_IN, // 로그인 성공
}