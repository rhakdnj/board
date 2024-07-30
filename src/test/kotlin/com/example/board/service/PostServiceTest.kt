package com.example.board.service

import com.example.board.domain.Comment
import com.example.board.domain.Post
import com.example.board.exception.PostNotDeletableException
import com.example.board.exception.PostNotFoundException
import com.example.board.exception.PostNotUpdatableException
import com.example.board.repository.CommentRepository
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostCreateRequestDto
import com.example.board.service.dto.PostSearchRequestDto
import com.example.board.service.dto.PostUpdateDto
import com.fasterxml.uuid.Generators
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : BehaviorSpec({
        given("게시글 생성 시") {
            When("게시글 인풋이 정상적으로 들어오면") {
                val postId =
                    postService.createPost(
                        PostCreateRequestDto(
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
                    postService.updatePost(
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
                        postService.updatePost(
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
                        postService.updatePost(
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
                val deletedId = postService.deletePost(saved.id, "createdBy")
                then("게시글이 정상적으로 삭제됨을 확인한다.") {
                    deletedId shouldBe saved.id
                    postRepository.findByIdOrNull(deletedId) shouldBe null
                }
            }

            val saved2 = postRepository.save(Post(title = "title", content = "content", createdBy = "createdBy"))
            When("삭제 요청 사용자와 게시글 작성자가 동일하지 않으면") {
                then("삭제할 수 없는 게시물 입니다 예외가 발생한다.") {
                    shouldThrow<PostNotDeletableException> {
                        postService.deletePost(saved2.id, "different createdBy")
                    }
                }
            }
        }

        beforeSpec {
            postRepository.saveAll(
                listOf(
                    Post(title = "title1", content = "content", createdBy = "createdBy1"),
                    Post(title = "title12", content = "content", createdBy = "createdBy1"),
                    Post(title = "title13", content = "content", createdBy = "createdBy1"),
                    Post(title = "title14", content = "content", createdBy = "createdBy1"),
                    Post(title = "title15", content = "content", createdBy = "createdBy1"),
                    Post(title = "title16", content = "content", createdBy = "createdBy16"),
                    Post(title = "title17", content = "content", createdBy = "createdBy17"),
                    Post(title = "title18", content = "content", createdBy = "createdBy18"),
                    Post(title = "title19", content = "content", createdBy = "createdBy19"),
                    Post(title = "title11", content = "content", createdBy = "createdBy11"),
                ),
            )
        }
        given("게시글 상세 조회시") {
            val saved =
                postRepository.save(
                    Post(
                        title = "title",
                        content = "content",
                        createdBy = "createdBy",
                    ),
                )

            When("게시글이 있을 때") {
                val post = postService.getPost(saved.id)
                then("게시글의 내용이 정상적으로 반한됨을 확인한다.") {
                    post.id shouldBe saved.id
                    post.title shouldBe "title"
                    post.content shouldBe "content"
                    post.createdBy shouldBe "createdBy"
                }
            }

            When("게시글이 없을 때") {
                then("게시글을 찾을 수 없다라는 예외가 발생한다.") {
                    shouldThrow<PostNotFoundException> {
                        postService.getPost(
                            Generators.timeBasedEpochRandomGenerator().generate(),
                        )
                    }
                }
            }

            commentRepository.save(Comment(post = saved, content = "댓글 내용1", createdBy = "댓글 작성자"))
            commentRepository.save(Comment(post = saved, content = "댓글 내용2", createdBy = "댓글 작성자"))
            commentRepository.save(Comment(post = saved, content = "댓글 내용3", createdBy = "댓글 작성자"))
            When("댓글 추가시") {
                val post = postService.getPost(saved.id)
                then("댓글이 함께 조회됨을 확인한다.") {
                    post.comments.size shouldBe 3
                    post.comments[0].content shouldBe "댓글 내용1"
                    post.comments[1].content shouldBe "댓글 내용2"
                    post.comments[2].content shouldBe "댓글 내용3"
                }
            }
        }

        given("게시글 목록 조회시") {
            When("게시글 목록이 있을 때") {
                val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto())
                then("게시글 페이지가 반환된다.") {
                    postPage.number shouldBe 0
                    postPage.size shouldBe 5
                    postPage.content.size shouldBe 5
                    postPage.content[0].title shouldContain "title"
                    postPage.content[0].createdBy shouldContain "createdBy"
                }
            }

            When("타이틀로 검색") {
                val postPage =
                    postService.findPageBy(
                        PageRequest.of(0, 5),
                        PostSearchRequestDto(
                            title = "title1",
                        ),
                    )
                then("타이틀에 해당하는 게시글 목록이 반환된다.") {
                    postPage.number shouldBe 0
                    postPage.size shouldBe 5
                    postPage.content.size shouldBe 5
                    postPage.content[0].title shouldContain "title"
                    postPage.content[0].createdBy shouldContain "createdBy"
                }
            }

            When("작성자로 검색") {
                val postPage =
                    postService.findPageBy(
                        PageRequest.of(0, 5),
                        PostSearchRequestDto(
                            createdBy = "createdBy1",
                        ),
                    )
                then("작성자에 해당하는 게시글 목록이 반환된다.") {
                    postPage.number shouldBe 0
                    postPage.size shouldBe 5
                    postPage.content.size shouldBe 5
                    postPage.content[0].title shouldContain "title"
                    postPage.content[0].createdBy shouldBe "createdBy1"
                }
            }
        }
    })
