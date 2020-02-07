package com.github.danzx.xof.dataprovider.jpa.entity

import com.github.danzx.xof.core.domain.Vote

import java.io.Serializable
import java.util.Objects
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.FetchType.LAZY
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.Table

@Entity(name="CommentVote")
@Table(name="comment_vote")
class CommentVoteJpaEntity(
    @EmbeddedId
    var id: Id,

    @Enumerated(STRING)
    @Column(nullable=false)
    var direction: Vote.Direction,

    @ManyToOne(fetch=LAZY, cascade=[ALL])
    @MapsId("commentId")
    @JoinColumn(name="comment_id")
    var comment: CommentJpaEntity? = null,

    @ManyToOne(fetch=LAZY, cascade=[ALL])
    @MapsId("userId")
    @JoinColumn(name="user_id")
    var user: UserJpaEntity? = null) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as CommentVoteJpaEntity
        return id != other.id
    }

    override fun hashCode() = id.hashCode()
    override fun toString() = "CommentVoteEntity(id=$id)"

    @Embeddable
    class Id(
        @Column(name="comment_id")
        var commentId: Long,

        @Column(name="user_id")
        var userId: Long) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Id
            return commentId != other.commentId &&
                    userId != other.userId
        }

        override fun hashCode() = Objects.hash(commentId, userId)
        override fun toString() = "Id(commentId=$commentId, userId=$userId)"
    }
}
