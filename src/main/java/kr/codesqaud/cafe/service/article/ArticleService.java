package kr.codesqaud.cafe.service.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.dto.article.ArticleForm;
import kr.codesqaud.cafe.domain.dto.article.ArticleTimeForm;
import kr.codesqaud.cafe.domain.dto.article.ArticleUpdateForm;
import kr.codesqaud.cafe.domain.vo.PageForm;
import kr.codesqaud.cafe.repository.article.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article add(ArticleForm form, String loginUserId) {
        Article article = new Article(loginUserId, form.getTitle(), form.getContents());
        return articleRepository.save(article);
    }

    public List<ArticleTimeForm> findArticles(PageForm pageForm) {
        // DTO가 가지고 있는 변수로 필터링하는 과정
        return articleRepository.findAll(pageForm).stream()
                .map(ArticleTimeForm::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public ArticleTimeForm findArticleId(Long id) {
        Article article = findArticle(id);
        return ArticleTimeForm.from(article);
    }

    public ArticleUpdateForm findUpdate(Long id) {
        Article article = findArticle(id);
        return ArticleUpdateForm.from(article);
    }

    public void updateArticle(Long id, ArticleUpdateForm updateForm) {
        // ArticleUpdateForm의 정보들을 Article(Entity)에 덮어씌우기
        Article originArticle = findArticle(id);
        originArticle.setTitle(updateForm.getTitle());
        originArticle.setContents(updateForm.getContents());
        articleRepository.update(id, originArticle);
    }

    private Article findArticle(Long id) {
        // Optional을 스트림으로 처리
        return articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("이 아이디를 찾을 수 없어: " + id));
    }

    public void delete(Long id) {
        articleRepository.deleteArticle(id);
    }

    public Long countArticles() {
        return articleRepository.countUndeletedArticles();
    }
}
