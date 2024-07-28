package com.example.board

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

data class Person(
    val name: String,
    val age: Int,
) {
    fun canVote(): Boolean = age >= 18
}

class PersonTest :
    StringSpec({

        "person with age >= 18 should be able to vote" {
            val person = Person("Alice", 20)
            person.canVote() shouldBe true
        }

        "person with age < 18 should not be able to vote" {
            val person = Person("Bob", 17)
            person.canVote() shouldBe false
        }
    })
