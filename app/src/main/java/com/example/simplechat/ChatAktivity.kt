package com.example.simplechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.simplechat.databinding.ActivityChatAktivityBinding
import com.google.firebase.database.*

class ChatAktivity : AppCompatActivity() {

    companion object{
        lateinit var  userName: String
        lateinit var  chatPartner: String
    }

    private lateinit var binding: ActivityChatAktivityBinding

    private lateinit var db: FirebaseDatabase
    lateinit var reference1: DatabaseReference
    lateinit var reference2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatAktivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("user")?:"unbekannt"
        chatPartner = intent.getStringExtra("other")?:"unbekannt"

        db = FirebaseDatabase.getInstance()

        reference1 = db.getReference("messages/${userName}_${chatPartner}")
        reference2 = db.getReference("messages/${chatPartner}_${userName}")

        binding.messageArea.btnSend.setOnClickListener {
            val messageText = binding.messageArea.etMessage.text.toString()
            if(messageText.isNotBlank()){
                val map = hashMapOf(
                    "message" to messageText,
                    "user" to userName
                )

                reference1.push().setValue(map)
                reference2.push().setValue(map)
                binding.messageArea.etMessage.text.clear()
            }
        }

        reference1.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val map = snapshot.value as HashMap<String,String>
                val user = map["user"]
                val message = map["message"]

                if(user == userName){
                    addMessage("Du: \n$message", 1)
                }else{
                    addMessage("$chatPartner: \n$message",2)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun addMessage(message: String, typ: Int) {
        TODO("Not yet implemented")
    }
}