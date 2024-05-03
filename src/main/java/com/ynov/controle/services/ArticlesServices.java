package com.ynov.controle.services;

import com.ynov.controle.models.Article;

import java.util.Optional;

public interface ArticlesServices {
    Object getAllArticles();

    Optional<Article> getArticleById(Long id);
}
