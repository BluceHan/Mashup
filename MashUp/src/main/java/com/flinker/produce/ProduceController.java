package com.flinker.produce;

import org.springframework.stereotype.Component;

@Component
public class ProduceController {

    private static Producer producer = Producer.newProducer();;

    public ProduceController() {
    }


}
