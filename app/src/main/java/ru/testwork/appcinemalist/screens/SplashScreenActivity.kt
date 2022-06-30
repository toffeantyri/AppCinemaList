package ru.testwork.appcinemalist.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.testwork.appcinemalist.R
import ru.testwork.appcinemalist.util.log

@ExperimentalCoroutinesApi
@FlowPreview
class SplashScreenActivity : AppCompatActivity() {

    @BindView(R.id.iv_splash_logo)
    lateinit var imageLogo: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ButterKnife.bind(this)

        log(this::class.java.simpleName + " onCreate")
        imageLogo.apply {
            scaleX = 0f
            scaleY = 0f
        }
        imageLogo.animate().setDuration(1000).scaleX(1.3f).scaleY(1.3f).withEndAction {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
