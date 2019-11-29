package com.github.danzx.xof.dataprovider.jpa.repository

fun CommentVoteJpaRepository.countVotesByCommentId(commentId: Long) =
    countByCommentIdGroupingByDirection(commentId)
        .fold(0L, {sum, view -> sum + (view.count * view.direction.value)})

fun PostVoteJpaRepository.countVotesByPostId(postId: Long) =
    countByPostIdGroupingByDirection(postId)
        .fold(0L, {sum, view -> sum + (view.count * view.direction.value)})
