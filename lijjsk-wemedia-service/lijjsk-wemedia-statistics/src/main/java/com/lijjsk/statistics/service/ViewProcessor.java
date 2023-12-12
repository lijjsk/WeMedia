package com.lijjsk.statistics.service;

public interface ViewProcessor {
    public void addViewNum(String jsonString);
    public void reduceViewNum(String jsonString);
    public void addLikeNum(String jsonString);
    public void reduceLikeNum(String jsonString);
    public void addCollectNum(String jsonString);
    public void reduceCollectNum(String jsonString);
    public void addShareNum(String jsonString);
    public void reduceShareNum(String jsonString);
    public void addCoinNum(String jsonString);
    public void reduceCoinNum(String jsonString);
}
