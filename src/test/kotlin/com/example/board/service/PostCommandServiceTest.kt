package com.example.board.service

import com.example.board.domain.Post
import com.example.board.exception.PostNotDeletableException
import com.example.board.exception.PostNotFoundException
import com.example.board.exception.PostNotUpdatableException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostCreateDto
import com.example.board.service.dto.PostUpdateDto
import com.fasterxml.uuid.Generators
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostCommandServiceTest(
    private val postCommandService: PostCommandService,
    private val postRepository: PostRepository,
) : BehaviorSpec({
        given("게시글 생성 시") {
            When("게시글 인풋이 정상적으로 들어오면") {
                val postId =
                    postCommandService.createPost(
                        PostCreateDto(
                            title = "title",
                            content = "content",
                            createdBy = "createdBy",
                        ),
                    )
                then("게시글이 정상적으로 생성됨을 확인한다.") {
                    postId shouldNotBe null

                    val post = postRepository.findByIdOrNull(postId)
                    post shouldNotBe null
                    post?.title shouldBe "title"
                    post?.content shouldBe "content"
                    post?.createdBy shouldBe "createdBy"
                }
            }
        }

        given("게시글 수정 시") {
            val saved =
                postRepository.save(
                    Post(
                        title = "title",
                        content = "content",
                        createdBy = "createdBy",
                    ),
                )
            When("게시글 인풋이 정상적으로 들어오면") {
                val updatedId =
                    postCommandService.updatePost(
                        id = saved.id,
                        updateDto =
                            PostUpdateDto(
                                title = "update title",
                                content = "update content",
                                updatedBy = "createdBy",
                            ),
                    )

                then("게시글이 정상적으로 수정됨을 확인한다.") {
                    saved.id shouldBe updatedId

                    val updated = postRepository.findByIdOrNull(updatedId)
                    updated shouldNotBe null
                    updated?.title shouldBe "update title"
                    updated?.content shouldBe "update content"
                    updated?.updatedBy shouldBe "createdBy"
                }
            }

            When("게시글이 없을 때") {
                then("게시글을 찾을 수 없다라는 예외가 발생한다.") {
                    shouldThrow<PostNotFoundException> {
                        postCommandService.updatePost(
                            id = Generators.timeBasedEpochRandomGenerator().generate(),
                            updateDto =
                                PostUpdateDto(
                                    title = "update title",
                                    content = "update content",
                                    updatedBy = "createdBy",
                                ),
                        )
                    }
                }
            }

            When("작성자가 동일하지 않으면") {
                then("수정할 수 없는 게시물 입니다라는 예외가 발생한다.") {
                    shouldThrow<PostNotUpdatableException> {
                        postCommandService.updatePost(
                            id = saved.id,
                            updateDto =
                                PostUpdateDto(
                                    title = "update title",
                                    content = "update content",
                                    updatedBy = "different createdBy",
                                ),
                        )
                    }
                }
            }
        }

        given("게시글 삭제시") {
            val saved =
                postRepository.save(
                    Post(
                        title = "title",
                        content = "content",
                        createdBy = "createdBy",
                    ),
                )
            When("정상 삭제시") {
                val deletedId = postCommandService.deletePost(saved.id, "createdBy")
                then("게시글이 정상적으로 삭제됨을 확인한다.") {
                    deletedId shouldBe saved.id
                    postRepository.findByIdOrNull(deletedId) shouldBe null
                }
            }

            val saved2 = postRepository.save(Post(title = "title", content = "content", createdBy = "createdBy"))
            When("삭제 요청 사용자와 게시글 작성자가 동일하지 않으면") {
                then("삭제할 수 없는 게시물 입니다 예외가 발생한다.") {
                    shouldThrow<PostNotDeletableException> {
                        postCommandService.deletePost(saved2.id, "different createdBy")
                    }
                }
            }
        }
    })
