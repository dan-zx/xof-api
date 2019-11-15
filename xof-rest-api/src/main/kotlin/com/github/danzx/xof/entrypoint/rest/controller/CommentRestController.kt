package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.common.sort.dsl.sortBy
import com.github.danzx.xof.core.filter.dsl.commentsWith
import com.github.danzx.xof.core.filter.dsl.parentId
import com.github.danzx.xof.core.usecase.UseCaseExecutor
import com.github.danzx.xof.core.usecase.comment.CreateNewCommentUseCase
import com.github.danzx.xof.core.usecase.comment.DeleteCommentByIdUseCase
import com.github.danzx.xof.core.usecase.comment.GetCommentByIdUseCase
import com.github.danzx.xof.core.usecase.comment.GetCommentsUseCase
import com.github.danzx.xof.core.usecase.comment.ReplaceCommentContentUseCase
import com.github.danzx.xof.core.usecase.comment.VoteOnCommentUseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest
import com.github.danzx.xof.entrypoint.rest.request.mapper.toCreateNewCommentCommand
import com.github.danzx.xof.entrypoint.rest.request.mapper.toPagination
import com.github.danzx.xof.entrypoint.rest.request.mapper.toVote
import com.github.danzx.xof.entrypoint.rest.response.mapper.responseEntityWithNoContent
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
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import javax.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/comments")
@Api(tags=["Comments API"], description="Comment endpoints")
class CommentRestController {

    @Autowired lateinit var createNewCommentUseCase: CreateNewCommentUseCase
    @Autowired lateinit var getCommentByIdUseCase: GetCommentByIdUseCase
    @Autowired lateinit var getCommentsUseCase: GetCommentsUseCase
    @Autowired lateinit var replaceCommentContentUseCase: ReplaceCommentContentUseCase
    @Autowired lateinit var voteOnCommentUseCase: VoteOnCommentUseCase
    @Autowired lateinit var deleteCommentByIdUseCase: DeleteCommentByIdUseCase
    @Autowired lateinit var useCaseExecutor: UseCaseExecutor

    @GetMapping("/{id}")
    @ApiOperation("Get comment by id")
    @ApiResponses(
        ApiResponse(code = 200, message = "OK - Found the comment with the supplied id"),
        ApiResponse(code = 400, message = "Bad request - When the supplied id is 0 or less"),
        ApiResponse(code = 404, message = "Not Found - When no comment matched with the supplied id")
    )
    fun getById(@PathVariable @Min(1) id: Long) =
        useCaseExecutor(
            useCase = getCommentByIdUseCase,
            command = id
        )

    @GetMapping("/{id}/replies")
    @ApiOperation("Get comment replies")
    @ApiResponses(
        ApiResponse(code = 200, message = "OK - Found comments"),
        ApiResponse(code = 400, message = "Bad request - When the supplied parameters are invalid")
    )
    fun getReplies(
        @PathVariable @Min(1) id: Long,
        @Valid paginationRequest: PaginationRequest) =
        useCaseExecutor(
            useCase = getCommentsUseCase,
            command = CommentsLoaderCommand(
                commentsWith { parentId eq id },
                paginationRequest.toPagination(),
                sortBy { +"created" }
            ),
            responseConverter = { it.toPageResponse() }
        )

    @PostMapping
    @ApiOperation("Create comment")
    @ApiResponses(
        ApiResponse(code = 201, message = "Created - Comment created"),
        ApiResponse(code = 400, message = "Bad request - The supplied payload is invalid"),
        ApiResponse(code = 404, message = "Not Found - When either the user or the post where not found")
    )
    fun create(@RequestBody @Valid request: CreateCommentRequest) =
        useCaseExecutor(
            useCase = createNewCommentUseCase,
            command = request.toCreateNewCommentCommand(),
            responseConverter = { it.toCreatedResponseEntity() }
        )

    @PutMapping("/{id}/content")
    @ApiOperation("Replace comment content")
    @ApiResponses(
        ApiResponse(code = 200, message = "OK - Content replaced"),
        ApiResponse(code = 400, message = "Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code = 404, message = "Not Found - When no comment matched with the supplied id")
    )
    fun replaceContent(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: ContentUpdateRequest) =
        useCaseExecutor(
            useCase = replaceCommentContentUseCase,
            command = ReplaceCommentContentCommand(id, request.value)
        )

    @PutMapping("/{id}/vote")
    @ApiOperation("Apply votes to comment")
    @ApiResponses(
        ApiResponse(code = 204, message = "No Content - Vote applied"),
        ApiResponse(code = 400, message = "Bad request - The supplied payload is invalid or the supplied id is 0 or less"),
        ApiResponse(code = 404, message = "Not Found - When either the user or the comment where not found")
    )
    fun vote(
        @PathVariable @Min(1) id: Long,
        @RequestBody @Valid request: VoteRequest) =
        useCaseExecutor(
            useCase = voteOnCommentUseCase,
            command = request.toVote(id),
            responseConverter = { responseEntityWithNoContent() }
        )

    @DeleteMapping("/{id}")
    @ApiOperation("Delete comment by id")
    @ApiResponses(
        ApiResponse(code = 204, message = "No Content - Found the comment with the supplied id and deleted"),
        ApiResponse(code = 400, message = "Bad request - When the supplied id is 0 or less"),
        ApiResponse(code = 404, message = "Not Found - When no comment matched with the supplied id")
    )
    fun delete(@PathVariable @Min(1) id: Long) =
        useCaseExecutor(
            useCase = deleteCommentByIdUseCase,
            command = id,
            responseConverter = { responseEntityWithNoContent() }
        )
}
