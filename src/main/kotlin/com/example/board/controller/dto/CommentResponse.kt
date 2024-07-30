package com.example.board.controller.dto

import com.example.board.service.dto.CommentResponseDto
import java.time.LocalDateTime
import java.util.UUID

data class CommentResponse(
    val id: UUID,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun CommentResponseDto.toResponse() =
    CommentResponse(
        id = id,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
    )
