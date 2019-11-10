package com.github.danzx.xof.core.repository.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.repository.DataByIdLoader

interface CommentByIdLoader : DataByIdLoader<Long, Comment>
