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
    val firstTag: String? = null,
    val likeCount: Int = 0,
)

fun Page<Post>.toSummaryRespnoseDto(countLike: (UUID) -> Int) =
    PageImpl(
        content.map { it.toSummaryResponse(countLike) },
        pageable,
        totalElements,
    )

fun Post.toSummaryResponse(countLike: (UUID) -> Int) =
    PostSummaryResponseDto(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt,
        firstTag = tags.firstOrNull()?.name,
        likeCount = countLike(id),
    )
