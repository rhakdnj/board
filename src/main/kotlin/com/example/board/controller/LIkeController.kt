package com.example.board.controller

import com.fasterxml.uuid.Generators
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class LIkeController {
    @PostMapping("/posts/{postId}/likes")
    fun createLike(
        @PathVariable postId: UUID,
        @RequestParam createdBy: String,
    ): ResponseEntity<UUID> {
        println(postId)
        println(createdBy)
        return ResponseEntity.status(HttpStatus.CREATED).body(Generators.timeBasedEpochRandomGenerator().generate())
    }
}
