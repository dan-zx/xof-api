package com.github.danzx.xof.core.annotation

import javax.inject.Named

@Named
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class UseCaseComponent(val value: String = "")
