package core;

/**
 * Created by Ekaterina on 11.07.2018.
 */
public enum Languages {
    RU("ru"),
    UK("uk"),
    EN("en"),
    INVALID("qw");


    public String languageCode;

    private Languages(String lang) {
        this.languageCode = lang;
    }
}