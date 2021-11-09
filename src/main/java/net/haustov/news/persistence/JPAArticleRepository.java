package net.haustov.news.persistence;

import net.haustov.news.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAArticleRepository extends JpaRepository<Article, Long> {
}
