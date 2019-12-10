package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.usecase.post.command.ReplacePostTitleCommand

import io.kotlintest.matchers.date.between
import io.kotlintest.should
import io.kotlintest.shouldBe

import org.junit.jupiter.api.Test

import ru.yole.kxdate.ago
import ru.yole.kxdate.milliseconds

import java.time.LocalDateTime.now

class ReplacePostTitleUseCaseTest : ReplacePostDataUseCaseBaseTest<ReplacePostTitleUseCase>() {

    @Test
    fun `should replace post title when no exceptions happen`() {
        val command = ReplacePostTitleCommand(id = testPost.id, value = "NewTitle")
        val actual = useCase(command)

        actual should {
            it.id shouldBe testPost.id
            it.content shouldBe testPost.content
            it.created shouldBe testPost.created
            it.user shouldBe testPost.user
            it.votes shouldBe testPost.votes
            it.title shouldBe command.value
            it.updated shouldBe between(500.milliseconds.ago, now())
        }
    }
}