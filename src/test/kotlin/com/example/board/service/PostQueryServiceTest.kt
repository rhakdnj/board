package com.example.board.service

import com.example.board.domain.Post
import com.example.board.exception.PostNotFoundException
import com.example.board.repository.PostRepository
import com.example.board.service.dto.PostSearchRequestDto
import com.fasterxml.uuid.Generators
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest

@SpringBootTest
class PostQueryServiceTest(
    private val postQueryService: PostQueryService,
    private val postRepository: PostRepository,
) : BehaviorSpec({
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
                val post = postQueryService.getPost(saved.id)
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
                        postQueryService.getPost(
                            Generators.timeBasedEpochRandomGenerator().generate(),
                        )
                    }
                }
            }
        }

        given("게시글 목록 조회시 ") {
            When("게시글 목록이 있을 때") {
                val postPage = postQueryService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto())
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
                    postQueryService.findPageBy(
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
                    postQueryService.findPageBy(
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
