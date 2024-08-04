package com.example.board.service

import com.example.board.exception.PostNotDeletableException
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostCreateRequestDto
import com.example.board.service.dto.PostDetailResponseDto
import com.example.board.service.dto.PostSearchRequestDto
import com.example.board.service.dto.PostSummaryResponseDto
import com.example.board.service.dto.PostUpdateDto
import com.example.board.service.dto.toDetailResponse
import com.example.board.service.dto.toEntity
import com.example.board.service.dto.toSummaryRespnoseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createPost(createDto: PostCreateRequestDto): UUID = postRepository.save(createDto.toEntity()).id

    @Transactional
    fun updatePost(
        id: UUID,
        updateDto: PostUpdateDto,
    ): UUID {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        post.update(updateDto)
        return id
    }

    @Transactional
    fun deletePost(
        id: UUID,
        deletedBy: String,
    ): UUID {
        val post = postRepository.findByIdOrNull(id) ?: throw PostNotFoundException()
        if (post.createdBy != deletedBy) {
            throw PostNotDeletableException()
        }
        postRepository.delete(post)
        return id
    }

    fun findPageBy(
        pageRequest: PageRequest,
        requestDto: PostSearchRequestDto,
    ): Page<PostSummaryResponseDto> =
        postRepository
            .findPageBy(pageRequest, requestDto)
            .toSummaryRespnoseDto()

    fun getPost(id: UUID): PostDetailResponseDto =
        postRepository.findByIdOrNull(id)?.toDetailResponse()
            ?: throw PostNotFoundException()
}
