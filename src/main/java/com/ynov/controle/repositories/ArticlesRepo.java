package com.ynov.controle.repositories;

import com.ynov.controle.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticlesRepo extends JpaRepository<Article, Long> {
    List<Article> findAll();
    Optional<Article> findById(Long id);
    Article save(Article article);
    void deleteById(Long id);
}
