package com.github.danzx.xof.core.usecase

interface UseCaseExecutor {
    operator fun <Command, Response, ResponseDto> invoke(
        useCase: UseCase<Command, Response>,
        command: Command,
        responseConverter: (Response) -> ResponseDto) =
        responseConverter(useCase(command))

    operator fun <Command, Response> invoke(
        useCase: UseCase<Command, Response>,
        command: Command) =
        useCase(command)
}