package kr.co.fastcampus.sns

/**
 * @author soohwan.ok
 */
class UserDataRepository constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
) {
    suspend fun login(id: String, pw: String): Boolean {
        if(isLoggedIn()){
            return true
        }
        val token = remoteDataSource.login(id, pw) ?: return false
        localDataSource.setToken(token)
        return true
    }

    suspend fun isLoggedIn():Boolean{
        // localDataSource로 확인해서 실제로 로그인이 되었는지 확인
        return !localDataSource.getToken().isNullOrEmpty()
    }

    // 현재 토큰을 확인하는 메소드
    suspend fun getCurrentToken():String?{
        return localDataSource.getToken()
    }

    suspend fun logout(){
        localDataSource.clear()
    }

}