package com.jic.elearning.thrid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * https://howtoprogram.xyz/2016/10/31/java-rest-client-examples-using-okhttp/
 */
@Service
public class ScheduleHttpServiceImpl implements ScheduleHttpService {

    public static final MediaType XWFL = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final Log LOGGER = LogFactory.getLog(ScheduleHttpServiceImpl.class);
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";


    private final OkHttpClient okHttpClient;
    private final ObjectMapper mapper;


    @Autowired
    ScheduleHttpServiceImpl(OkHttpClient okHttpClient,
                            ObjectMapper mapper) {
        this.okHttpClient = okHttpClient;
        this.mapper = mapper;
    }

    @Override
    public void runOneTask(String group, String job, String userName) throws Exception {
        LOGGER.info(String.format("Schedule Run One Task [%s,%s,%s].", group, job, userName));
        String url = String.format("http://localhost:8010/task/run-one/%s/%s", group, job);
        LOGGER.info(String.format("schedule.runOneTask.url = [%s]", url));

        RequestBody body = RequestBody.create(
                JSON,
                "{\n" +
                        "  \"userName\": \"" + userName + "\"\n" +
                        "}");
        Request request = new Request.Builder()
//                .header("Cookie", sessionId)
//                .header("Host", tianmaProperties.getHostName())
//                .header("Connection", "keep-alive")
//                .header("Accept", "application/json, text/javascript, */*; q=0.01")
//                .header("Origin", "http://www.tianmasport.com")
//                .header("X-Requested-With", "XMLHttpRequest")
//                .header("User-Agent", USER_AGENT)
                //               .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .header("Referer", "http://www.tianmasport.com/ms/login.shtml")
//                .header("Accept-Encoding", "gzip, deflate")
//                .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4")
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.code() == 200) {
            LOGGER.info(response.body().string());
        } else {
           throw new Exception(response.body().string());
        }

    }

}
