package com.github.danzx.xof.core.repository.user

import com.github.danzx.xof.core.domain.User

interface UserByUsernameLoader {
    fun loadByUsername(username: String) : User?
}
