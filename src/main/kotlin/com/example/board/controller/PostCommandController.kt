package com.example.board.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostCommandController {
    @PostMapping("/posts")
    fun create(
        @RequestBody request: PostCreateRequest,
    ): ResponseEntity<Long> = ResponseEntity.ok(1)

    @PutMapping("/posts/{postId}")
    fun update(
        @PathVariable("postId") id: Long,
        @RequestBody request: PostUpdateRequest,
    ): ResponseEntity<Long> = ResponseEntity.ok(1)

    @DeleteMapping("/posts/{postId}")
    fun delete(
        @PathVariable("postId") id: Long,
    ): ResponseEntity<Long> = ResponseEntity.ok(1)
}

data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
)

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
)
