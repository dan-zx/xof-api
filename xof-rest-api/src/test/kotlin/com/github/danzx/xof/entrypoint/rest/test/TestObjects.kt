package com.github.danzx.xof.entrypoint.rest.test

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse

import org.springframework.http.HttpStatus

import java.time.LocalDateTime
import java.time.LocalDateTime.now

val TEST_USER = User(
    id = 56,
    name = "Test user",
    lastName = "Users Last Name",
    username = "Users Username",
    avatarImageUrl = "http://userimage.jpg",
    join = LocalDateTime.of(2019, 12, 6, 12, 0, 0)
)

val TEST_POST = Post(
    id = 85,
    title = "Test Title",
    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    created = LocalDateTime.of(2019, 12, 6, 12, 0, 0),
    updated = LocalDateTime.of(2019, 12, 6, 12, 0, 0),
    votes = 5,
    user = SimpleUser(
        id = TEST_USER.id,
        username = TEST_USER.username
    )
)

val TEST_COMMENT = Comment(
    id = 102,
    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    created = LocalDateTime.of(2019, 12, 6, 12, 0, 0),
    updated = LocalDateTime.of(2019, 12, 6, 12, 0, 0),
    user = SimpleUser(
        id = TEST_USER.id,
        username = TEST_USER.username
    ),
    postId = TEST_POST.id,
    parentId = 3,
    votes = -2
)

val BAD_REQUEST_ERROR = ErrorResponse(
    error = HttpStatus.BAD_REQUEST.reasonPhrase,
    status = HttpStatus.BAD_REQUEST.value(),
    message = "",
    path = "",
    timestamp = now()
)

val VALIDATION_ERROR = BAD_REQUEST_ERROR.copy(message = "Validation failed")

val NOT_FOUND_ERROR = ErrorResponse(
    error = HttpStatus.NOT_FOUND.reasonPhrase,
    status = HttpStatus.NOT_FOUND.value(),
    message = "",
    path = "",
    timestamp = now()
)

val USERNAME_ALREADY_EXISTS_ERROR = ErrorResponse(
    error = HttpStatus.CONFLICT.reasonPhrase,
    status = HttpStatus.CONFLICT.value(),
    message = "Username already exists",
    path = "",
    timestamp = now()
)