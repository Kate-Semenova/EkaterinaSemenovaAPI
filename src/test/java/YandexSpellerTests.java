import beans.YandexSpellerAnswer;
import core.YandexSpellerAPI;
import core.YandexSpellerConstants;
import core.YandexSpellerOptions;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static core.Languages.*;
import static core.YandexSpellerAPI.successResponse;
import static core.YandexSpellerErrors.*;
import static core.YandexSpellerOptions.*;
import static core.YandexSpellerParameters.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created by Ekaterina on 11.07.2018.
 */
public class YandexSpellerTests {

    @DataProvider
    public Object[][] mistakes() {

        return new Object[][]{
                {"hello", 0},
                {"Im not gooing too skool becase Ive gradiated from mani eers agou", 9},
                {"ww.qwerty.ru", 1}
        };
    }

    @DataProvider
    public Object[][] options() {

        return new Object[][]{
                {IGNORE_DIGITS, "hell666", 0},
                {IGNORE_URLS, "www.qwerty.ru", 0},
                {IGNORE_CAPITALIZATION, "лондон - столица англии", 0}
        };
    }

    //1
    @Test(description = "Api should return the world in Russian if keyboard had wrong layout")
    public void latinKeybord() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(RU)
                                .text("Ghbdtn")
                                .callApi());
        assertThat(answers.get(0).s.get(0), equalTo("Привет"));
        assertThat(answers.get(0).code, equalTo(ERROR_UNKNOWN_WORD));

    }

    //2
    @Test(description = "Api should return the correct word from the sentence")
    public void wrongWordInTheScentence() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .text("I go for a walk evry day")
                                .callApi());
        assertThat(answers.get(0).s.get(0), equalTo("every day"));
        assertThat(answers.get(0).code, equalTo(ERROR_UNKNOWN_WORD));
        assertThat(answers.get(0).s.size(), equalTo(3));

    }

    //3
    @Test(description = "")
    public void longWordWithFiveMistakes() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(RU)
                                .text("гедраилектрастансия")
                                .callApi());
        assertThat(answers.get(0).s.get(0), equalTo("гидроэлектростанция"));
    }

    //4
    @Test(dataProvider = "mistakes",
            description = "api should return nothing if there are no mistakes")
    public void numberOfMistakes(String text, int answersSize) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .text(text)
                                .callApi());
        assertThat(answers.size(), equalTo(answersSize));

    }

    //5
    @Test(dataProvider = "options",
            description = "api should return nothing if the option IGNORE_DIGITS is turn on")
    public void optionTest(String option, String text, int answersSize) {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .options(option)
                                .text(text)
                                .callApi());
        assertThat(answers.size(), equalTo(answersSize));

    }

    //6
    @Test
    public void ignoreCapitalization() throws UnsupportedEncodingException {

        RestAssured
                .given(YandexSpellerAPI.baseRequestConfiguration())
                .queryParams(TEXT, "масква звонят колокола")
                .queryParams(OPTIONS, YandexSpellerOptions.IGNORE_CAPITALIZATION)
                .queryParams(LANGUAGE, RU)
                .accept(ContentType.JSON)
                .log().everything()
                .get(YandexSpellerConstants.YANDEX_SPELLER_API_URI)
                .prettyPeek()
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .body(Matchers.allOf(
                        Matchers.containsString("\"code\":1")),
                        Matchers.containsString("\"word\":\"\\u043c\\u0430\\u0441\\u043a\\u0432\\u0430\""),
                        Matchers.containsString("\"s\":[\"\\u043c\\u043e\\u0441\\u043a\\u0432\\u0430\",\"\\u043c\\u0430\\u0441\\u0441\\u043a\\u0432\\u0430\"]"))
                .contentType(ContentType.JSON)
                .time(lessThan(20000L)).specification(successResponse());

    }

    //7
    @Test
    public void badRequest() {
        YandexSpellerAPI
                .with()
                .language(INVALID)
                .text("helo")
                .callApi()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //8
    @Test
    public void ukrainian() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(UK)
                                .text("гроши")
                                .callApi());
        assertThat(answers.get(0).s.toString(), equalTo("[гроші, груши, грошей]"));

    }

    //9
    //ignore
    @Test
    public void errorCapitalization() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .text("moscow never sleeps")
                                .callApi());
        assertThat(answers.get(0).code, equalTo(ERROR_CAPITALIZATION));
    }

    //10
    @Test
    public void errorRepeatWord() {
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .text("how are are you")
                                .callApi());
        assertThat(answers.get(0).code, equalTo(ERROR_REPEAT_WORD));
    }
}
