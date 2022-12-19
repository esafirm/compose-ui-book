package nolambda.uibook.browser.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import nolambda.uibook.browser.config.AppBrowserConfig
import nolambda.uibook.components.bookform.GlobalState
import nolambda.uibook.components.booklist.BookList
import nolambda.uibook.setting.SettingPage

class UIBookSettingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SettingPage()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, UIBookSettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}
