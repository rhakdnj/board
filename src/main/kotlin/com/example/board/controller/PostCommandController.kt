package com.example.board.controller

import com.example.board.controller.dto.PostCreateRequest
import com.example.board.controller.dto.PostUpdateRequest
import com.example.board.controller.dto.toDto
import com.example.board.service.PostCommandService
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

@Tag(name = "게시글")
@RestController
class PostCommandController(
    private val postCommandService: PostCommandService,
) {
    @Operation(summary = "게시글 생성")
    @PostMapping("/posts")
    fun create(
        @RequestBody request: PostCreateRequest,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postCommandService.createPost(request.toDto()))

    @Operation(summary = "게시글 수정")
    @PutMapping("/posts/{postId}")
    fun update(
        @PathVariable("postId") id: UUID,
        @RequestBody request: PostUpdateRequest,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                postCommandService.updatePost(
                    id = id,
                    updateDto = request.toDto(),
                ),
            )

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/posts/{postId}")
    fun delete(
        @PathVariable("postId") id: UUID,
        @RequestParam("deletedBy") deletedBy: String,
    ): ResponseEntity<UUID> =
        ResponseEntity.status(HttpStatus.OK).body(
            postCommandService.deletePost(
                id = id,
                deletedBy = deletedBy,
            ),
        )
}
