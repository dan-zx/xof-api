package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostVotePersister
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class VoteOnPostUseCaseTest {

    @RelaxedMockK lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase
    @RelaxedMockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @RelaxedMockK lateinit var postVotePersister : PostVotePersister
    @InjectMockKs lateinit var useCase: VoteOnPostUseCase

    @Test
    fun `should save or update the post votes when no exceptions happen`() {
        val command = Vote(
            entityId = 1,
            userId = 2,
            direction = Vote.Direction.UP
        )
        useCase(command)

        verify { validatePostIdExistsUseCase(command.entityId) }
        verify { validateUserIdExistsUseCase(command.userId) }
        verify { postVotePersister.saveOrUpdate(command) }
    }
}