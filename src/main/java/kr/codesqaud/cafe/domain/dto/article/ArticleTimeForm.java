package kr.codesqaud.cafe.domain.dto.article;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.User;

import java.time.format.DateTimeFormatter;

public class ArticleTimeForm {
    private Long id;
    private String userId;
    private String title;
    private String contents;
    private String currentTime;
    // count(*)
    private Long replyCount;

    public ArticleTimeForm() {
    }

    // 정적 팩토리 메서드
    // form에서만 쓰이니까 private으로 보이지 않게 (바깥에서 주입할 일이 없으니까)
    private ArticleTimeForm(Long id, String userId, String title, String contents, String currentTime, Long replyCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.contents = contents;
        this.currentTime = currentTime;
        this.replyCount = replyCount;
    }

    // static을 안 붙이면 객체를 또 만들어야 하니까
    // 인스턴스면 또 객체를 안들어서 return new가 필요 없음
    public static ArticleTimeForm from(Article article) {
        return new ArticleTimeForm(article.getId(), article.getUserId(), article.getTitle(), article.getContents(), article.getCurrentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), article.getReplyCount());
    }

    public String getUserId() {
        return userId;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Long replyCount) {
        this.replyCount = replyCount;
    }
}
