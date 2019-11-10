package com.github.danzx.xof.core.domain

data class Vote(
    var entityId: Long,
    var userId: Long,
    var direction: Direction) {

    enum class Direction(val value: Int) {
        DOWN(-1),
        ZERO(0),
        UP(+1)
    }
}