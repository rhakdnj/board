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
    val comments: List<CommentResponse> = emptyList(),
    val tags: List<String> = emptyList(),
    val likeCount: Int = 0,
)

fun PostDetailResponseDto.toResponse() =
    PostDetailResponse(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
        comments = comments.map { it.toResponse() },
        tags = tags,
    )
