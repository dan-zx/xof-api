package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.usecase.post.command.ReplacePostContentCommand

import io.kotlintest.matchers.date.between
import io.kotlintest.should
import io.kotlintest.shouldBe

import org.junit.jupiter.api.Test

import ru.yole.kxdate.ago
import ru.yole.kxdate.milliseconds

import java.time.LocalDateTime.now

class ReplacePostContentUseCaseTest : ReplacePostDataUseCaseBaseTest<ReplacePostContentUseCase>() {

    @Test
    fun `should replace post content when no exceptions happen`() {
        val command = ReplacePostContentCommand(id = testPost.id, value = "NewContent")
        val actual = useCase(command)

        actual should {
            it.id shouldBe testPost.id
            it.title shouldBe testPost.title
            it.created shouldBe testPost.created
            it.user shouldBe testPost.user
            it.votes shouldBe testPost.votes
            it.content shouldBe command.value
            it.updated shouldBe between(500.milliseconds.ago, now())
        }
    }
}