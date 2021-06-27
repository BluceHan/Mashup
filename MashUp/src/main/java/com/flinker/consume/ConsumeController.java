package com.flinker.consume;


import com.flinker.bean.UrlProduce;
import com.flinker.produce.Producer;
import com.flinker.utils.UrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ConsumeController implements Runnable{

    private Integer partition = -1;
    private static final Producer producer = Producer.newProducer();

    public ConsumeController() {
    }

    public ConsumeController(Integer partition) {
        this.partition = partition;
    }


    @Override
    public void run() {
        UrlProduce urlProduce = producer.get(partition);
        String data = UrlUtils.parseUrl(urlProduce.getUrl());
        if (data == null || "".equals(data)) {
            urlProduce.setThreadLocal(false);
            throw new NullPointerException("");
        }
        Consumes.add(urlProduce.getAccount(), data);
        urlProduce.setThreadLocal(true);
    }
}
