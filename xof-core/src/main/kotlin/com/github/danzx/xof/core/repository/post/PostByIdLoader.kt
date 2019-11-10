package com.github.danzx.xof.core.repository.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.repository.DataByIdLoader

interface PostByIdLoader : DataByIdLoader<Long, Post>
