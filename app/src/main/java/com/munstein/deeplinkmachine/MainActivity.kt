package com.munstein.deeplinkmachine

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.munstein.deeplinkmachine.ui.theme.BackgroundGrey
import com.munstein.deeplinkmachine.ui.theme.DeeplinkMachineTheme
import com.munstein.deeplinkmachine.ui.theme.DroidGreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeeplinkMachineTheme {
                MyAppScreen(message = stringResource(R.string.app_name))
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            checkForDeeplink(intent)
        }
    }

    private fun checkForDeeplink(intent: Intent){
        if(intent.dataString == DEEPLINK_DEMO_CUSTOM_SCHEME){
            showToast(R.string.toast_msg_deeplink_custom)
        }

        if(intent.dataString == DEEPLINK_DEMO_APP_LINK){
            showToast(R.string.toast_msg_deeplink_app_link)
        }

        if(intent.dataString?.contains(DEEPLINK_DEMO_WITH_PATH_PARAMETER) == true) {
            val data = intent.data
            val parameter = data?.lastPathSegment
            parameter?.let {
                showToast(R.string.toast_msg_deeplink_parameter, parameter)
            }
        }
    }

    private fun showToast(messageResId: Int, vararg formatArgs: Any) {
        Toast.makeText(this, getString(messageResId, *formatArgs), Toast.LENGTH_SHORT).show()
    }

    private companion object {
        const val DEEPLINK_DEMO_CUSTOM_SCHEME = "munsteinapp://open_app"
        const val DEEPLINK_DEMO_APP_LINK = "https://www.munsteinapp.com/app/open"
        const val DEEPLINK_DEMO_WITH_PATH_PARAMETER = "https://www.munsteinapp.com/app/byPathPattern"
    }
}

@Composable
fun MyAppScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGrey),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DroidGreen),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DeeplinkMachineTheme {
        MyAppScreen(message = stringResource(R.string.app_name))
    }
}