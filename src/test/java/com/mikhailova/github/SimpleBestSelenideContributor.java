package com.mikhailova.github;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SimpleBestSelenideContributor {

    @Test
    void andreiSolntsevShouldBeTheBestContributor() {
        // открыть страничку selenide
        open("https://github.com/selenide/selenide");
        // подвести мышку к первому элементу в области Contributors
        $(".BorderGrid").$(byText("Contributors"))
                .closest("div").$("ul li").hover();
        // проверка: в появившемся окошечке (overlay) text = Andrei Solntsev
        $$(".Popover-message").findBy(visible).shouldHave(text("Andrei Solntsev"));

        // Чтобы страничка зафризилась в консоли в враузере ввести:
        // setTimeout(function(){debugger},5000);


    }

}
