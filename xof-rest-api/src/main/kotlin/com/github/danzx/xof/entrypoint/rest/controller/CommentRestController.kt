package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.common.pagination.dsl.page
import com.github.danzx.xof.common.pagination.dsl.paginationWith
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.filter.dsl.commentsWith
import com.github.danzx.xof.core.filter.dsl.parentId
import com.github.danzx.xof.entrypoint.rest.adapter.CommentUseCaseAdapter
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest
import com.github.danzx.xof.entrypoint.rest.response.PageResponse
import com.github.danzx.xof.entrypoint.rest.utils.responseEntityForCreatedResource

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import javax.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/comments")
@Api(tags=["Comments API"], description="Comment endpoints")
class CommentRestController {

    @Autowired lateinit var commentUseCaseAdapter: CommentUseCaseAdapter

    @GetMapping("/{id}")
    @ApiOperation("Get comment by id")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found the comment with the supplied id"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no comment matched with the supplied id")
    ])
    fun getById(@PathVariable @Min(1) id: Long) = commentUseCaseAdapter.getById(id)

    @GetMapping("/{id}/replies")
    @ApiOperation("Get comment replies")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found comments"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    ])
    fun getReplies(
        @PathVariable @Min(1) id: Long,
        @RequestParam("page", required=false) @Min(1) pageNumber: Int?,
        @RequestParam("size", required=false) @Min(1) pageSize: Int?) : PageResponse<Comment> {
        val commentFilter = commentsWith { parentId eq id }
        val pagination = paginationWith {
            page number pageNumber
            page size pageSize
        }
        return commentUseCaseAdapter.getAll(commentFilter, pagination)
    }

    @PostMapping
    @ApiOperation("Create comment")
    @ApiResponses(value=[
        ApiResponse(code=201, message="Created - Comment created"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid"),
        ApiResponse(code=404, message="Not Found - When either the user or the post where not found")
    ])
    fun create(@RequestBody @Valid request: CreateCommentRequest): ResponseEntity<Comment> {
        val createdComment = commentUseCaseAdapter.create(request)
        return responseEntityForCreatedResource(createdComment.id, createdComment)
    }

    @PutMapping("/{id}/content")
    @ApiOperation("Replace comment content")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Content replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no comment matched with the supplied id")
    ])
    fun replaceContent(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: ContentUpdateRequest) = commentUseCaseAdapter.replaceContent(id, request.value)

    @PutMapping("/{id}/vote")
    @ApiOperation("Apply votes to comment")
    @ApiResponses(value=[
        ApiResponse(code=204, message="No Content - Vote applied"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When either the user or the comment where not found")
    ])
    fun vote(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: VoteRequest): ResponseEntity<Any> {
        commentUseCaseAdapter.vote(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete comment by id")
    @ApiResponses(value=[
        ApiResponse(code=204, message="No Content - Found the comment with the supplied id and deleted"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no comment matched with the supplied id")
    ])
    fun delete(@PathVariable @Min(1) id: Long): ResponseEntity<Any> {
        commentUseCaseAdapter.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
