package com.example.board.controller

import com.example.board.service.LikeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class LIkeController(
    private val likeService: LikeService,
) {
    @PostMapping("/posts/{postId}/likes")
    fun createLike(
        @PathVariable postId: UUID,
        @RequestParam createdBy: String,
    ): ResponseEntity<UUID> =
        ResponseEntity.status(HttpStatus.CREATED).body(
            likeService.createLike(postId = postId, createdBy = createdBy),
        )
}
