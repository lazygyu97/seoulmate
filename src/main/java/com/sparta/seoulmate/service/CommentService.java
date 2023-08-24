package com.sparta.seoulmate.service;

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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // Comment 생성
    public CommentResponseDto createComment (Long postId, User user, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Comment comment = Comment.builder()
                .author(user)
                .post(post)
                .content(commentRequestDto.getContent())
                .build();

        Comment savedComment = commentRepository.save(comment);
        // 작성한 댓글 저장

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
    public List<CommentResponseDto> getComments() {
        List<CommentResponseDto> commentResponseDtos = commentRepository.findAllByOrderByModifiedAtDesc()
                .stream().map(CommentResponseDto::of).collect(Collectors.toList());
        return commentResponseDtos;
    }

    // Comment 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, User user, CommentRequestDto commentRequestDto) {
        Comment comment = findComment(commentId);

        if(!comment.getAuthor().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }

        comment.setContent(commentRequestDto.getContent());

        return CommentResponseDto.of(comment);
    }

    // Comment 삭제
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        if(!comment.getAuthor().getId().equals(user.getId())) {
            throw new RejectedExecutionException();
        }
        commentRepository.delete(comment);
    }

    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
