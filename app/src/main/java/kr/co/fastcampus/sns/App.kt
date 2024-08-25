package kr.co.fastcampus.sns

import android.app.Application

/**
 * Application 클래스가 AppContainer를 관리함
 *
 * [Application에 AppContainer를 두는 이유]
 * : Android Component (Activity/Fragment/Service)들이 손쉽게 접근 가능한 가장 일반적인 경로
 *
 *
 */

// Application 상속 - Manifest에 선언 해야함 (android:name=".App")
class App : Application() {
    val appContainer:AppContainer = AppContainer(context = this)
}