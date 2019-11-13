package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.common.pagination.page
import com.github.danzx.xof.common.pagination.paginationWith
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.filter.commentsWith
import com.github.danzx.xof.core.filter.postId
import com.github.danzx.xof.core.filter.postsWith
import com.github.danzx.xof.core.filter.title
import com.github.danzx.xof.entrypoint.rest.adapter.CommentUseCaseAdapter
import com.github.danzx.xof.entrypoint.rest.adapter.PostUseCaseAdapter
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.request.TitleUpdateRequest
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
@RequestMapping("/posts")
@Api(tags=["Posts API"], description="Post endpoints")
class PostRestController {

    @Autowired lateinit var postUseCaseAdapter: PostUseCaseAdapter
    @Autowired lateinit var commentUseCaseAdapter: CommentUseCaseAdapter

    @GetMapping("/{id}")
    @ApiOperation("Get post by id")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found the post with the supplied id"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    ])
    fun getById(@PathVariable @Min(1) id: Long) = postUseCaseAdapter.getById(id)

    @GetMapping
    @ApiOperation("Get posts")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found posts"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    ])
    fun getFilteredWithPagination(
        @RequestParam("q", required=false) titleQuery: String?,
        @RequestParam("page", required=false) @Min(1) pageNumber: Int?,
        @RequestParam("size", required=false) @Min(1) pageSize: Int?) : PageResponse<Post> {
        val filter = postsWith { title containing titleQuery }
        val pagination = paginationWith {
            page number pageNumber
            page size pageSize
        }
        return postUseCaseAdapter.getAll(filter, pagination)
    }

    @GetMapping("/{id}/comments")
    @ApiOperation("Get post comments")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found comments"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    ])
    fun getComments(
        @PathVariable @Min(1) id: Long,
        @RequestParam("page", required=false) @Min(1) pageNumber: Int?,
        @RequestParam("size", required=false) @Min(1) pageSize: Int?) : PageResponse<Comment> {
        val commentFilter = commentsWith { postId eq id }
        val pagination = paginationWith {
            page number pageNumber
            page size pageSize
        }
        return commentUseCaseAdapter.getAll(commentFilter, pagination)
    }

    @PostMapping
    @ApiOperation("Create post")
    @ApiResponses(value=[
        ApiResponse(code=201, message="Created - Post created"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id")
    ])
    fun create(@RequestBody @Valid request: CreatePostRequest): ResponseEntity<Post> {
        val createdPost = postUseCaseAdapter.create(request)
        return responseEntityForCreatedResource(createdPost.id, createdPost)
    }

    @PutMapping("/{id}/title")
    @ApiOperation("Replace post title")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - title replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    ])
    fun replaceTitle(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: TitleUpdateRequest)
            = postUseCaseAdapter.replaceTitle(id, request.value)

    @PutMapping("/{id}/content")
    @ApiOperation("Replace post content")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - content replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    ])
    fun replaceContent(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: ContentUpdateRequest) = postUseCaseAdapter.replaceContent(id, request.value)

    @PutMapping("/{id}/vote")
    @ApiOperation("Apply votes to posts")
    @ApiResponses(value=[
        ApiResponse(code=204, message="No Content - Vote applied"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When either the user or the post where not found")
    ])
    fun vote(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: VoteRequest): ResponseEntity<Any> {
        postUseCaseAdapter.vote(id, request)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete post by id")
    @ApiResponses(value=[
        ApiResponse(code=204, message="No Content - Found the post with the supplied id and deleted"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    ])
    fun delete(@PathVariable @Min(1) id: Long): ResponseEntity<Any> {
        postUseCaseAdapter.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
