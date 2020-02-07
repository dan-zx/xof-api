package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.filter.dsl.commentsWith
import com.github.danzx.xof.core.filter.dsl.parentId
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.util.dsl.sortBy
import com.github.danzx.xof.entrypoint.rest.controller.BaseRestController.Companion.BASE_PATH
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.request.mapper.toCreateNewCommentCommand
import com.github.danzx.xof.entrypoint.rest.request.mapper.toPagination
import com.github.danzx.xof.entrypoint.rest.request.mapper.toReplaceCommentContentCommand
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
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import javax.validation.constraints.Min

@RestController
@RequestMapping("$BASE_PATH/comments")
@Api(tags=["Comments API"], description="Comment endpoints")
class CommentRestController : BaseRestController() {

    @Autowired @Qualifier("createNewCommentUseCase")
    lateinit var createNewCommentUseCase: UseCase<CreateNewCommentCommand, Comment>

    @Autowired @Qualifier("getCommentByIdUseCase")
    lateinit var getCommentByIdUseCase: UseCase<Long, Comment>

    @Autowired @Qualifier("getCommentsUseCase")
    lateinit var getCommentsUseCase: UseCase<CommentsLoaderCommand, Page<Comment>>

    @Autowired @Qualifier("replaceCommentContentUseCase")
    lateinit var replaceCommentContentUseCase: UseCase<ReplaceCommentContentCommand, Comment>

    @Autowired @Qualifier("deleteCommentByIdUseCase")
    lateinit var deleteCommentByIdUseCase: UseCase<Long, Unit>

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

    @PatchMapping("/{id}/content")
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
            command = request.toReplaceCommentContentCommand(id)
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
            responseConverter = { ResponseEntities.NO_CONTENT }
        )
}
