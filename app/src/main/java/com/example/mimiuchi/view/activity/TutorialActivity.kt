package com.example.mimiuchi.view.activity

import android.app.Activity
import android.os.Bundle
import com.example.mimiuchi.R
import com.stephentuso.welcome.*

class TutorialActivity : WelcomeActivity() /*AppCompatActivity()*/ {

    companion object {
        /**
         * まだ表示していなかったらチュートリアルを表示
         * SharedPreferencesの管理に関しては内部でよしなにやってくれているので普通に呼ぶだけで良い
         */
        fun showIfNeeded(activity: Activity, savedInstanceState: Bundle?) {
            WelcomeHelper(activity, TutorialActivity::class.java).show(savedInstanceState)
        }

        /**
         * 強制的にチュートリアルを表示したい時にはこちらを呼ぶ
         */
        fun showForcibly(activity: Activity) {
            WelcomeHelper(activity, TutorialActivity::class.java).forceShow()
        }
    }

    /**
     * 表示するチュートリアル画面を定義する
     */
    override fun configuration(): WelcomeConfiguration {
        return WelcomeConfiguration.Builder(this)
            .defaultBackgroundColor(R.color.tutorialColor)
            .bottomLayout(WelcomeConfiguration.BottomLayout.STANDARD_DONE_IMAGE)
            .page(FullscreenParallaxPage(R.layout.tutorial_1))
            .page(FullscreenParallaxPage(R.layout.tutorial_2))
            .page(FullscreenParallaxPage(R.layout.tutorial_3))
            .swipeToDismiss(false)
            .build()
    }

}
