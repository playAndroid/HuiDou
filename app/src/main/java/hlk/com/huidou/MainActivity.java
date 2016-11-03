package hlk.com.huidou;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import hlk.com.huidou.adapter.ChatListAdapter;
import hlk.com.huidou.bean.ChatMessage;
import hlk.com.huidou.utils.HttpUtils;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private View mSend;
    private EditText mContent;
    private ArrayList<ChatMessage> mDatas = new ArrayList<>();
    private ChatListAdapter adapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatMessage from = (ChatMessage) msg.obj;
            mDatas.add(from);
            adapter.notifyDataSetChanged();
            mListView.setSelection(mDatas.size() - 1);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        registerListener();
    }

    private void registerListener() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送消息
                sendMessage();
            }
        });
    }

    /**
     * 发送消息
     */
    private void sendMessage() {
        final String msg = mContent.getText().toString().trim();
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "没有填写任何消息", Toast.LENGTH_SHORT).show();
            return;
        }
        ChatMessage to = new ChatMessage(ChatMessage.Type.OUTPUT, msg);
        mDatas.add(to);
        adapter.notifyDataSetChanged();
        mListView.setSelection(mDatas.size() - 1);
        mContent.setText("");
        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //是否活跃的,键盘管理器
        if (im.isActive()) {
            // 如果开启
            im.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }

        new Thread() {
            @Override
            public void run() {
                ChatMessage from = null;
                try {
                    from = HttpUtils.sendMassage(msg);
                } catch (Exception e) {
                    from = new ChatMessage(ChatMessage.Type.INPUT, "服务器异常了,挂掉了");
                }
                Message message = Message.obtain();
                message.obj = from;
                mHandler.sendMessage(message);

            }
        }.start();

    }

    private void initData() {
        mDatas.add(new ChatMessage(ChatMessage.Type.INPUT, "hello,我是灰豆,可以陪你聊天哦,嘿嘿"));
        adapter = new ChatListAdapter(this, mDatas);
        mListView.setAdapter(adapter);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        mContent = (EditText) findViewById(R.id.et_content);
        mSend = findViewById(R.id.btn_send);
    }
}
