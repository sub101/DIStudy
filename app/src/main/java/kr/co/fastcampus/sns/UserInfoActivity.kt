package kr.co.fastcampus.sns

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.co.fastcampus.sns.ui.theme.FastcampusSNSTheme


/**
 * @author soohwan.ok
 */
class UserInfoActivity : ComponentActivity() {

    // userLocalDataSource, Lazy 패턴으로 Container를 통해 가져오기
    private val userLocalDataSource by lazy{ (application as App).appContainer.createUserLocalDataSource()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FastcampusSNSTheme {
                Surface {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            var token by remember { mutableStateOf("") } // 토큰 내용 노출
                            LaunchedEffect(Unit){
                                // localDataSouce에서 토큰 내용을 가져와서 토큰을 text composable로 보여줌
                                launch {
                                    token = userLocalDataSource.getToken().orEmpty()
                                }
                            }
                            Text(text = "$token")
                            Spacer(modifier = Modifier.height(20.dp))
                            TextButton(onClick = {
                                lifecycleScope.launch {
                                    userLocalDataSource.clear()
                                    startActivity(Intent(this@UserInfoActivity, LoginActivity::class.java))
                                    finish()
                                }
                            }) {
                                Text(text = "Logout")
                            }
                        }

                    }
                }
            }
        }
    }
}