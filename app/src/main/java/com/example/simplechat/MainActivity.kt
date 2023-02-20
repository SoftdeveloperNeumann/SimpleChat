package com.example.simplechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToChat.setOnClickListener {view ->
            val intent = Intent(this,ChatAktivity::class.java).apply {
                putExtra("user", binding.etUser.text.toString())
                putExtra("other",binding.etOther.text.toString())
            }
            startActivity(intent)
        }
    }
}