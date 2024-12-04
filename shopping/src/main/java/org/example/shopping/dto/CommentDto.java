package org.example.shopping.dto;

import org.example.shopping.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    private Long articleId;
    private String nickname;
    private String body;

    public static CommentDto createCommentDto(Comment comment) {//정적 메서드기 때문에 따로 객체 생성을 하지 않아도 메서드 사용이 가능함.
        //유틸리티 메서드에 많이 사용하는 방식. ex) Util.Math 같은거
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
