package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.common.sort.dsl.sortBy
import com.github.danzx.xof.core.filter.dsl.commentsWith
import com.github.danzx.xof.core.filter.dsl.postId
import com.github.danzx.xof.core.filter.dsl.postsWith
import com.github.danzx.xof.core.filter.dsl.title
import com.github.danzx.xof.core.usecase.UseCaseExecutor
import com.github.danzx.xof.core.usecase.comment.GetCommentsUseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.post.CreateNewPostUseCase
import com.github.danzx.xof.core.usecase.post.DeletePostByIdUseCase
import com.github.danzx.xof.core.usecase.post.GetPostByIdUseCase
import com.github.danzx.xof.core.usecase.post.GetPostsUseCase
import com.github.danzx.xof.core.usecase.post.ReplacePostContentUseCase
import com.github.danzx.xof.core.usecase.post.ReplacePostTitleUseCase
import com.github.danzx.xof.core.usecase.post.VoteOnPostUseCase
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.usecase.post.command.ReplacePostContentCommand
import com.github.danzx.xof.core.usecase.post.command.ReplacePostTitleCommand
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.request.TitleUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest
import com.github.danzx.xof.entrypoint.rest.request.mapper.toCreateNewPostCommand
import com.github.danzx.xof.entrypoint.rest.request.mapper.toPagination
import com.github.danzx.xof.entrypoint.rest.request.mapper.toVote
import com.github.danzx.xof.entrypoint.rest.response.ResponseEntities
import com.github.danzx.xof.entrypoint.rest.response.mapper.toPageResponse
import com.github.danzx.xof.entrypoint.rest.response.mapper.toCreatedResponseEntity

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired lateinit var createNewPostUseCase: CreateNewPostUseCase
    @Autowired lateinit var getPostByIdUseCase: GetPostByIdUseCase
    @Autowired lateinit var getPostsUseCase: GetPostsUseCase
    @Autowired lateinit var replacePostTitleUseCase: ReplacePostTitleUseCase
    @Autowired lateinit var replacePostContentUseCase: ReplacePostContentUseCase
    @Autowired lateinit var voteOnPostUseCase: VoteOnPostUseCase
    @Autowired lateinit var deletePostByIdUseCase: DeletePostByIdUseCase
    @Autowired lateinit var getCommentsUseCase: GetCommentsUseCase
    @Autowired lateinit var useCaseExecutor: UseCaseExecutor

    @GetMapping("/{id}")
    @ApiOperation("Get post by id")
    @ApiResponses(
        ApiResponse(code=200, message="OK - Found the post with the supplied id"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    )
    fun getById(@PathVariable @Min(1) id: Long) =
        useCaseExecutor(
            useCase = getPostByIdUseCase,
            command = id
        )

    @GetMapping
    @ApiOperation("Get posts")
    @ApiResponses(
        ApiResponse(code=200, message="OK - Found posts"),
        ApiResponse(code=400, message="Bad request - When the supplied parameters are invalid")
    )
    fun getFilteredWithPagination(
        @RequestParam("q", required=false) titleQuery: String?,
        @Valid paginationRequest: PaginationRequest) =
        useCaseExecutor(
            useCase = getPostsUseCase,
            command = PostsLoaderCommand(
                postsWith { title containing titleQuery },
                paginationRequest.toPagination(),
                sortBy { +"created" }
            ),
            responseConverter = { it.toPageResponse() }
        )

    @GetMapping("/{id}/comments")
    @ApiOperation("Get post comments")
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
                commentsWith { postId eq id },
                paginationRequest.toPagination(),
                sortBy { +"created" }
            ),
            responseConverter = { it.toPageResponse() }
        )

    @PostMapping
    @ApiOperation("Create post")
    @ApiResponses(
        ApiResponse(code=201, message="Created - Post created"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid"),
        ApiResponse(code=404, message="Not Found - When no user matched with the supplied id")
    )
    fun create(@RequestBody @Valid request: CreatePostRequest) =
        useCaseExecutor(
            useCase = createNewPostUseCase,
            command = request.toCreateNewPostCommand(),
            responseConverter = { it.toCreatedResponseEntity() }
        )

    @PutMapping("/{id}/title")
    @ApiOperation("Replace post title")
    @ApiResponses(
        ApiResponse(code=200, message="OK - title replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    )
    fun replaceTitle(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: TitleUpdateRequest) =
        useCaseExecutor(
            useCase = replacePostTitleUseCase,
            command = ReplacePostTitleCommand(id, request.value)
        )

    @PutMapping("/{id}/content")
    @ApiOperation("Replace post content")
    @ApiResponses(
        ApiResponse(code=200, message="OK - content replaced"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    )
    fun replaceContent(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: ContentUpdateRequest) =
        useCaseExecutor(
            useCase = replacePostContentUseCase,
            command = ReplacePostContentCommand(id, request.value)
        )

    @PutMapping("/{id}/vote")
    @ApiOperation("Apply votes to posts")
    @ApiResponses(
        ApiResponse(code=204, message="No Content - Vote applied"),
        ApiResponse(code=400, message="Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When either the user or the post where not found")
    )
    fun vote(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: VoteRequest) =
        useCaseExecutor(
            useCase = voteOnPostUseCase,
            command = request.toVote(id),
            responseConverter = { ResponseEntities.NO_CONTENT }
        )

    @DeleteMapping("/{id}")
    @ApiOperation("Delete post by id")
    @ApiResponses(
        ApiResponse(code=204, message="No Content - Found the post with the supplied id and deleted"),
        ApiResponse(code=400, message="Bad request - When the supplied id is 0 or less"),
        ApiResponse(code=404, message="Not Found - When no post matched with the supplied id")
    )
    fun delete(@PathVariable @Min(1) id: Long) =
        useCaseExecutor(
            useCase = deletePostByIdUseCase,
            command = id,
            responseConverter = { ResponseEntities.NO_CONTENT }
        )
}
