package com.mikhailova.github;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class SimpleSelenideSearch {
    @Test
    void chouldFindSelenideRepositoryPage(){
        // открыть страницу github.com
        open("https://github.com");
        // ввести в поле поиска selenide
        // нажать Enter
        $("[data-test-selector=nav-search-input]").setValue("selenide").pressEnter();
        // нажать на линк от первого результата поиска
        $$(".repo-list li").first().$("a").click();
        // можно оптимизировать код так: $(".repo-list li").$("a").click();
        // или так:
        // var listOfRepositories=$$(".repo-list li");
        // listOfRepositories.first().$("a").click();


        // check: в заголовке встречается selenide/selenide
        $("h1").should(Condition.text("selenide / selenide"));


        // Общая структура:
        // arrange - подготовка
        // act - действие
        // assert - проверка
        // act - действие
        // assert - проверка

    }
}
