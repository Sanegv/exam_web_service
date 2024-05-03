package com.ynov.controle.implementations;

import com.ynov.controle.models.Article;
import com.ynov.controle.repositories.ArticlesRepo;
import com.ynov.controle.services.ArticlesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticlesImplem implements ArticlesServices {
    @Autowired
    ArticlesRepo articlesRepo;

    @Override
    public Object getAllArticles() {
        return articlesRepo.findAll();
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articlesRepo.findById(id);
    }

    @Override
    public void deleteArticleById(Long id) {
        articlesRepo.deleteById(id);
    }
}
