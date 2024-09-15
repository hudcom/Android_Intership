package com.project.android_intership.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.project.android_intership.R
import com.project.android_intership.databinding.ActivityMainBinding
import com.project.android_intership.ui.view.fragment.NewsFeedFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideSystemUI()
        setupBinding()
        setupToolbar()

        // Завантаження фрагменту
        if (savedInstanceState == null) {
            val fragment = NewsFeedFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.news_feed_fragment, fragment)
                .commit()
        }
    }

    // Налаштування binding
    private fun setupBinding(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Налаштування toolbar
    private fun setupToolbar(){
        setSupportActionBar(binding.toolbar)
        // Встановлюємо заголовок
        supportActionBar?.title = getString(R.string.app_name)
    }

    // Приховати системний UI
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }
}