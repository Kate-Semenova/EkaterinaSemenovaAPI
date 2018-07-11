package beans;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Ekaterina on 10.07.2018.
 */
public class YandexSpellerAnswer {
    @Expose
    public int code;
    @Expose
    public int pos;
    @Expose
    public int row;
    @Expose
    public int col;
    @Expose
    public int len;
    @Expose
    public String word;
    @Expose
    public List<String> s;

    @Override
    public String toString() {
        return "YandexSpellerAnswer{" +
                "code=" + code +
                ", pos=" + pos +
                ", row=" + row +
                ", col=" + col +
                ", len=" + len +
                ", word='" + word + '\'' +
                ", s=" + s +
                '}';
    }
}
