package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostByIdLoader
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

class GetPostByIdUseCase : UseCase<Long, Post> {

    @Inject lateinit var loader : PostByIdLoader

    override operator fun invoke(id: Long) = loader.loadById(id) ?: throw PostNotFoundException()
}