package com.example.musicbrainz.presentation.screens.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.musicbrainz.databinding.ActivityMainBinding
import com.example.musicbrainz.framework.util.extensions.makeStatusBarDark

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        makeStatusBarDark()
    }
}