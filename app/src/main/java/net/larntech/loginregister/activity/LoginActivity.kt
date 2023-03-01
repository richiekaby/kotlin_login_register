package net.larntech.loginregister.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import net.larntech.loginregister.MainActivity
import net.larntech.loginregister.R
import net.larntech.loginregister.network.model.login.LoginResponse
import net.larntech.loginregister.network.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var edUsername: TextInputEditText
    private lateinit var edPassword: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViews()
        handleClicks()
    }

    private fun findViews(){
        edUsername = findViewById(R.id.ed_username)
        edPassword = findViewById(R.id.ed_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun handleClicks(){
        btnLogin.setOnClickListener {
            handleLogin()
        }

        btnRegister.setOnClickListener {
            handleRegister()
        }
    }

    private fun handleLogin(){
        if(validInputs()){
            loginUser()
        }else{
            showMessage("All inputs required")
        }
    }

    private fun handleRegister(){
       //to do when we create register activity
        startActivity(Intent(this,RegisterActivity::class.java))
        finish()
    }

    //check username and password
    private fun validInputs(): Boolean{
        return edUsername.text.toString().isNotEmpty() && edPassword.text.toString().isNotEmpty()
    }

    private fun loginUser(){
        val userName = edUsername.text.toString()
        val password = edPassword.text.toString()
        handleProgressBar(true)

        val apiClient = ApiClient.apiService

        apiClient.loginUser(userName,password).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                handleProgressBar(false)

                if(response.isSuccessful){
                    val loginResponse = response.body()
                    if(loginResponse != null && loginResponse.status) {
                        showMessage("Login Successful")
                        handleSuccessLogin(loginResponse)
                    }else{
                        showMessage(loginResponse!!.message)
                    }
                }else{
                    showMessage("Unable to login, Please try again later ...")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                handleProgressBar(true)

                showMessage("We are unable to complete your request please try again later ...")
            }

        })

    }

    private fun handleSuccessLogin(loginResponse: LoginResponse){
        startActivity(Intent(this,MainActivity::class.java).putExtra("data",loginResponse))
        finish()
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