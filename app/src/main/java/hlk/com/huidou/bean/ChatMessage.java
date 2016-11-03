package hlk.com.huidou.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 聊天实体
 * Created by hlk on 2016/11/2.
 */

public class ChatMessage {
    /**
     * 发送人姓名
     */
    private String name;
    /**
     * 发送时间
     */
    private Date date;
    /**
     * 发送内容
     */
    private String msg;
    /**
     * 格式化的时间
     */
    private String dateStr;
    /**
     * 消息类型
     */
    private  Type type;

    public ChatMessage() {
    }

    public ChatMessage(Type type, String msg) {
        this.type = type;
        this.msg = msg;
        setDate(new Date());
    }

    public enum Type {
        INPUT, OUTPUT
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = dateFormat.format(date);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
