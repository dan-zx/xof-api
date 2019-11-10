package com.github.danzx.xof.core.domain

const val AUTO_GENERATED_ID = -1L

fun Long?.orAutoGeneratedId() = if (this == AUTO_GENERATED_ID) null else this
