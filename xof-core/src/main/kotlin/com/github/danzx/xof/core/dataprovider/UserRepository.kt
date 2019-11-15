package com.github.danzx.xof.core.dataprovider

import com.github.danzx.xof.core.domain.User

interface UserByIdLoader : DataByIdLoader<Long, User>
interface UserByIdRemover : DataByIdRemover<Long>
interface UserIdChecker : DataIdChecker<Long>
interface UserPersister : DataPersister<User>
interface UserUpdater : DataUpdater<User>

interface UserByUsernameLoader {
    fun loadByUsername(username: String) : User?
}

interface UserUsernameChecker {
    fun existsUsername(username: String) : Boolean
}
