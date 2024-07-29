package com.example.board.controller.dto

import com.example.board.service.dto.PostSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime
import java.util.UUID

data class PostSummaryResponse(
    val id: UUID,
    val title: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun Page<PostSummaryResponseDto>.toResponse() =
    PageImpl(
        content.map { it.toResponse() },
        pageable,
        totalElements,
    )

fun PostSummaryResponseDto.toResponse() =
    PostSummaryResponse(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt,
    )
