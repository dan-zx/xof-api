package com.github.danzx.xof.core.domain

import java.time.LocalDateTime

data class User(
    var id: Long,
    var name: String,
    var lastName: String,
    var username: String,
    var avatarImageUrl: String,
    var join: LocalDateTime)
