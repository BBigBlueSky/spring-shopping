package org.example.shopping.api;

import org.example.shopping.dto.CommentDto;
import org.example.shopping.repository.CommentRepository;
import org.example.shopping.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    CommentService commentService;
    //1.댓글 조회

    @GetMapping("/api/articles/{articleId}/comments")//테스트 할 떄 https 가 아니라 http 를 사용해야함
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId)
    {
        List<CommentDto> dtos = commentService.comments(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);//예외처리 생략. 보통 삼항 연산자가 아닌 예외처리로 다룸.
    }
    //2.댓글 작성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto)//request body로 데이터를 받음
    {
        CommentDto createdDto = commentService.create(articleId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }
    //3.댓글 수정
    @PatchMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,
                                             @RequestBody CommentDto dto)
    {
        //서비스 위임
        CommentDto updatedDto = commentService.update(id,dto);

        //결과 제출
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    //4.댓글 삭제
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {
        //서비스 위임
        CommentDto deletedDtd = commentService.delete(id);
        //결과 제출
        return ResponseEntity.status(HttpStatus.OK).body(deletedDtd);
    }
}
