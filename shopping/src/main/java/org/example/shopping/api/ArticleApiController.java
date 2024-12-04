package org.example.shopping.api;

import org.example.shopping.dto.ArticleForm;
import org.example.shopping.entity.Article;
import org.example.shopping.repository.ArticleRepository;
import org.example.shopping.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class ArticleApiController {

    @Autowired
    public ArticleService articleService;
    //이 방법의 의존성 주입은 필드주입 이다. 이 필드가 데이터베이스임. 서비스 객체 주입
    //원래는 Articlerepository를 사용해서 리퍼지토리에 직접 접근하는 방식으로 구현했는데 이제는 서비스 계층을 따로 만들어서 이를 통해 접근하는 방식으로 코드를 수정.

    //GET
    @GetMapping("/api/articles")//전체 글 리스트
    public List<Article> index(){//Article 타입 리스트를 받아옴. 이걸로 글 리스트 생성.
        return articleService.index();
    }
    @GetMapping("/api/articles/{id}")//개별 글 페이지
    public Article show(@PathVariable long id){//Article 타입의 엔티티 하나만 가져옴. 이걸로 페이지 생성.
        return articleService.show(id);//데이터가 찾아지지 않으면 null값을 줌.
    }
//    //POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){ //ResponseEntity라는 클래스 내부에 나중에 정의하기로 한 타입이 있는데 이 타입을 우리는 Article 로 입력하기로 함.
        Article created = articleService.create(dto); //서비스 클래스의  create 메서드에 dto 를 주고 엔티티를 반환받음.
        return (created !=null) ?//반환받은 엔티티에 null값이 아닌 실제 엔티티가 들어있다면
                ResponseEntity.status(HttpStatus.OK).body(created) ://이녀석을
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //아니라면 이걸 반환함. 이때 엔티티 응답을 반환하므로 반환형에 ResponseEntity를 줌. 해당 클래스는 제네릭을 사용하므로 어떤 타입인지를 명시하기 위해 <Article>도 붙여줌.
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>>TransactionTest(@RequestBody List<ArticleForm> dtos)
    {
        List<Article> createdList = articleService.createArticles(dtos);
        return(createdList != null)?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //PATCH

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable long id, @RequestBody ArticleForm dto){
        Article updated = articleService.update(id,dto);
        return (updated != null)?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
//    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable long id){
        Article deleted = articleService.delete(id);
        return (deleted != null)?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
