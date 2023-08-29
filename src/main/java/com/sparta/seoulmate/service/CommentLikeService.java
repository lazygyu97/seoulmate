package com.sparta.seoulmate.service;

import com.sparta.seoulmate.dto.comment.CommentResponseDto;
import com.sparta.seoulmate.entity.Comment;
import com.sparta.seoulmate.entity.CommentLike;
import com.sparta.seoulmate.entity.User;
import com.sparta.seoulmate.repository.CommentLikeRepository;
import com.sparta.seoulmate.repository.CommentRepository;
import com.sparta.seoulmate.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto onClickCommentLike(UserDetailsImpl userDetails, Long commentId) throws Exception {

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

    public void deleteCommentLike(UserDetailsImpl userDetails, Long commentId) {
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
}
