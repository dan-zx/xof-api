package com.github.danzx.xof.core.dataprovider.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.dataprovider.DataByIdLoader

interface PostByIdLoader : DataByIdLoader<Long, Post>
