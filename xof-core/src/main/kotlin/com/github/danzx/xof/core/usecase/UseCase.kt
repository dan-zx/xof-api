package com.github.danzx.xof.core.usecase

interface UseCase<in Command, out Response> {
    operator fun invoke(command: Command) : Response
}
