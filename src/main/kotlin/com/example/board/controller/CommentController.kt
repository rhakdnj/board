package com.example.board.controller

import com.example.board.controller.dto.CommentCreateRequest
import com.example.board.controller.dto.CommentUpdateRequest
import com.example.board.controller.dto.toDto
import com.example.board.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "댓글")
@RestController
class CommentController(
    private val commentService: CommentService,
) {
    @Operation(summary = "댓글 생성")
    @PostMapping("/posts/{postId}/comments")
    fun create(
        @PathVariable postId: UUID,
        @RequestBody request: CommentCreateRequest,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.createComment(postId, request.toDto()))

    @Operation(summary = "댓글 수정")
    @PutMapping("/comment/{commentId}")
    fun update(
        @PathVariable("commentId") id: UUID,
        @RequestBody request: CommentUpdateRequest,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                commentService.updateComment(
                    id = id,
                    requestDto = request.toDto(),
                ),
            )

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    fun delete(
        @PathVariable("commentId") id: UUID,
        @RequestParam("deletedBy") deletedBy: String,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                commentService.deleteComment(
                    id = id,
                    deletedBy = deletedBy,
                ),
            )
}
