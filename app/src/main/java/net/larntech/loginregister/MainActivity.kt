package net.larntech.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.larntech.loginregister.activity.LoginActivity
import net.larntech.loginregister.network.model.login.LoginResponse

class MainActivity : AppCompatActivity() {

    private lateinit var tvWelcome: TextView
    private lateinit var btnLogOut: Button
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
    }

    private fun findViews(){
        tvWelcome = findViewById(R.id.tvUsername)
        btnLogOut = findViewById(R.id.btnLogout)
        handleClick()
        getData()
    }

    private fun handleClick(){
        btnLogOut.setOnClickListener {
            returnToMain()
        }
    }

    private fun returnToMain(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun getData(){
        intent = getIntent()
        var loginResponse = intent.getSerializableExtra("data") as LoginResponse
        tvWelcome.text = "Welcome ${loginResponse.username}"
    }
}