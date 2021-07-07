package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendCorrectForm() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Петров Сергей");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + inputDate), Duration.ofSeconds(15));

    }

    @Test
    void errorExpectedWhenInputIncorrectCity() {
        $("[data-test-id='city'] input").setValue("Медвежьегорск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Петров Сергей");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }


    @Test
    void errorExpectedWithIncorrectName() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Niki");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void errorExpectedWhenInputIncorrectPhone() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Петров Сергей");
        $("[data-test-id=phone] input").setValue("+7999-999-99-99");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void errorExpectedWhenUncheckedCheckbox() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Петров Сергей");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }


    @Test
    void errorExpectedWhenEmptyFieldTel() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Петров Сергей");
        $("[data-test-id=phone] input").setValue("");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void errorExpectedWhenEmptyFieldName() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }


    @Test
    void errorExpectedWhenInputIncorrectDate() {
        $("[data-test-id='city'] input").setValue("Петрозаводск");
        String inputDate = LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.uuuu"));
        SelenideElement data = $("[data-test-id=date]");
        data.$("[value]").doubleClick().sendKeys(Keys.BACK_SPACE);
        data.$("[placeholder]").setValue(inputDate);
        $("[data-test-id=name] input").setValue("Петров Сергей");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));


    }
}




