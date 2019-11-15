package com.github.danzx.xof.core.exception

abstract class EntityNotFoundException : Exception()
class CommentNotFoundException : EntityNotFoundException()
class PostNotFoundException : EntityNotFoundException()
class UserNotFoundException : EntityNotFoundException()
