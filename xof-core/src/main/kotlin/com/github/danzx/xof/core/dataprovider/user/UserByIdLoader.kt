package com.github.danzx.xof.core.dataprovider.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.dataprovider.DataByIdLoader

interface UserByIdLoader : DataByIdLoader<Long, User>
