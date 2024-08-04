package com.example.board.controller

import com.example.board.controller.dto.PostCreateRequest
import com.example.board.controller.dto.PostDetailResponse
import com.example.board.controller.dto.PostSearchRequest
import com.example.board.controller.dto.PostSummaryResponse
import com.example.board.controller.dto.PostUpdateRequest
import com.example.board.controller.dto.toDto
import com.example.board.controller.dto.toResponse
import com.example.board.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "게시글")
@RestController
class PostController(
    private val postService: PostService,
) {
    @Operation(summary = "게시글 생성")
    @PostMapping("/posts")
    fun create(
        @RequestBody request: PostCreateRequest,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.createPost(request.toDto()))

    @Operation(summary = "게시글 수정")
    @PutMapping("/posts/{postId}")
    fun update(
        @PathVariable("postId") id: UUID,
        @RequestBody request: PostUpdateRequest,
    ): ResponseEntity<UUID> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(
                postService.updatePost(
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
            postService.deletePost(
                id = id,
                deletedBy = deletedBy,
            ),
        )

    @Operation(summary = "게시글 검색")
    @GetMapping("/posts")
    fun search(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        request: PostSearchRequest,
    ): ResponseEntity<Page<PostSummaryResponse>> =
        ResponseEntity
            .ok()
            .body(
                postService
                    .findPageBy(
                        pageRequest = PageRequest.of(page - 1, size),
                        requestDto = request.toDto(),
                    ).toResponse(),
            )

    @Operation(summary = "게시글 조회")
    @GetMapping("/posts/{postId}")
    fun get(
        @PathVariable("postId") id: UUID,
    ): ResponseEntity<PostDetailResponse> =
        ResponseEntity
            .ok()
            .body(postService.getPost(id).toResponse())
}
