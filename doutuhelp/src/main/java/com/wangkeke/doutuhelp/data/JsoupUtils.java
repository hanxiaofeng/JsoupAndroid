package com.wangkeke.doutuhelp.data;

import android.os.Handler;
import android.os.Message;

import com.wangkeke.doutuhelp.bean.FoldData;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkeke on 2017/11/21.
 */

public class JsoupUtils {

    private static final String BASE_URL = "http://sj.enterdesk.com/";

    private static JsoupUtils jsoupUtils = null;

    private List<FoldData> listData = null;

    private static Handler myHandler;

    String url = "";

    public static JsoupUtils newInstance(Handler handler) {
        myHandler = handler;
        if (null == jsoupUtils) {
            jsoupUtils = new JsoupUtils();
        }
        return jsoupUtils;
    }

    public void getImageFoldByUrl(int page) {

        if (page == 1) {
            url = BASE_URL;
        } else {
            url = BASE_URL + "" + page + ".html";
        }

        listData = new ArrayList<>();
        // 通过get()获取一个Document对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Connection connect = Jsoup.connect(url);
                    connect.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/20100101 Firefox/32.0");

                    final Document document = connect.get();

                    Elements typoElements = document.body().select("div.main")
                            .select("div.egeli_pic_m").select("div.egeli_pic_li");

                    // 多个select()拼接可以用空格隔开
                    typoElements.remove(0);
                    typoElements.remove(typoElements.size()-1);
                    for (Element element : typoElements) {
                        String href = element.select("dl.egeli_pic_dl dd a img").attr("src");
                        FoldData foldData = new FoldData();
                        foldData.setTitle("");
                        foldData.setImgUrl(href);
                        foldData.setJumpUrl("");
                        foldData.setDate("");
                        listData.add(foldData);
                    }

                    Message message = new Message();
                    message.obj = listData;
                    myHandler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
