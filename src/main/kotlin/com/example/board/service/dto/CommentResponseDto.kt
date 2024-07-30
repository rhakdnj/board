package com.example.board.service.dto

import com.example.board.domain.Comment
import java.time.LocalDateTime
import java.util.UUID

data class CommentResponseDto(
    val id: UUID,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val updatedBy: String? = null,
    val updatedAt: LocalDateTime? = null,
)

fun Comment.toResponseDto() =
    CommentResponseDto(
        id = id,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
        updatedBy = updatedBy,
        updatedAt = updatedAt,
    )
