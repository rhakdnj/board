package com.example.board.controller.dto

import com.example.board.service.dto.PostDetailResponseDto
import java.time.LocalDateTime
import java.util.UUID

data class PostDetailResponse(
    val id: UUID,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun PostDetailResponseDto.toResponse() =
    PostDetailResponse(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
    )
