package com.mikhailova.homework;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class DragAndDrop {

    @Test
    void SwapTriangles() {
        // открыть страницу https://the-internet.herokuapp.com/drag_and_drop
        open("https://the-internet.herokuapp.com/drag_and_drop");

        // перенести прямоугольник А на место В
        $("#column-a").dragAndDropTo($("#column-b"));

        // проверить, что прямоугольник A теперь слева
        $("#column-a").shouldHave(cssValue("float", "left"));
    }

}
