package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostUpdater
import com.github.danzx.xof.core.test.constants.TEST_POST

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
abstract class ReplacePostDataUseCaseBaseTest<T: ReplacePostDataUseCase> {

    @MockK lateinit var getPostByIdUseCase: GetPostByIdUseCase
    @RelaxedMockK lateinit var updater: PostUpdater
    @InjectMockKs lateinit var useCase: T

    @BeforeEach
    fun onBeforeTest() {
        every { getPostByIdUseCase(TEST_POST.id) } returns TEST_POST.copy()
        every { updater.update(any()) } returnsArgument 0
    }
}