package org.example.shopping.service;

import org.example.shopping.dto.CommentDto;
import org.example.shopping.entity.Article;
import org.example.shopping.entity.Comment;
import org.example.shopping.repository.ArticleRepository;
import org.example.shopping.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.example.shopping.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    //게시글이 있어야 댓글 조회가 가능. comment 데이터 베이스 테이블에서 articleid를 외래키로 넣어뒀기 때문.
    @Autowired
    ArticleRepository articleRepository;


    public List<CommentDto> comments(Long articleId) {
//        //1. 댓글 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        //2  엔티티->DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for(int i = 0;i<comments.size();i++)//엔티티 수 만큼 반복.
//        {
//            Comment c = comments.get(i);//엔티티List에서 i번째 엔티티 빼오기.
//            CommentDto dto = CommentDto.createCommentDto(c);//c를 통해 dto 만들기.
//            dtos.add(dto);//dto 리스트에 저장.
//        }
        //3. 결과 반환
        return commentRepository.findByArticleId(articleId).//dtos
                stream().
                map(comment->CommentDto.createCommentDto(comment)).//스트림의 각 요소인comment를 매개변수로 create 함수 사용.
                collect(Collectors.toList());//스트림으로 변경한 후 처리함으로써 코드의 가독성을 높힘.
    }

    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        //1. 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId).orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패" + "대상 댓글이 없습니다."));
        //2. 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto,article);
        //3. 댓글 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);
        //4. DTO로 변환해 반환
        return CommentDto.createCommentDto(created);
    }//create는 DB내용을 변경하기때문에 실패했을 때 아에 없던일로 만들어야함. 따라서 Transactional 어노테이션 사용

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        //1. 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글 수정 실패"+"대상 댓글이 없습니다."));
        //2. 댓글 수정
        target.patch(dto);
        //3. DB수정
        Comment updated = commentRepository.save(target);
        //4. 댓글 엔티티를 Dto로 변환후 반환
        return CommentDto.createCommentDto(updated);
    }

    public CommentDto delete(Long id) {
        //1. 댓글 조회
        Comment target = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("없는 댓글입니다."));
        //2. 댓글 삭제
        commentRepository.delete(target);
        //3. 삭제한 댓글 DTO 변환 및 반환
        return CommentDto.createCommentDto(target);
    }
}
