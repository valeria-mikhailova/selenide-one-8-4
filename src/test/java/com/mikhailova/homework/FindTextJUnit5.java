package com.mikhailova.homework;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class FindTextJUnit5 {
    @Test
    void FindTextJUnit5inPageSoftAssertions() {
        // открыть страницу github.com
        open("https://github.com");

        // ввести в поле поиска selenide
        // нажать Enter
        $("[data-test-selector=nav-search-input]").setValue("selenide").pressEnter();

        // нажать на линк от первого результата поиска
        $$(".repo-list li").first().$("a").click();

        // перейти в раздел Wiki проекта
        $("#wiki-tab").click();

        // клик на "показать ещё страницы"
        var ShowMorePages = $(".js-wiki-more-pages-link").shouldHave(text("Show 2 more pages…"));
        ShowMorePages.click();

        // открыть страницу SoftAssertions
        $(withText("SoftAssertions")).click();

        // проверить, что внутри есть пример кода для JUnit5
        $(".markdown-body").shouldHave(text("JUnit5"));
        }
}
