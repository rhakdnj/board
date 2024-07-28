package com.example.board.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostQueryController {
    @GetMapping("/posts")
    fun search(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        request: PostSearchRequest,
    ): ResponseEntity<Long> = ResponseEntity.ok(1)

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
