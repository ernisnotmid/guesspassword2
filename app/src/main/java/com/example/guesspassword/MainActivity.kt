package com.example.guesspassword

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextPassword: EditText = findViewById(R.id.editTextPassword)
        val buttonGuess: Button = findViewById(R.id.buttonGuess)
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val buttonShowPassword: Button = findViewById(R.id.buttonShowPassword)
        val textViewPassword: TextView = findViewById(R.id.textViewPassword)
        val buttonNewPassword: Button = findViewById(R.id.buttonNewPassword)
        val textViewHint: TextView = findViewById(R.id.textViewHint)
        val textView: TextView = findViewById(R.id.textView)

        editTextPassword.visibility = View.INVISIBLE
        buttonGuess.visibility = View.INVISIBLE
        textViewResult.visibility = View.INVISIBLE
        buttonShowPassword.visibility = View.INVISIBLE
        textViewPassword.visibility = View.INVISIBLE
        textViewHint.visibility = View.INVISIBLE
        textView.visibility = View.INVISIBLE

        val chooseLength: Button = findViewById(R.id.chooseLengthButton)
        val editLength: EditText = findViewById(R.id.editLength)
        val textLengthPass: TextView = findViewById(R.id.textLengthPassword)
        chooseLength.setOnClickListener(){
            val passwordLength = editLength.text.toString().toInt()
            chooseLength.visibility = View.INVISIBLE
            editLength.visibility = View.INVISIBLE
            textLengthPass.visibility = View.INVISIBLE
            textView.text = "Пароль состоит из $passwordLength символов"


            editTextPassword.visibility = View.VISIBLE
            buttonGuess.visibility = View.VISIBLE
            textViewResult.visibility = View.VISIBLE
            buttonShowPassword.visibility = View.GONE
            textViewPassword.visibility = View.VISIBLE
            textViewHint.visibility = View.VISIBLE
            textView.visibility = View.VISIBLE


            game = Game(passwordLength)
            buttonGuess.setOnClickListener {
                val userGuess = editTextPassword.text.toString()

                if (game.guess(userGuess)) {
                    textViewResult.text = "Поздравляем! Вы угадали пароль с ${game.attempts} попытки."
                    buttonShowPassword.visibility = Button.GONE
                    buttonNewPassword.visibility = Button.VISIBLE
                    textViewHint.text = ""
                } else {
                    textViewResult.text = "Неверный пароль. Попробуйте еще раз."
                    textViewHint.text = game.getHint(userGuess)
                    if (game.attempts >= 3) {
                        buttonShowPassword.visibility = Button.VISIBLE
                    }
                }
            }

            buttonShowPassword.setOnClickListener {
                textViewPassword.text = "Загаданный пароль: ${game.password}"
                buttonNewPassword.visibility = Button.VISIBLE
            }

            buttonNewPassword.setOnClickListener {
                game.reset()
                editTextPassword.text.clear()
                textViewResult.text = ""
                textViewPassword.text = ""
                textViewHint.text = ""
                textViewResult.text = "Пароль сброшен. Начните угадывать новый пароль."
                buttonNewPassword.visibility = Button.GONE
                buttonShowPassword.visibility = Button.GONE
            }
        }

    }
}
