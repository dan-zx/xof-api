package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.test.constants.TEST_POST
import com.github.danzx.xof.core.usecase.post.command.ReplacePostTitleCommand

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

import org.junit.jupiter.api.Test

class ReplacePostTitleUseCaseTest : ReplacePostDataUseCaseBaseTest<ReplacePostTitleUseCase>() {

    @Test
    fun `should replace post title when no exceptions happen`() {
        val command = ReplacePostTitleCommand(id = TEST_POST.id, value = "NewTitle")
        val actual = useCase(command)

        actual should {
            it.id shouldBe TEST_POST.id
            it.content shouldBe TEST_POST.content
            it.created shouldBe TEST_POST.created
            it.user shouldBe TEST_POST.user
            it.votes shouldBe TEST_POST.votes
            it.title shouldBe command.value
            it.updated shouldNotBe TEST_POST.updated
        }
    }
}