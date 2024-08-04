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
    val comments: List<CommentResponseDto> = emptyList(),
    val tags: List<String> = emptyList(),
)

fun Post.toDetailResponse() =
    PostDetailResponseDto(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
        comments = comments.map { it.toResponseDto() },
        tags = tags.map { it.name },
    )
