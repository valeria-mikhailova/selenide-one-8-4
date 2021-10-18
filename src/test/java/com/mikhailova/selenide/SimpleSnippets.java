package com.mikhailova.selenide;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.CollectionCondition.sizeLessThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

// this is not a full list, just the most common
public class SimpleSnippets {

    void browser_command_examples() {

        // команды браузера

        open("https://google.com");
        open("/customer/orders");
        open("/", AuthenticationType.BASIC, "user", "password");

        Selenide.back();

        Selenide.clearBrowserCookies(); // для очистки куки, чтобы, например, перелогиниться
        Selenide.clearBrowserLocalStorage(); // для очистки LocalStorage, чтобы, например, перелогиниться

        Selenide.confirm(); // OK in alert dialogs
        Selenide.dismiss(); // Cancel in alert dialogs

        Selenide.closeWindow(); // close active tab
        Selenide.closeWebDriver(); // close browser completely

        Selenide.switchTo().frame("new"); // когда нужно попасть во фрейм
        Selenide.switchTo().defaultContent(); // когда нужно вернуться назад из фрейма

        Selenide.switchTo().window("The Internet"); // перейти на другую вкладку
    }

    void selectors_examples() {
        $("div").click(); // ищет процессы из селектора
        element("div").click(); // синоним предыдущего

        $("div", 2).click(); // the third div

        $x("//h1/div").click();
        $(byXpath("//h1/div")).click();

        $(byText("full text")).click(); //бай текст - ищет полный текст
        $(withText("ull tex")).click(); // виз текст - ищет кусочек текста


       // навигация
        $("").parent();       // find parent element
        $("").sibling(2);     // find down third sibling element, на одном уровне (братья сёстры) - следующий за ним
        $("").preceding(0);   // find up first sibling element, на одном уровне (братья сёстры) - предыдущий
        $("").closest("div"); // find up the tree the next element with tag

        $("div").$("h1").find(byText("abc")).click();

        // very optional
        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();

        $(byId("mytext")).click();
        $("#mytext").click();

        $(byClassName("red")).click();
        $(".red").click();
    }

    void actions_examples() {
        $("").click();
        $("").doubleClick();
        $("").contextClick();

        $("").hover(); // подвести мышку

        $("").setValue("text"); // очищает и пишет текст
        $("").append("text"); // добавляет текст. Не очищает
        $("").append(""); // очистить
        $("").clear(); // очистить
        //
        $("div").sendKeys("c"); // hotkey c on element
        actions().sendKeys("c").perform(); //hotkey c on whole application
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // Ctrl + F
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f"));

        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();

        // complex actions with keybord and mouse, example
        actions().moveToElement($("div")).clickAndHold().moveByOffset(300, 200).release().perform();
        $("1").dragAndDropTo($("2")); // элемент один перетащить на элемент 2

        // old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_option");
        $("").selectRadio("radio_options");

    }

    void assertions_examples() {
        $("").shouldBe(visible); // должен быть видимым
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc")); // должен иметь текст
        $("").shouldNotHave(text("abc"));
        $("").should(appear); // должен появиться
        $("").shouldNot(appear);


        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30)); // если что-то длится более 4 сек, то есть какие-то проблемы
        // , но на тестировочной среде норма может быть и 10 сек
        $("").waitUntil(visible, 30000);  //is deprecated не рекомендуемый к использованию

    }

    void conditions_examples() {
        // то, что пишется в assertions = visible, hidden и т.д.
        $("").shouldBe(visible);
        $("").shouldBe(hidden);

        $("").shouldHave(text("abc"));
        $("").shouldHave(exactText("abc")); // exactText - точный текст, совпадает один в один
        $("").shouldHave(textCaseSensitive("abc")); // должен совпадать регистр
        $("").shouldHave(exactTextCaseSensitive("abc")); // должен совпадать текст и регистр
        $("").should(matchText("[0-9]abc$"));

        $("").shouldHave(cssClass("red")); // есть ли тут класс red
        $("").shouldHave(cssValue("font-size", "12")); // проверить, например, цвет элемента

        $("").shouldHave(value("25")); // для проверки текста в инпуте
        $("").shouldHave(exactValue("25")); // exactValue - проверяет точное значение
        $("").shouldBe(empty);

        $("").shouldHave(attribute("disabled")); // проверка атрибутов
        $("").shouldHave(attribute("name", "example"));
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));

        $("").shouldBe(checked); // for checkboxes

        // Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist);

        // Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled);
        $("").shouldBe(enabled);
    }

    void collections_examples() {
        // $ - один эелемент
        // $$ - два элемента

        $$("div"); // does nothing! ничего не ищет, пока не совершено действие

        // selections
        $$("div").filterBy(text("123")).shouldHave(size(1)); // filterBy - ищет из div'ов тех, в которых есть текст "123"
        $$("div").excludeWith(text("123")).shouldHave(size(1)); // excludeWith - исключить те элементы, у которых есть текст "123"

        $$("div").first().click();
        elements("div").first().click();
        // $("div").click();
        $$("div").last().click();
        $$("div").get(1).click(); // the second! (start with 0)
        $("div", 1).click(); // same as previous
        $$("div").findBy(text("123")).click(); //  finds first

        // assertions
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma")); // проверяет, что в коллекции ровно три элемента у которого есть текст. Alfa2 - подойдёт
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma")); // проверяет, что в коллекции ровно три элемента с именно таким текстом

        $$("").shouldHave(textsInAnyOrder("Beta", "Gamma", "Alfa"));
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Gamma", "Alfa"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text

        $$("").shouldHave(sizeGreaterThan(0)); // в коллекции есть элементы, в данном случае больше нуля
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3)); // максимум 3 элемента
        $$("").shouldHave(sizeLessThanOrEqual(2));

    }

    void file_operation_examples() throws FileNotFoundException {

        File file1 = $("a.fileLink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        // don't forget to submit!
        $("uploadButton").click();
    }

    void javascript_examples() {
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);
    }
}
