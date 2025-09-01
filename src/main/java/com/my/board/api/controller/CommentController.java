package com.my.board.api.controller;

import com.my.board.api.exception.ApiResponse;
import com.my.board.api.exception.BadRequestException;
import com.my.board.api.service.CommentService;
import com.my.board.dto.CommentDto;
import com.my.board.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
// 모든 메서드가 @ResponseBody를 자동으로 갖도록 만듦
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("api/exception")
    public String exHandler(){
        throw new BadRequestException("Test");
    }


    // 1. 댓글 조회
    // "/api/comments/{commentId}"
    @GetMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentDto> commentSearch(
            @PathVariable("commentId")Long commentId) {
        Map<String, Object> result = commentService.findComment(commentId);
        CommentDto dto = (CommentDto) result.get("dto");
        if (ObjectUtils.isEmpty(dto)) {
            String message = "댓글 조회 실패";
            throw new BadRequestException(message);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }
    //2번 댓글 생성api
    // api/articles/{articleId}/Comments
    @PostMapping("/api/articles/{articleId}/Comments")
    public ResponseEntity<?> commentCreate(
            @PathVariable("articleId") Long articleId,
            @RequestBody CommentDto dto
    ) {
         commentService.insertComment(articleId, dto);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(ApiResponse.builder().message("떗글생성성공").build());
    }
}