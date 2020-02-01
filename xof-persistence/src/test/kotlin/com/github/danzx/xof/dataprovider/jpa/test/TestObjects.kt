package com.github.danzx.xof.dataprovider.jpa.test

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.domain.User

import java.time.LocalDateTime

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
    votes = 1,
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
    parentId = null,
    votes = -1
)
