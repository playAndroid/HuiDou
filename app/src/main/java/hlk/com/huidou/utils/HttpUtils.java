package hlk.com.huidou.utils;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import hlk.com.huidou.bean.ChatMessage;
import hlk.com.huidou.bean.CommonException;
import hlk.com.huidou.bean.Result;

/**
 * 网络工具类
 * Created by user on 2016/11/3.
 */

public class HttpUtils {
    private static String API_KEY = "534dc342ad15885dffc10d7b5f813451";
    private static String URLS = "http://www.tuling123.com/openapi/api";

    /**
     * 发送消息
     *
     * @param msg 内容
     * @return ChatMessage 消息对象
     */
    public static ChatMessage sendMassage(String msg) {
        ChatMessage message = new ChatMessage();
        String url = setParams(msg);
        String res = doGet(url);
        Gson gson = new Gson();
        Result result = gson.fromJson(res, Result.class);
        if (result.getCode() > 400000 || result.getText() == null || result.getText().trim().equals("")) {
            message.setMsg("该功能有待开发");
        } else {
            message.setMsg(result.getText());
        }
        message.setType(ChatMessage.Type.INPUT);
        message.setDate(new Date());
        return message;
    }

    /**
     * get请求
     *
     * @param urlStr url
     * @return result
     */
    private static String doGet(String urlStr) {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;

        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5 * 1000);
            connection.setConnectTimeout(5 * 1000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                outputStream = new ByteArrayOutputStream();
                int len = -1;
                byte[] bytes = new byte[128];
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
                outputStream.flush();
                return outputStream.toString();
            } else {
                throw new CommonException("服务器连接错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("服务器连接错误");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }


    }

    /**
     * 拼接Url
     *
     * @param msg 发送的信息
     * @return 访问的url
     */
    private static String setParams(String msg) {

        try {
            msg = URLEncoder.encode(msg, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return URLS + "?key=" + API_KEY + "&info=" + msg;
    }
}
