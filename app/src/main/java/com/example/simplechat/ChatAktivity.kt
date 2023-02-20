package com.example.simplechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplechat.databinding.ActivityChatAktivityBinding

class ChatAktivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatAktivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatAktivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}