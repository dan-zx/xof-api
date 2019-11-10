package com.github.danzx.xof.core.repository.user

interface UserUsernameChecker {
    fun existsUsername(username: String) : Boolean
}
