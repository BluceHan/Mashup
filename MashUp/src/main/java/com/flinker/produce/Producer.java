package com.flinker.produce;


import com.flinker.bean.UrlProduce;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class Producer {

    private static final List<LinkedList<UrlProduce>> produceList = new LinkedList<LinkedList<UrlProduce>>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private Producer() { }

    private static class ProducerInit {
        public static Producer instance = new Producer();
    }

    public static Producer newProducer() {
        return ProducerInit.instance;
    }

    public void add(String account, UrlProduce urlProduce) {
        if (account == null || ("").equals(account) || urlProduce == null) {
            throw new NullPointerException("");
        }
        try {
            readWriteLock.writeLock().lock();
            if (produceList.size() < urlProduce.getPartition()) {
                int count = (urlProduce.getPartition() - produceList.size()) + 1;
                for (int i = 0; i < 10; i ++) {
                    produceList.add(new LinkedList<>());
                }

            }
            System.out.println(produceList.size());
            List<UrlProduce> urlProduces = produceList.get(urlProduce.getPartition());
            urlProduces.add(urlProduce);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public UrlProduce get(int index) {
        if (index < 0 && index > 10) {
            throw new ArrayIndexOutOfBoundsException("");
        }

        if (produceList.get(index).isEmpty()) {
            throw new NullPointerException("");
        }

        try {
            readWriteLock.readLock().lock();
            LinkedList<UrlProduce> urlProduces = produceList.get(index);
            UrlProduce result = urlProduces.get(0);
            urlProduces.remove(0);
            return result;
        } finally {
            readWriteLock.readLock().unlock();
        }

    }

    public void remove(UrlProduce urlProduce) {
        if (urlProduce.getPartition() < 0 && urlProduce.getPartition() > 10) {
            throw new ArrayIndexOutOfBoundsException("");
        }

        if (produceList.get(urlProduce.getPartition()).isEmpty()) {
            throw new NullPointerException("");
        }

        try {
            List<UrlProduce> urlProduces = produceList.get(urlProduce.getPartition());
            readWriteLock.writeLock().lock();
            urlProduces.remove(0);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        System.out.println(1234);
        Producer producer = Producer.newProducer();
        System.out.println(producer);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    int a = j % 10;
                    System.out.println("====" + a + "===");
                }
                System.out.println("===>" + producer.get(2));
            }).start();
        }
    }

}
