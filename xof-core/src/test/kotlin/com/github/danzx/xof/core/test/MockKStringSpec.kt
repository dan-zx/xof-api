package com.github.danzx.xof.core.test

import io.kotlintest.TestCase
import io.kotlintest.specs.StringSpec
import io.mockk.MockKAnnotations.init

abstract class MockKStringSpec : StringSpec() {

    override fun beforeTest(testCase: TestCase) = init(this, relaxUnitFun = true)
}