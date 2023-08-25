package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.comment.CommentListResponseDto;
import com.sparta.seoulmate.dto.comment.CommentRequestDto;
import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.Comment;
import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.repository.CommentRepository;
import com.sparta.seoulmate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // Comment 생성
    public CommentResponseDto createComment (Long postId, User user, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Comment comment = commentRequestDto.toEntity(post, user);

        // 작성한 댓글 저장
        Comment savedComment = commentRepository.save(comment);

        return CommentResponseDto.of(savedComment);
    }

    // Comment 단건 조회
    @Transactional
    public CommentResponseDto getComment(Long commentId) {
        Comment comment = findComment(commentId);
        return CommentResponseDto.of(comment);
    }

    // Comment 다건 조회
    @Transactional
    public CommentListResponseDto getComments() {
        List<Comment> commnetList = commentRepository.findAll();
        return CommentListResponseDto.of(commnetList);
    }

    // Comment 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, User user, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(commentId);

        if(!comment.getAuthor().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        comment.updateContent(commentRequestDto.getContent());

        return CommentResponseDto.of(comment);
    }

    // Comment 삭제
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        if(!comment.getAuthor().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }
        // or 연산자 써서 사용자 계정이 admin일 때 삭제할 수 있도록
        commentRepository.delete(comment);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
