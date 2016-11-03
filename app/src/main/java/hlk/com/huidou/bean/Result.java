package hlk.com.huidou.bean;

/**
 * Created by user on 2016/11/3.
 */

public class Result {
    private int code;
    private String text;

    public Result(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
