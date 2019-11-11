package com.github.danzx.xof.core.dataprovider.user

interface UserUsernameChecker {
    fun existsUsername(username: String) : Boolean
}
