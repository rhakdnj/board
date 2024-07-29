package com.example.board.service.dto

import com.example.board.domain.Post
import java.time.LocalDateTime
import java.util.UUID

data class PostDetailResponseDto(
    val id: UUID,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun Post.toDetailResponse() =
    PostDetailResponseDto(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
    )
