package ru.netology;


import lombok.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

import com.github.javafaker.Faker;

public class TestPlan {

    @Test
    public void formTest() {
        open("http://localhost:9999");
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $$("[type='text']").exclude(hidden).first().setValue(validUser.getCity());
        $$("[type='tel']").exclude(hidden).first().doubleClick();
        $$("[type='tel']").exclude(hidden).first().setValue(firstMeetingDate);
        $$("[type='text']").exclude(hidden).last().setValue(validUser.getName());
        $$("[type='tel']").exclude(hidden).last().setValue(validUser.getPhone());
        $(withText("соглашаюсь")).click();
        $(byText("Запланировать")).click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
        $$("[type='tel']").exclude(hidden).first().doubleClick();
        $$("[type='tel']").exclude(hidden).first().setValue(secondMeetingDate);
        $(byText("Запланировать")).click();
        $(byText("Перепланировать")).click();
        $(".notification").shouldBe(appear, Duration.ofSeconds(15));
    }
}
