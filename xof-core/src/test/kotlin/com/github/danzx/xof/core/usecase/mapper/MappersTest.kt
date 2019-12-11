package com.github.danzx.xof.core.usecase.mapper

import com.github.danzx.xof.core.domain.Ids
import com.github.danzx.xof.core.domain.Usernames
import com.github.danzx.xof.core.test.constants.TEST_USER
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand

import io.kotlintest.matchers.date.beInToday
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.matchers.numerics.shouldBeZero

class MappersTest : StringSpec({

    "CreateNewCommentCommand.toComment() should return a Comment with the data from the command" {
        val command = CreateNewCommentCommand(
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            userId = 1,
            postId = 2,
            parentId = 3
        )

        command.toComment() should {
            it.id shouldBe Ids.AUTO_GENERATED
            it.user.username shouldBe Usernames.NOT_AVAILABLE
            it.votes.shouldBeZero()
            it.created should beInToday()
            it.updated should beInToday()
            it.content shouldBe command.content
            it.user.id shouldBe command.userId
            it.postId shouldBe command.postId
            it.parentId shouldBe command.parentId
        }
    }

    "CreateNewPostCommand.toPost() should return a Comment with the data from the command" {
        val command = CreateNewPostCommand(
            title = "Title",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            userId = 1
        )

        command.toPost() should {
            it.id shouldBe Ids.AUTO_GENERATED
            it.user.username shouldBe Usernames.NOT_AVAILABLE
            it.votes.shouldBeZero()
            it.created should beInToday()
            it.updated should beInToday()
            it.title shouldBe command.title
            it.content shouldBe command.content
            it.user.id shouldBe command.userId
        }
    }

    "CreateNewUserCommand.toUser() should return a Comment with the data from the command" {
        val command = CreateNewUserCommand(
            name = "Test user",
            lastName = "Users Last Name",
            username = "Users Username",
            avatarImageUrl = "http://userimage.jpg"
        )

        command.toUser() should {
            it.id shouldBe Ids.AUTO_GENERATED
            it.name shouldBe command.name
            it.lastName shouldBe command.lastName
            it.avatarImageUrl shouldBe command.avatarImageUrl
            it.join should beInToday()
        }
    }

    "ReplaceUserCommand.copyTo(User) should return the same user but with the data from the command" {
        val command = ReplaceUserCommand(
            id = TEST_USER.id,
            name = "User",
            lastName = "UserLastName",
            username = "New User Username",
            avatarImageUrl = "http://userimage.jpg"
        )
        val user = TEST_USER.copy()

        command copyTo user

        user should {
            it.id = TEST_USER.id
            it.join = TEST_USER.join
            it.name shouldBe command.name
            it.lastName shouldBe command.lastName
            it.username shouldBe command.username
            it.avatarImageUrl shouldBe command.avatarImageUrl
        }
    }
})