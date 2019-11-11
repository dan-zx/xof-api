package com.github.danzx.xof.core.dataprovider.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.dataprovider.DataByIdLoader

interface CommentByIdLoader : DataByIdLoader<Long, Comment>
