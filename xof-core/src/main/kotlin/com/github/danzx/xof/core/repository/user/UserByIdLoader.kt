package com.github.danzx.xof.core.repository.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.repository.DataByIdLoader

interface UserByIdLoader : DataByIdLoader<Long, User>
