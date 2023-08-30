package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.comment.CommentListResponseDto;
import com.sparta.seoulmate.dto.comment.CommentRequestDto;
import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.*;
import com.sparta.seoulmate.repository.CommentLikeRepository;
import com.sparta.seoulmate.repository.CommentRepository;
import com.sparta.seoulmate.repository.PostRepository;
import com.sparta.seoulmate.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

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

    @Transactional
    public CommentResponseDto likeComment(UserDetailsImpl userDetails, Long commentId) throws Exception {

        // 토큰 체크
        User user = userDetails.getUser();
        if (user == null) {
            throw new IllegalStateException("사용자 정보를 찾을 수 없습니다.");
        }

        // 좋아요 누른 댓글 조회
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다."));

        // 좋아요 누른 댓글이 본인 댓글이면 좋아요 불가능
        if (user.getId().equals(comment.getAuthor().getId())) {
            throw new IllegalArgumentException("자신의 댓글에는 좋아요 할 수 없습니다.");
        }

        // 중복 좋아요 방지
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(comment.getId(), user.getId());
        if (commentLike != null){
            throw new IllegalArgumentException("이미 좋아요를 하고 있습니다.");
        }

        // commentLikeRepository DB저장
        CommentLike sevedCommentLike = CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
        commentLikeRepository.save(sevedCommentLike);

        // CommentResponseDto 생성
        CommentResponseDto commentResponseDto = CommentResponseDto.of(comment);

        return commentResponseDto;
    }

    public void deleteLikeComment(UserDetailsImpl userDetails, Long commentId) {
        // 토큰 체크
        User user = userDetails.getUser();
        if (user == null) {
            throw new IllegalStateException("사용자 정보를 찾을 수 없습니다.");
        }

        // 좋아요 누른 댓글 조회 (좋아요가 눌려 있지 않으면 삭제 대상이 아님)
        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, user.getId());
        if (commentLike == null){
            throw new IllegalStateException("이미 좋아요를 하고 있지 않습니다.");
        }

        // 좋아요 누른 본인이거나 admin 경우만 삭제 가능
        if (user.getId().equals(commentLike.getUser().getId())) {
            commentLikeRepository.delete(commentLike);
        } else {
            throw new IllegalStateException("좋아요 취소 권한이 없습니다.");
        }
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글이 존재하지 않습니다."));
    }
}
