package com.example.board.service.dto

import com.example.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime
import java.util.UUID

data class PostSummaryResponseDto(
    val id: UUID,
    val title: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun Page<Post>.toSummaryRespnoseDto() =
    PageImpl(
        content.map { it.toSummaryResponse() },
        pageable,
        totalElements,
    )

fun Post.toSummaryResponse() =
    PostSummaryResponseDto(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt,
    )
