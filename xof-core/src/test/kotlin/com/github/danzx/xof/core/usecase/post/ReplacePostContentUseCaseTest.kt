package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.test.constants.TEST_POST
import com.github.danzx.xof.core.usecase.post.command.ReplacePostContentCommand

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

import org.junit.jupiter.api.Test

class ReplacePostContentUseCaseTest : ReplacePostDataUseCaseBaseTest<ReplacePostContentUseCase>() {

    @Test
    fun `should replace post content when no exceptions happen`() {
        val command = ReplacePostContentCommand(id = TEST_POST.id, value = "NewContent")
        val actual = useCase(command)

        actual should {
            it.id shouldBe TEST_POST.id
            it.title shouldBe TEST_POST.title
            it.created shouldBe TEST_POST.created
            it.user shouldBe TEST_POST.user
            it.votes shouldBe TEST_POST.votes
            it.content shouldBe command.value
            it.updated shouldNotBe TEST_POST.updated
        }
    }
}