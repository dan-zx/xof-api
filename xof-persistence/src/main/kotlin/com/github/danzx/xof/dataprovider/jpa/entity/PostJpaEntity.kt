package com.github.danzx.xof.dataprovider.jpa.entity

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode.JOIN

import java.io.Serializable
import java.time.LocalDateTime
import java.util.Objects
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity(name="Post")
@Table(name="post", uniqueConstraints=[UniqueConstraint(columnNames=["created_date", "user_id"])])
class PostJpaEntity(

    @Id
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="post_id")
    var id: Long? = null,

    @Column(nullable=false)
    var title: String,

    @Column(nullable=false, columnDefinition="CLOB")
    var content: String,

    @Column(name="created_date", nullable=false)
    var created: LocalDateTime,

    @Column(name="updated_date", nullable=false)
    var updated: LocalDateTime,

    @Fetch(JOIN)
    @ManyToOne(optional=false, cascade=[ALL])
    @JoinColumn(name="user_id", nullable=false)
    var user: UserJpaEntity,

    @OneToMany(mappedBy="post", fetch=LAZY, cascade=[ALL], orphanRemoval=true)
    var comments: MutableSet<CommentJpaEntity> = HashSet(),

    @OneToMany(mappedBy="post", fetch=LAZY, cascade=[ALL], orphanRemoval=true)
    var votes: MutableSet<PostVoteJpaEntity> = HashSet()) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PostJpaEntity
        return created != other.created &&
                user != other.user
    }

    override fun hashCode() = Objects.hash(created, user)
    override fun toString() = "PostEntity(id=$id, created=$created, user=$user)"
}
