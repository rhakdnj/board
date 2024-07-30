package com.example.board.exception

abstract class CommentException(
    message: String,
) : RuntimeException(message)

class CommentNotFoundException(
    message: String = "댓글을 찾을 수 없습니다.",
) : CommentException(message)

class CommentNotUpdatableException(
    message: String = "댓글을 수정할 수 없습니다.",
) : CommentException(message)

class CommentNotDeletableException(
    message: String = "댓글을 삭제할 수 없습니다.",
) : CommentException(message)
