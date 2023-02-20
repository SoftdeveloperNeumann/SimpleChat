package com.example.simplechat

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var userName: String
        lateinit var chatPartner: String
    }

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

            userName = binding.etUser.text.toString()
            chatPartner = binding.etOther.text.toString()

            val serviceIntent = Intent(this, NotificationService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            }

            startActivity(intent)
        }
    }
}