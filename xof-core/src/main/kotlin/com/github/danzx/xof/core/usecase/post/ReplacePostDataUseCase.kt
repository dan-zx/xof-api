package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostUpdater
import com.github.danzx.xof.core.domain.Post

import java.time.LocalDateTime.now

import javax.inject.Inject

abstract class ReplacePostDataUseCase {

    @Inject lateinit var getPostByIdUseCase: GetPostByIdUseCase
    @Inject lateinit var updater: PostUpdater

    protected fun changePostDataWithId(id: Long, dataChanges: Post.() -> Unit) : Post {
        val post = getPostByIdUseCase(id)
        post.dataChanges()
        post.updated = now()
        updater.update(post)
        return post
    }
}