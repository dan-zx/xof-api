package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.common.pagination.page
import com.github.danzx.xof.common.pagination.paginationWith
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.filter.commentsWith
import com.github.danzx.xof.core.filter.postsWith
import com.github.danzx.xof.core.filter.userId
import com.github.danzx.xof.entrypoint.rest.adapter.CommentUseCaseAdapter
import com.github.danzx.xof.entrypoint.rest.adapter.PostUseCaseAdapter
import com.github.danzx.xof.entrypoint.rest.adapter.UserUseCaseAdapter
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest
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
import javax.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping("/users")
@Api(tags=["Users API"], description="User endpoints")
class UserRestController {

    @Autowired lateinit var userUseCaseAdapter: UserUseCaseAdapter
    @Autowired lateinit var postUseCaseAdapter: PostUseCaseAdapter
    @Autowired lateinit var commentUseCaseAdapter: CommentUseCaseAdapter

    @GetMapping
    @ApiOperation("Get user")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found the user with the supplied username"),
        ApiResponse(code=400, message="Bad request - When the supplied username is blank"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied username")
    ])
    fun getByUsername(@RequestParam("username") @NotBlank username: String) = userUseCaseAdapter.getByUsername(username)

    @GetMapping("/{id}")
    @ApiOperation("Get user by id")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found the user with the supplied id"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id")
    ])
    fun getById(@PathVariable @Min(1) id: Long) = userUseCaseAdapter.getById(id)

    @GetMapping("/{id}/posts")
    @ApiOperation("Get user posts")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found posts"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    ])
    fun getPosts(
        @PathVariable @Min(1) id: Long,
        @RequestParam("page", required=false) @Min(1) pageNumber: Int?,
        @RequestParam("size", required=false) @Min(1) pageSize: Int?) : PageResponse<Post> {
        val filter = postsWith { userId eq id }
        val pagination = paginationWith {
            page number pageNumber
            page size pageSize
        }
        return postUseCaseAdapter.getAll(filter, pagination)
    }

    @GetMapping("/{id}/comments")
    @ApiOperation("Get user comments")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - Found comments"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    ])
    fun getComments(
        @PathVariable @Min(1) id: Long,
        @RequestParam("page", required=false) @Min(1) pageNumber: Int?,
        @RequestParam("size", required=false) @Min(1) pageSize: Int?) : PageResponse<Comment> {
        val filter = commentsWith { userId eq id }
        val pagination = paginationWith {
            page number pageNumber
            page size pageSize
        }
        return commentUseCaseAdapter.getAll(filter, pagination)
    }

    @PostMapping
    @ApiOperation("Create user")
    @ApiResponses(value=[
        ApiResponse(code=201, message="Created - User created"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid"),
        ApiResponse(code=409, message="Conflict - The supplied username already exists")
    ])
    fun create(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<User> {
        val user = userUseCaseAdapter.create(request)
        return responseEntityForCreatedResource(user.id, user)
    }

    @PutMapping("/{id}")
    @ApiOperation("Replace user")
    @ApiResponses(value=[
        ApiResponse(code=200, message="OK - User replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id"),
        ApiResponse(code=409, message="Conflict - The supplied username already exists")
    ])
    fun replace(@PathVariable @Min(1) id: Long,
                @RequestBody @Valid request: ReplaceUserRequest) = userUseCaseAdapter.replace(id, request)

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user by id")
    @ApiResponses(value=[
        ApiResponse(code=204, message="No Content - Found the user with the supplied id and deleted"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id")
    ])
    fun delete(@PathVariable @Min(1) id: Long): ResponseEntity<Any> {
        userUseCaseAdapter.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
