package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.domain.Ids

fun Long.orAutoGeneratedId() = if (this == Ids.AUTO_GENERATED) null else this