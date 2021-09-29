package com.example.hw_1

import androidx.annotation.VisibleForTesting

object UserHolder {

    private val map = mutableMapOf<String, User>()
    private val phoneFormat = Regex("""^[+][\d]{11}""")

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): User = User.makeUser(fullName, email = email, password = password)
        .also { user ->
            if (map.containsKey(user.login)) throw IllegalArgumentException("A user with this email already exists")
            else map[user.login] = user
        }

    fun loginUser(login: String, password: String): String? {
        val phoneLogin = cleanPhone(login)
        return if (phoneLogin.isNotEmpty()) {
            map[phoneLogin]
        } else {
            map[login.trim()]
        }?.let {
            if (it.checkPassword(password)) it.userInfo
            else null
        }
    }

    fun registerUserByPhone(fullName: String, rawPhone: String): User =
        User.makeUser(fullName, phone = rawPhone)
            .also { user ->
                if (map.containsKey(user.phone)) throw IllegalArgumentException("This number is used")
                if (cleanPhone(rawPhone).matches(phoneFormat)) map[user.login] = user
                else throw IllegalArgumentException("Number entered incorrectly")
            }

    fun requestAccessCode(login: String) {
        val phone: String = cleanPhone(login)
        map[phone]?.let {
            val code: String = it.generateAccessCode()
            it.passwordHash = it.encrypt(code)
            it.accessCode = code
            it.sendAccessCodeToUser(phone, code)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }

    private fun cleanPhone(phone: String): String {
        return phone.replace("""[^+\d]""".toRegex(), "")
    }
}