package core;

/**
 * Created by Ekaterina on 11.07.2018.
 */
public class YandexSpellerErrors {
    /**
     * Слова нет в словаре.
     */
    public final static int ERROR_UNKNOWN_WORD = 1;
    /**
     * Повтор слова.
     */
    public final static int ERROR_REPEAT_WORD = 2;
    /**
     * Неверное употребление прописных и строчных букв.
     */
    public final static int ERROR_CAPITALIZATION = 3;
    /**
     * Текст содержит слишком много ошибок.
     * При этом приложение может отправить Яндекс.Спеллеру оставшийся непроверенным текст в следующем запросе.
     */
    public final static int ERROR_TOO_MANY_ERRORS = 4;
}
