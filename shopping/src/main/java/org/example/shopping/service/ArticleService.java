package org.example.shopping.service;

import org.example.shopping.dto.ArticleForm;
import org.example.shopping.entity.Article;
import org.example.shopping.entity.User;
import org.example.shopping.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
//service, controller, repository 같은것도 빈을 상속받은 어노테이션임. 이런 어노테이션의 종류에 따라 생존주기가 달라짐.
@Slf4j
@Service//2가지 이유로 서비스 객체를 둔다. 1. 모듈화 - 서비스 기능을 따로 만들어 두면 필요한 곳에서 사용가능 2. 보안 - 컨트롤러에서 리퍼지토리 직접 접근시 해커의 공격에 취약함.
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long userid) {
        return articleRepository.findById(userid).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();//데이터를 dto폼 데이터에 담아 받았음. 따라서 DB에서 사용하기 위해 엔티티로 변환해주는것.
        return articleRepository.save(article);
    }

    public Article update(long id, ArticleForm dto) {
        //업데이트의 결과가 어떤지만 넘겨주면 되기 때문에 리턴값이 Article이 아니라 ResponseEntity다. 이 엔티티는 REST컨트롤러의 반환형, 즉 응답용 클래스다.
        Article article = dto.toEntity();//article 필드에는 사용자에게 받아온 정보를줌
        log.info("id:{}, article:{}", id, article.toString());
        Article target = articleRepository.findById(id).orElse(null);//repository에서 id와 일치하는 값이 있는지 찾아봄.
        //잘못된 요청 처리. 만약 target이 없거나, 수정을 요청한 id와 url의 id가 다르다면 오류발생.
        if(target==null||id!=article.getId())
        {
            //400오류코드 전송
            log.info("id:{}, article:{}", id,article.toString());
            return null;
        }
        //정상적인 응답 처리
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    public Article delete(long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null)//리포지토리에 해당 데이터가 없다면 상태코드 전송
        {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    public List<Article> createArticles(List<ArticleForm> dtos) {
        List<Article> articleList = dtos.stream()
                .map(dto->dto.toEntity())
                .collect(Collectors.toList());
        articleList.stream()
                .forEach(article->articleRepository.save(article));//스트림의 각 요소를 article이라는 변수에 할당.
        articleRepository.findById(-1L)
                .orElseThrow(()-> new IllegalStateException("결제 실패!"));
        return articleList;

    }
}
