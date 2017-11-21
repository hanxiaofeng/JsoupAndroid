package com.wangkeke.study2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // 连接提供了一个方便的接口来从web获取内容，并将它们解析为文档
    final Connection connect = Jsoup.connect("http://gank.io/xiandu/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        // 伪装成浏览器抓取，具体有没用布吉岛。。
        connect.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/20100101 Firefox/32.0");

        // 通过get()获取一个Document对象

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Document document = connect.get();

                    String title = document.head().select("title").text();
                    Log.e("result", "title = " + title);
                    String threeTitle = document.body().select("div.typo").select("div.container").select("h3.center").text();
                    Log.e("result", "threeTitle = " + threeTitle);

                    //获取li标签中的值
                    // <div class="container content">两个类随便选一个就行，这应该是js知识。。
                    Elements typoElements = document.body().select("div.typo").select("div.container");
                    // 多个select()拼接可以用空格隔开
                    Elements catsLi = typoElements.select("div#xiandu_cat ul li");

                    for (Element element : catsLi) {
                        String href = element.select("a").attr("href");
                        String text = element.text();
                        System.out.println(href + "\t" + text);
                        Log.e("result", "catsLi = " + href + "\t" + text);
                    }

                    getArticleInfo(typoElements);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void getArticleInfo(Elements typoElements){
        Elements itemElements = typoElements.select("div.xiandu_items div.xiandu_item");
        for (Element element : itemElements) {
            Elements leftElements = element.select("div.xiandu_left");
            // 编号，1:
            String no = leftElements.select("span.xiandu_index").text();
            // 跳转网址，http://daily.zhihu.com/story/9657300?utm_source=gank.io%2Fxiandu&utm_medium=website
            String url = leftElements.select("a").attr("href");
            // 内容，想精想怪 90——吃鸡手游化那些事
            String desc = leftElements.select("a").text();
            // 时间，8 分钟前
            String date = leftElements.select("span small").text();

            Elements rightElements = element.select("div.xiandu_right");
            // 来源url，/xiandu/view/zhihu
            String sourceUrl = rightElements.select("a.site-name").attr("href");
            // 来源名称，知乎日报
            String sourceTitle = rightElements.select("a.site-name").attr("title");
            // 来源图标，http://ww4.sinaimg.cn/large/610dc034jw1f9sfzr2gmnj204v04va9y.jpg
            String sourceIcon = rightElements.select("a.site-name img").attr("src");
//            System.out.println(no + "\t" + url + "\t" + desc + " \t" + date + "\t" + sourceUrl + "\t" + sourceTitle + "\t" + sourceIcon);


            Log.e("result", "" + no + "\t" + url + "\t" + desc + " \t" + date + "\t" + sourceUrl + "\t" + sourceTitle + "\t" + sourceIcon);
        }
    }
}
