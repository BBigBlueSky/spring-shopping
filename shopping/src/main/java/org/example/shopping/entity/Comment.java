package org.example.shopping.entity;

import org.example.shopping.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;//long은 원시타입이라 null할당이 불가능하나 Long은 참조타입이라 가능함. 처음 comment를 생성했을 때는 id내부에 값이 없어 null이 할당되어야함. 따라서 long 이 아닌 Long이 되어야함.
    @ManyToOne//이 속성으로 해당 자료형의 이름을 가진 엔티티의 기본키에 연결함?
    @JoinColumn(name="article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        //예외발생
        if(dto.getId()!=null)
        {
            throw new IllegalArgumentException("아이디가 없어야함");//만일 리포지토리에 생성하지도 않은 게시글에 id가 존재하면 이건 잘못된거.
        }
        if(dto.getArticleId()!= article.getId())
        {
           throw new IllegalArgumentException("아이디가 다름");//생성하려는 댓글의 원 게시물 id가 리포지토리에 존재하는 게시글의 아이디와 다르다면 잘못된거.
        }
        //엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,//Comment 엔티티에서 aritcleId는 article을 외래키로 받기때문에 articleid가 아닌 article을 인자로 주는것.
                dto.getNickname(),
                dto.getBody()
        );
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", article=" + article +
                ", nickname='" + nickname + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public void patch(CommentDto dto) {
        if(this.id!=dto.getId())
        {
            throw new IllegalArgumentException("잘못된 id입력.");
        }
        if(dto.getNickname()!=null)
        {
            this.nickname = dto.getNickname();
        }
        if(dto.getBody()!=null)
        {
            this.body = dto.getBody();
        }
    }
}
