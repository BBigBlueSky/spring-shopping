package org.example.shopping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.shopping.dto.ArticleForm;
import org.example.shopping.dto.CommentDto;
import org.example.shopping.dto.UserDto;

@AllArgsConstructor
@ToString
@Entity //1. 엔티티 선언
@Getter//자바 빈에 의해 getId와 같은 메서드 자동 생성. 표현 규약이 있음. 그걸 참조.
public class Article {
    @Id //3. id값 선언
    @GeneratedValue//4.id 값 자동 생성 선언.
    private Long id;

    //2. Article 엔티티의 필드(열) 선언
    @Column
    private String title;
    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=true)
    private User user;

    public Article() {
    }

    public static Article createArticle(ArticleForm dto, User user) {
        //예외발생
        if (dto.getId() != null) {
            throw new IllegalArgumentException("아이디가 없어야함");//만일 리포지토리에 생성하지도 않은 게시글에 id가 존재하면 이건 잘못된거.
        }
        if (dto.getUserId() != user.getId()) {
            throw new IllegalArgumentException("아이디가 다름");//생성하려는 댓글의 원 게시물 id가 리포지토리에 존재하는 게시글의 아이디와 다르다면 잘못된거.
        }
        //엔티티 생성 및 반환
        return new Article(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),//Comment 엔티티에서 aritcleId는 article을 외래키로 받기때문에 articleid가 아닌 article을 인자로 주는것.
                user
        );
    }


    public void patch(Article article) {//업데이트를 위해 들어온 값에 특정 필드 값만 수정하기 위함. 모두 변경을 막기위함.
        //예를 들어 content는 수정했는데 title은 수정을 안했다 가정하자. 이 함수가 없으면 title값은 null이 되어버림.
        //이때 if문을 통해 업데이트 할 내용이 없는 필드는 그냥 그대로 두는 것.
        if(article.title!=null)
            this.title = article.title;
        if(article.content!=null)
            this.content = article.content;
    }

    //toString은 객체의 정보를 문자열로 제공한다. 디버깅 하기 편해진다.
}
