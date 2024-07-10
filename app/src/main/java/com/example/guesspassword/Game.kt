package com.example.guesspassword

import kotlin.random.Random

class Game(public val passwordLength: Int) {
    var password: String = generateRandomPassword(passwordLength)
        private set
    var attempts: Int = 0
        private set

    fun guess(input: String): Boolean {
        attempts++
        return input == password
    }

    fun getHint(input: String): String {
        val correctPositions = getCorrectPositions(input)
        val correctDigits = getCorrectDigits(input)
        return "Правильные цифры на правильных местах: $correctPositions, правильные цифры не на своих местах: $correctDigits"
    }

    fun reset() {
        password = generateRandomPassword(passwordLength)
        attempts = 0
    }

    private fun generateRandomPassword(length: Int): String {
        val digits = "0123456789"
        return (1..length)
            .map { digits[Random.nextInt(digits.length)] }
            .joinToString("")
    }

    private fun getCorrectPositions(input: String): Int {
        var count = 0
        for (i in input.indices) {
            if (input[i] == password[i]) {
                count++
            }
        }
        return count
    }

    private fun getCorrectDigits(input: String): Int {
        val passwordChars = password.toCharArray().toMutableList()
        val inputChars = input.toCharArray().toMutableList()
        //  правильные цифры на правильных местах
        for (i in input.indices.reversed()) {
            if (input[i] == password[i]) {
                passwordChars.removeAt(i)
                inputChars.removeAt(i)
            }
        }
        var count = 0
        //  оставшиеся правильные цифры
        for (ch in inputChars) {
            if (passwordChars.contains(ch)) {
                passwordChars.remove(ch)
                count++
            }
        }
        return count
    }
}
