package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.comment.CommentListResponseDto;
import com.sparta.seoulmate.dto.comment.CommentRequestDto;
import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.Comment;
import com.sparta.seoulmate.entity.Post;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.entity.UserRoleEnum;
import com.sparta.seoulmate.repository.CommentRepository;
import com.sparta.seoulmate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        // 로그인한 사용자가 댓글 작성자이거나 관리자인 경우
        if (user.getId().equals(comment.getAuthor().getId()) || user.getRole() == UserRoleEnum.ADMIN) {
            comment.updateContent(commentRequestDto.getContent());
        } else {
            throw new IllegalStateException("댓글 수정 권한이 없습니다.");
        }

        return CommentResponseDto.of(comment);
    }

    // Comment 삭제
    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        // 로그인한 사용자가 댓글 작성자이거나 관리자인 경우
        if (user.getId().equals(comment.getAuthor().getId()) || user.getRole() == UserRoleEnum.ADMIN) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalStateException("댓글 삭제 권한이 없습니다.");
        }
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
