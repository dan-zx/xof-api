package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.usecase.UseCaseExecutor

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class TransactionalUseCaseExecutor : UseCaseExecutor()