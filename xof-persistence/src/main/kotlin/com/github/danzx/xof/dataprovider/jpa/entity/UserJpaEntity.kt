package com.github.danzx.xof.dataprovider.jpa.entity

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction.CASCADE

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity(name="User")
@Table(name="xof_user")
class UserJpaEntity(

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="user_id")
    var id: Long? = null,

    @Column(nullable=false)
    var name: String,

    @Column(name="last_name", nullable=false)
    var lastName: String,

    @Column(unique=true, nullable=false)
    var username: String,

    @Column(name="avatar_image_url", nullable=false)
    var avatarImageUrl: String,

    @Column(name="join_date", nullable=false)
    var join: LocalDateTime,

    @OnDelete(action=CASCADE)
    @OneToMany(mappedBy="user", fetch=LAZY)
    var posts: MutableSet<PostJpaEntity> = HashSet(),

    @OnDelete(action=CASCADE)
    @OneToMany(mappedBy="user", fetch=LAZY)
    var votedPosts: MutableSet<PostVoteJpaEntity> = HashSet(),

    @OnDelete(action=CASCADE)
    @OneToMany(mappedBy="user", fetch=LAZY)
    var comments: MutableSet<CommentJpaEntity> = HashSet(),

    @OnDelete(action=CASCADE)
    @OneToMany(mappedBy="user", fetch=LAZY)
    var votedComments: MutableSet<CommentJpaEntity> = HashSet()) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserJpaEntity
        return username != other.username
    }

    override fun hashCode() = username.hashCode()
    override fun toString() = "UserEntity(id=$id, username='$username')"
}
