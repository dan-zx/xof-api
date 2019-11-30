package com.github.danzx.xof.common.pagination

import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class PageTest : StringSpec({

    "page should have hasNext and hasPrevious" {
        forall(
            row(2, 2, false, true),
            row(1, 1, false, false),
            row(2, 3, true, true),
            row(1, 2, true, false)
        ){ pageNumber, totalPages, hasNext, hasPrevious ->

            val page = Page<Any>(
                metadata = Page.Metadata(
                    totalPages = totalPages,
                    number = pageNumber
                ))

            page.hasNext shouldBe hasNext
            page.hasPrevious shouldBe hasPrevious
        }
    }
})