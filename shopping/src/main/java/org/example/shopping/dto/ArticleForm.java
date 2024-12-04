package org.example.shopping.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.shopping.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class ArticleForm {
    private Long id;
    private String title;
    private String content;
    private Long userId;

    public Article toEntity() {
        return new Article(id,title,content,null);
    }
    public static ArticleForm createArticleForm(Article article) {//정적 메서드기 때문에 따로 객체 생성을 하지 않아도 메서드 사용이 가능함.
        //유틸리티 메서드에 많이 사용하는 방식. ex) Util.Math 같은거
        return new ArticleForm(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getUser().getId()
        );
    }
}
