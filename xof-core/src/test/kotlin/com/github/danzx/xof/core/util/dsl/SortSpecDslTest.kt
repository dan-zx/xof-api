package com.github.danzx.xof.core.util.dsl

import com.github.danzx.xof.core.util.SortSpec
import com.github.danzx.xof.core.util.SortSpec.Direction.ASC
import com.github.danzx.xof.core.util.SortSpec.Direction.DESC

import io.kotlintest.matchers.collections.shouldContainInOrder
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.specs.StringSpec

class SortSpecDslTest : StringSpec({

    "sortBy should return a List<SortSpec>" {
        val expected = listOf(SortSpec("prop1", ASC), SortSpec("prop2", DESC))
        val actual = sortBy {
            +"prop1"
            -"prop2"
        }
        actual shouldHaveSize expected.size
        actual shouldContainInOrder expected
    }
})