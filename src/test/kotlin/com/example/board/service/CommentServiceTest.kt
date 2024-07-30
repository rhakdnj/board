package com.example.board.service

import com.example.board.domain.Comment
import com.example.board.domain.Post
import com.example.board.exception.CommentNotDeletableException
import com.example.board.exception.CommentNotUpdatableException
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.CommentRepository
import com.example.board.repository.PostRepository
import com.example.board.service.dto.CommentCreateRequestDto
import com.example.board.service.dto.CommentUpdateRequestDto
import com.fasterxml.uuid.Generators
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
        given("댓글 생성 시") {
            val post =
                postRepository.save(
                    Post(title = "게시글 제목", content = "게시글 내용", createdBy = "게시글 생성자"),
                )
            When("댓글 인풋이 정상적으로 들어오면") {
                val commentId =
                    commentService.createComment(
                        postId = post.id,
                        requestDto = CommentCreateRequestDto(content = "댓글 내용", createdBy = "댓글 생성자"),
                    )
                then("정상적으로 생성됨을 확인한다.") {
                    commentId shouldNotBe null
                    val comment = commentRepository.findByIdOrNull(commentId)
                    comment shouldNotBe null
                    comment?.content shouldBe "댓글 내용"
                    comment?.createdBy shouldBe "댓글 생성자"
                }
            }
            When("게시글이 존재하지 않으면") {
                then("게시글이 찾을 수 없다라는 예외가 발생한다.") {
                    shouldThrow<PostNotFoundException> {
                        commentService.createComment(
                            postId = Generators.timeBasedGenerator().generate(),
                            requestDto = CommentCreateRequestDto(content = "댓글 내용", createdBy = "댓글 생성자"),
                        )
                    }
                }
            }
        }

        given("댓글 수정 시") {
            val post =
                postRepository.save(
                    Post(title = "게시글 제목", content = "게시글 내용", createdBy = "게시글 생성자"),
                )
            val saved =
                commentRepository.save(
                    Comment(post = post, content = "댓글 내용", createdBy = "댓글 생성자"),
                )
            When("인풋이 정상적으로 들어오면") {
                val updatedId =
                    commentService.updateComment(
                        id = saved.id,
                        requestDto = CommentUpdateRequestDto(content = "수정된 댓글 내용", updatedBy = "댓글 생성자"),
                    )
                then("댓글이 정상적으로 수정됨을 확인한다.") {
                    saved.id shouldBe updatedId

                    val updated = commentRepository.findByIdOrNull(updatedId)
                    updated shouldNotBe null
                    updated?.content shouldBe "수정된 댓글 내용"
                    updated?.updatedBy shouldBe "댓글 생성자"
                }
            }
            When("작성자와 수정자가 다르면") {
                then("댓글을 수정할 수 없다라는 예외가 발생한다.") {
                    shouldThrow<CommentNotUpdatableException> {
                        commentService.updateComment(
                            id = saved.id,
                            requestDto =
                                CommentUpdateRequestDto(
                                    content = "수정된 댓글 내용",
                                    updatedBy = "작성자와 다른 수정자",
                                ),
                        )
                    }
                }
            }
        }

        given("댓글 삭제시") {
            val post =
                postRepository.save(
                    Post(title = "게시글 제목", content = "게시글 내용", createdBy = "게시글 생성자"),
                )
            val saved =
                commentRepository.save(
                    Comment(post = post, content = "댓글 내용", createdBy = "댓글 생성자"),
                )
            When("인풋이 정상적으로 들어오면") {
                val deletedId = commentService.deleteComment(saved.id, "댓글 생성자")
                then("댓글이 정상적으로 삭제됨을 확인한다.") {
                    deletedId shouldBe saved.id
                    postRepository.findByIdOrNull(deletedId) shouldBe null
                }
            }

            val saved2 =
                commentRepository.save(
                    Comment(post = post, content = "댓글 내용", createdBy = "댓글 생성자"),
                )
            When("작성자와 삭제자가 동일하지 않으면") {
                then("삭제할 수 없는 게시물 입니다 예외가 발생한다.") {
                    shouldThrow<CommentNotDeletableException> {
                        commentService.deleteComment(saved2.id, "댓글 생성자와 다른 삭제자")
                    }
                }
            }
        }
    })
