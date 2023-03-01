package net.larntech.loginregister.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import net.larntech.loginregister.R
import net.larntech.loginregister.network.model.register.RegisterResponse
import net.larntech.loginregister.network.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginBtn: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViews()
    }

    private fun findViews(){
        userEmail = findViewById(R.id.edEmail)
        userPassword = findViewById(R.id.edPassword)
        registerBtn = findViewById(R.id.btnRegister)
        loginBtn = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBar)
        handleClicks()
    }

    private fun handleClicks(){
        registerBtn.setOnClickListener {
            if(validateInputs()) {
                handleRegister()
            }else{
                showMessage("Email and Password required ...")
            }
        }

        loginBtn.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun validateInputs():Boolean{
        return userEmail.text.toString().isNotEmpty() && userPassword.text.toString().isNotEmpty()
    }

    private fun handleRegister(){
        var username = userEmail.text.toString()
        var password = userPassword.text.toString()
        handleProgressBar(true)

        var apiClient = ApiClient.apiService
        apiClient.registerUser(username,password).enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                handleProgressBar(false)
                if(response.isSuccessful){
                    var registerResponse = response.body()
                    if(registerResponse!!.status){
                        showMessage(registerResponse.message)
                        handleLogin()
                    }else{
                        showMessage(registerResponse.message)
                    }
                }else{
                    showMessage("Unable to process your request, Try again later ")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                handleProgressBar(false)
                showMessage("An error occurred "+t.localizedMessage)
            }

        })

    }



    private fun showMessage(message: String){
        Toast.makeText(this, message,Toast.LENGTH_LONG).show()
    }

    private fun handleProgressBar(show: Boolean){
        if(show) {
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE
        }
    }


}