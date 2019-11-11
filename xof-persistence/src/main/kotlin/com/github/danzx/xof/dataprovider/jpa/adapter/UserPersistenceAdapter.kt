package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.repository.user.UserByIdLoader
import com.github.danzx.xof.core.repository.user.UserByIdRemover
import com.github.danzx.xof.core.repository.user.UserByUsernameLoader
import com.github.danzx.xof.core.repository.user.UserIdChecker
import com.github.danzx.xof.core.repository.user.UserPersister
import com.github.danzx.xof.core.repository.user.UserUpdater
import com.github.danzx.xof.core.repository.user.UserUsernameChecker
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toUser
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toUserJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter : UserPersister, UserByIdLoader, UserByUsernameLoader, UserIdChecker, UserUsernameChecker, UserUpdater, UserByIdRemover {

    @Autowired
    lateinit var userJpaRepository: UserJpaRepository

    override fun save(user: User) : User {
        var userJpaEntity = user.toUserJpaEntity()
        userJpaEntity = userJpaRepository.save(userJpaEntity)
        user.id = userJpaEntity.id!!
        return user
    }

    override fun loadById(id: Long) = userJpaRepository.findByIdOrNull(id)?.toUser()

    override fun loadByUsername(username: String) = userJpaRepository.findByUsername(username)?.toUser()

    override fun existsId(id: Long) = userJpaRepository.existsById(id)

    override fun existsUsername(username: String) = userJpaRepository.existsByUsername(username)

    override fun update(user: User) = save(user)

    override fun removeById(id: Long) = userJpaRepository.deleteById(id)
}