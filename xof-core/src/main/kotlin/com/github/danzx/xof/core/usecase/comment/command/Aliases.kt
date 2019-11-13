package com.github.danzx.xof.core.usecase.comment.command

import com.github.danzx.xof.core.usecase.command.ReplaceDataValueCommand

typealias ReplaceCommentContentCommand = ReplaceDataValueCommand<String>

fun command(id: Long, value: String) = ReplaceDataValueCommand(id, value)
