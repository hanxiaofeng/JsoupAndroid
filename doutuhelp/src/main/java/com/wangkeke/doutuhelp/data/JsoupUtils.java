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

    private static JsoupUtils jsoupUtils = null;

    private List<FoldData> listData = null;

    private static Handler myHandler;

    public static JsoupUtils newInstance(Handler handler) {
        myHandler = handler;
        if (null == jsoupUtils) {
            jsoupUtils = new JsoupUtils();
        }
        return jsoupUtils;
    }

    public void getImageFoldByUrl(final String url) {
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
//                    Elements catsLi = typoElements.select("ul li");
                    typoElements.remove(0);
                    for (Element element : typoElements) {
                        String href = element.select("dl.egeli_pic_dl dd a img").attr("src");
//                        String hrefimg = element.select("span.dtimg").select("a img").attr("src");
//                        String name = element.select("span.dtname").select("a").text();
//                        String date = element.select("span.dttip").select("font.zihui").text();
//                        Log.e("result", "catsLi = " + hrefimg+"\t"+href + "\t" + name+"\t"+date);

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
