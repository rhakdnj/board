package com.example.board.service

import com.example.board.exception.PostNotFoundException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostDetailResponseDto
import com.example.board.service.dto.PostSearchRequestDto
import com.example.board.service.dto.PostSummaryResponseDto
import com.example.board.service.dto.toDetailResponse
import com.example.board.service.dto.toSummaryRespnoseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PostQueryService(
    private val postRepository: PostRepository,
) {
    fun getPost(id: UUID): PostDetailResponseDto =
        postRepository.findByIdOrNull(id)?.toDetailResponse()
            ?: throw PostNotFoundException()

    fun findPageBy(
        pageRequest: PageRequest,
        searchDto: PostSearchRequestDto,
    ): Page<PostSummaryResponseDto> =
        postRepository
            .findPageBy(pageRequest, searchDto)
            .toSummaryRespnoseDto()
}
