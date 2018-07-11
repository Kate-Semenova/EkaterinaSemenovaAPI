import beans.YandexSpellerAnswer;
import core.YandexSpellerAPI;
import org.testng.annotations.Test;

import java.util.List;

import static core.Languages.EN;
import static core.Languages.RU;
import static core.YandexSpellerErrors.ERROR_UNKNOWN_WORD;
import static core.YandexSpellerOptions.IGNORE_DIGITS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Ekaterina on 11.07.2018.
 */
public class YandexSpellerTests {

    @Test(description = "Api should return the world in Russian if keyboard had wrong layout")
    public void latinKeybord(){
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

    @Test(description = "Api should return the correct word from the sentence")
    public void wrongWordInTheScentence(){
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

    @Test (description = "")
    public void longWordWithFiveMistakes(){
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(RU)
                                .text("гедраилектрастансия")
                                .callApi());
        assertThat(answers.get(0).s.get(0), equalTo("гидроэлектростанция"));
    }

    @Test (description = "api should return nothing if there are no mistakes")
    public void noMistakes(){
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .text("hello")
                                .callApi());
        assertThat(answers.size(), equalTo(0));

    }


    @Test (description = "api should return as many answers as there are mistakes")
    public void manyMistakes(){
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .text("Im not gooing too skool becase Ive gradiated from mani eers agou")
                                .callApi());
        assertThat(answers.size(), equalTo(9));

    }

    @Test (description = "api should return nothing if the option IGNORE_DIGITS is turn on")
    public void optionNoDigit(){
        List<YandexSpellerAnswer> answers =
                YandexSpellerAPI.getYandexSpellerAnswers(
                        YandexSpellerAPI
                                .with()
                                .language(EN)
                                .options(IGNORE_DIGITS)
                                .text("hell666")
                                .callApi());
        assertThat(answers.size(), equalTo(0));

    }
}
