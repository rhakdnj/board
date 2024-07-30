package com.example.board.controller.dto

import java.time.LocalDateTime
import java.util.UUID

data class CommentResponse(
    val id: UUID,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)
