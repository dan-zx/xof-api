package com.github.danzx.xof.core.dataprovider.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.dataprovider.PaginatedDataLoader
import com.github.danzx.xof.core.filter.PostsFilter

interface PaginatedPostsLoader : PaginatedDataLoader<PostsFilter, Post>
