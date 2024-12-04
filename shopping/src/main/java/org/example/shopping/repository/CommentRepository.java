package org.example.shopping.repository;

import org.example.shopping.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    //특정 게시물의 모든 댓글 조회
    @Query(value="SELECT * FROM comment WHERE article_id=:articleId",nativeQuery=true)//엔티티로 찾네?
    //sql에서 비교연산자로 = 사용. article_id값이 articleid변수의 값과 같으면 데이터 선택. :는 파라미터 플레이스홀더로 프로그램 실행시간때 동적으로 값을 치환.
    List<Comment> findByArticleId(Long articleId);

    //특적 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);



}
