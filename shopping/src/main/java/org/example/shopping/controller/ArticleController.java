package org.example.shopping.controller;

import org.example.shopping.api.CommentApiController;
import org.example.shopping.dto.ArticleForm;
import org.example.shopping.dto.CommentDto;
import org.example.shopping.entity.Article;
import org.example.shopping.repository.ArticleRepository;
import org.example.shopping.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new") //<a href="">ddd</a> 라는 버튼을 누르면 a태그는 지정된 url로 get 요청을 보냄. 폼 제출은 post 요청. html규칙임.
    public String newArticleForm(){
        return "articles/new";//모든 mustache 경로의 시작점은 templates인가보다.
    }

    Model logIAO(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());
        model.addAttribute("isAuthenticated", isAuthenticated);
        return model;
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){

        //System.out.println(form.toString());
        log.info(form.toString());
        Article article = form.toEntity(); //폼 데이터를 Article 형태의 엔티티에 담음.
        Article saved = articleRepository.save(article);//인터페이스를 사용해서 동일한 이름의 함수를 다른 객체에서 사용가능.
        //즉 article 객체에 다른 인스턴스, 예를 들면 anotherForm.toEntity()를 넣어도 같은 인터페이스를 오버라이딩 했으면 아래 코드는 변경하지 않고 사용 가능.
        //System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id" + id);
        Article articleEntity  = articleRepository.findById(id).orElse(null);//id변수를 사용해 리퍼지토리로 데이터베이스에 접근해 엔티티를 찾아 리턴.
        //모델에 데이터 등록. 모델 변수를 뷰 페이지에서 사용하기 때문에 엔티티 내부 값을 모델에 등록해야함.
        List<CommentDto> commentDtos = commentService.comments(id);
        model= logIAO(model);
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentDtos",commentDtos);
        return"articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        model= logIAO(model);
        List<Article> articleEntityList = articleRepository.findAll();
        model.addAttribute("articleList",articleEntityList);
        return"articles/index";
    }

    @GetMapping("/articles/{id}/edit")//변수를 뷰 페이지에서 사용할 때는 {{id}}를 사용하고 컨트롤러에서 url변수로 사용할 때는 {id}사용
    public String edit(Model model, @PathVariable Long id){//경로변수 id
        model= logIAO(model);
        //수정할 데이터 가져오기. 이때 경로변수 id를 사용해서 특정 id의 엔티티를 가져옴. 이를 이용해 개별 뷰 페이지 생성 가능.
        Article articleEntity = articleRepository.findById(id).orElse(null);
        //모델 변수에 articleEntity등록
        model.addAttribute("article",articleEntity);
        //뷰 페이지 설정하기
        return"articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        Article articleEntity = form.toEntity();//view화면에서 데이터를 받아와 entity로 변환.
        log.info(articleEntity.toString());
        Article target;
        target= articleRepository.findById(articleEntity.getId()).orElse(null);//DB에서 폼속 id를 통해 수정할 데이터의 id에 접근.
        if(target!=null){
            articleRepository.save(articleEntity);//수정할 데이터의 id를 통해 DB검색후 그 id와 일치하는 데이터 존재시 수정페이지의 데이터를 덮어씀..
        }
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr)
    {
        log.info("삭제요청이 들어왔습니다");
        //1.삭제 대상 가져오기
        Article target= articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2.대상 엔티티 삭제하기
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다.");
        }
        //3.리디렉션 하기
        return "redirect:/articles";
    }

}
