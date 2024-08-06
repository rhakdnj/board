package com.example.board.repository

import com.example.board.domain.Post
import com.example.board.domain.QPost.post
import com.example.board.domain.QTag.tag
import com.example.board.service.dto.PostSearchRequestDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.UUID

interface PostRepository :
    JpaRepository<Post, UUID>,
    CustomPostRepository

interface CustomPostRepository {
    fun findPageBy(
        pageRequest: PageRequest,
        requestDto: PostSearchRequestDto,
    ): Page<Post>
}

class CustomPostRepositoryImpl :
    QuerydslRepositorySupport(Post::class.java),
    CustomPostRepository {
    override fun findPageBy(
        pageRequest: PageRequest,
        requestDto: PostSearchRequestDto,
    ): Page<Post> {
        // HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
        // - offset + limit 쿼리 발생이 아니라 메모리상에서 처리함
        val result =
            from(post)
                .leftJoin(post.tags, tag)
                .fetchJoin()
                .where(
                    requestDto.title?.let { post.title.contains(it) },
                    requestDto.createdBy?.let { post.createdBy.eq(it) },
                    requestDto.tag?.let {
                        post.tags
                            .any()
                            .name
                            .eq(it)
                    },
                ).orderBy(post.createdAt.desc())
                .offset(pageRequest.offset)
                .limit(pageRequest.pageSize.toLong())
                .fetchResults()

        return PageImpl(result.results, pageRequest, result.total)
    }
}
