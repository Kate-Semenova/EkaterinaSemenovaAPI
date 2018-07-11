package core;

/**
 * Created by Ekaterina on 11.07.2018.
 */
public class YandexSpellerOptions {
    /**
    *Пропускать слова с цифрами, например, "авп17х4534".
    */
    public final static String IGNORE_DIGITS = "2";
    /**
     *Пропускать интернет-адреса, почтовые адреса и имена файлов.
     */
    public final static String IGNORE_URLS = "4";
    /**
     *Подсвечивать повторы слов, идущие подряд. Например, "я полетел на на Кипр".
     */
    public final static String FIND_REPEAT_WORDS = "8";
    /**
     *Игнорировать неверное употребление ПРОПИСНЫХ/строчных букв, например, в слове "москва".
     */
    public final static String IGNORE_CAPITALIZATION = "512";
}
