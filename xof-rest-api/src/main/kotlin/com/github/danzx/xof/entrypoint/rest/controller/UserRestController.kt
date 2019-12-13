package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.filter.dsl.commentsWith
import com.github.danzx.xof.core.filter.dsl.postsWith
import com.github.danzx.xof.core.filter.dsl.userId
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.util.dsl.sortBy
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest
import com.github.danzx.xof.entrypoint.rest.request.mapper.toCreateNewUserCommand
import com.github.danzx.xof.entrypoint.rest.request.mapper.toPagination
import com.github.danzx.xof.entrypoint.rest.request.mapper.toReplaceUserCommand
import com.github.danzx.xof.entrypoint.rest.response.ResponseEntities
import com.github.danzx.xof.entrypoint.rest.response.mapper.toPageResponse
import com.github.danzx.xof.entrypoint.rest.response.mapper.toCreatedResponseEntity

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
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

@RestController
@RequestMapping("/api/users")
@Api(tags=["Users API"], description="User endpoints")
class UserRestController : BaseRestController() {

    @Autowired @Qualifier("createNewUserUseCase")
    lateinit var createNewUserUseCase: UseCase<CreateNewUserCommand, User>

    @Autowired @Qualifier("getUserByIdUseCase")
    lateinit var getUserByIdUseCase: UseCase<Long, User>

    @Autowired @Qualifier("getUserByUsernameUseCase")
    lateinit var getUserByUsernameUseCase: UseCase<String, User>

    @Autowired @Qualifier("replaceUserUseCase")
    lateinit var replaceUserUseCase: UseCase<ReplaceUserCommand, User>

    @Autowired @Qualifier("deleteUserByIdUseCase")
    lateinit var deleteUserByIdUseCase: UseCase<Long, Unit>

    @Autowired @Qualifier("getPostsUseCase")
    lateinit var getPostsUseCase: UseCase<PostsLoaderCommand, Page<Post>>

    @Autowired @Qualifier("getCommentsUseCase")
    lateinit var getCommentsUseCase: UseCase<CommentsLoaderCommand, Page<Comment>>

    @GetMapping
    @ApiOperation("Get user")
    @ApiResponses(
        ApiResponse(code=200, message="OK - Found the user with the supplied username"),
        ApiResponse(code=400, message="Bad request - When the supplied username is blank"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied username")
    )
    fun getByUsername(@RequestParam("username") @NotBlank username: String) =
        useCaseExecutor(
            useCase = getUserByUsernameUseCase,
            command = username
        )

    @GetMapping("/{id}")
    @ApiOperation("Get user by id")
    @ApiResponses(
        ApiResponse(code=200, message="OK - Found the user with the supplied id"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id")
    )
    fun getById(@PathVariable @Min(1) id: Long) =
        useCaseExecutor(
            useCase = getUserByIdUseCase,
            command = id
        )

    @GetMapping("/{id}/posts")
    @ApiOperation("Get user posts")
    @ApiResponses(
        ApiResponse(code=200, message="OK - Found posts"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    )
    fun getPosts(
        @PathVariable @Min(1) id: Long,
        @Valid paginationRequest: PaginationRequest) =
        useCaseExecutor(
            useCase = getPostsUseCase,
            command = PostsLoaderCommand(
                postsWith { userId eq id },
                paginationRequest.toPagination(),
                sortBy { +"created" }
            ),
            responseConverter = { it.toPageResponse() }
        )

    @GetMapping("/{id}/comments")
    @ApiOperation("Get user comments")
    @ApiResponses(
        ApiResponse(code=200, message="OK - Found comments"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    )
    fun getComments(
        @PathVariable @Min(1) id: Long,
        @Valid paginationRequest: PaginationRequest) =
        useCaseExecutor(
            useCase = getCommentsUseCase,
            command = CommentsLoaderCommand(
                commentsWith { userId eq id },
                paginationRequest.toPagination(),
                sortBy { +"created" }
            ),
            responseConverter = { it.toPageResponse() }
        )

    @PostMapping
    @ApiOperation("Create user")
    @ApiResponses(
        ApiResponse(code=201, message="Created - User created"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid"),
        ApiResponse(code=409, message="Conflict - The supplied username already exists")
    )
    fun create(@RequestBody @Valid request: CreateUserRequest) =
        useCaseExecutor(
            useCase = createNewUserUseCase,
            command = request.toCreateNewUserCommand(),
            responseConverter = { it.toCreatedResponseEntity() }
        )

    @PutMapping("/{id}")
    @ApiOperation("Replace user")
    @ApiResponses(
        ApiResponse(code=200, message="OK - User replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id"),
        ApiResponse(code=409, message="Conflict - The supplied username already exists")
    )
    fun replace(@PathVariable @Min(1) id: Long,
                @RequestBody @Valid request: ReplaceUserRequest) =
        useCaseExecutor(
            useCase = replaceUserUseCase,
            command = request.toReplaceUserCommand(id)
        )

    @DeleteMapping("/{id}")
    @ApiOperation("Delete user by id")
    @ApiResponses(
        ApiResponse(code=204, message="No Content - Found the user with the supplied id and deleted"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id")
    )
    fun delete(@PathVariable @Min(1) id: Long) =
        useCaseExecutor(
            useCase = deleteUserByIdUseCase,
            command = id,
            responseConverter = { ResponseEntities.NO_CONTENT }
        )
}
