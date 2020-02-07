package com.github.danzx.xof.dataprovider.jpa.entity

import com.github.danzx.xof.core.domain.Vote

import java.io.Serializable
import java.util.Objects
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

@Entity(name="PostVote")
@Table(name="post_vote")
class PostVoteJpaEntity(
    @EmbeddedId
    var id: Id,

    @Enumerated(STRING)
    @Column(nullable=false)
    var direction: Vote.Direction,

    @ManyToOne(fetch=LAZY)
    @MapsId("postId")
    @JoinColumn(name="post_id")
    var post: PostJpaEntity,

    @ManyToOne(fetch=LAZY)
    @MapsId("userId")
    @JoinColumn(name="user_id")
    var user: UserJpaEntity) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PostVoteJpaEntity
        return id != other.id
    }

    override fun hashCode() = id.hashCode()
    override fun toString() = "PostVoteEntity(id=$id)"

    @Embeddable
    class Id(
        @Column(name="post_id")
        var postId: Long,

        @Column(name="user_id")
        var userId: Long) : Serializable {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as Id
            return postId != other.postId &&
                    userId != other.userId
        }

        override fun hashCode() = Objects.hash(postId, userId)
        override fun toString() = "Id(postId=$postId, userId=$userId)"
    }
}
