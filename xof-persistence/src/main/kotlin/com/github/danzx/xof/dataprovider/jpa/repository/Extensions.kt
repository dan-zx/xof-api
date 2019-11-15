package com.github.danzx.xof.dataprovider.jpa.repository

fun CommentVoteJpaRepository.countVotesByCommentId(commentId: Long) =
    countByCommentIdGroupingByDirection(commentId)
        .asSequence()
        .map { it.count * it.direction.value }
        .sum()

fun PostVoteJpaRepository.countVotesByPostId(postId: Long) =
    countByPostIdGroupingByDirection(postId)
        .asSequence()
        .map { it.count * it.direction.value }
        .sum()
