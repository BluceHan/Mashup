package com.flinker.bean;

import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;

@Component
public class UrlProduce implements Comparable<UrlProduce>{

    private Integer partition;
    private String account;
    private URL url;
    private ThreadLocal<Boolean> threadLocal;

    public UrlProduce() {
    }

    public UrlProduce(String account, URL url, ThreadLocal threadLocal) {
        this.partition = account.hashCode() % 10;
        this.account = account;
        this.url = url;
        this.threadLocal = threadLocal;
    }

    public int getPartition() {
        return partition;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    public Boolean getThreadLocal() {
        return threadLocal.get();
    }

    public void setThreadLocal(Boolean threadLocal) {
        this.threadLocal.set(threadLocal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlProduce that = (UrlProduce) o;
        return Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }

    @Override
    public String toString() {
        return "UrlProduce{" +
                "partition=" + partition +
                ", account='" + account + '\'' +
                ", url=" + url +
                '}';
    }

    @Override
    public int compareTo(UrlProduce o) {
        return account.compareTo(o.getAccount());
    }
}
