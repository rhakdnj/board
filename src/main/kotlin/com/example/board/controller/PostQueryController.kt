package com.example.board.controller

import com.example.board.controller.dto.PostDetailResponse
import com.example.board.controller.dto.PostSearchRequest
import com.example.board.controller.dto.toDto
import com.example.board.controller.dto.toResponse
import com.example.board.service.PostQueryService
import com.example.board.service.dto.PostSummaryResponseDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "게시글")
@RestController
class PostQueryController(
    private val postQueryService: PostQueryService,
) {
    @Operation(summary = "게시글 검색")
    @GetMapping("/posts")
    fun search(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        request: PostSearchRequest,
    ): ResponseEntity<Page<PostSummaryResponseDto>> =
        ResponseEntity
            .ok()
            .body(
                postQueryService.findPageBy(
                    pageRequest = PageRequest.of(page - 1, size),
                    searchDto = request.toDto(),
                ),
            )

    @Operation(summary = "게시글 조회")
    @GetMapping("/posts/{postId}")
    fun get(
        @PathVariable("postId") id: UUID,
    ): ResponseEntity<PostDetailResponse> =
        ResponseEntity
            .ok()
            .body(postQueryService.getPost(id).toResponse())
}
