package com.example.board.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@Tag(name = "게시글")
@RestController
class PostQueryController {
    @Operation(summary = "게시글 검색")
    @GetMapping("/posts")
    fun search(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        request: PostSearchRequest,
    ): ResponseEntity<Long> = ResponseEntity.ok(1)

    @Operation(summary = "게시글 조회")
    @GetMapping("/posts/{postId}")
    fun get(
        @PathVariable("postId") id: Long,
    ): ResponseEntity<PostResponse> =
        ResponseEntity.ok(
            PostResponse(
                id = 1,
                title = "title",
                content = "content",
                createdBy = "createdBy",
                createdAt = LocalDateTime.now(),
            ),
        )
}

data class PostSearchRequest(
    @RequestParam
    val title: String?,
    @RequestParam
    val createdBy: String?,
)

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)
