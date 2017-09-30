package com.ec.thrift.util;

/**
 *
 * SocketPoolConfig.java
 *
 * @author Chen Jun 2015年4月28日
 */
public class SocketPoolConfig {
    private int maxActive;
    private int maxIddle;
    private int minIddle;
    private int minEvictableIdleTimeMillis;
    private int timeBetweenEvictionRunsMillis;
    private int timeoutMillis;
    private int MaxWaitMillis;
    private String heartBeatClass;
    private int heartBeatInterval;
    private boolean longLink = false;
    private String host;
    private int port;
    private int timeout;

    public SocketPoolConfig() {
    }

    public SocketPoolConfig(int timeoutMillis, boolean longLink, int heartBeatInterval) {

        this.longLink = longLink;
        this.timeoutMillis = timeoutMillis;
        this.heartBeatInterval = heartBeatInterval;
    }

    public SocketPoolConfig(int soTimeout, int timeoutMillis) {
        this(timeoutMillis, false, 0);
    }

    public SocketPoolConfig(boolean longLink) {
        this(0, longLink, 0);
    }

    public SocketPoolConfig(int maxActive, int maxIddle, int minIddle, int MaxWaitMillis, int minEvictableIdleTimeMillis,
            int timeBetweenEvictionRunsMillis, int timeoutMillis, int heartBeatInterval) {
        super();
        this.maxActive = maxActive;
        this.maxIddle = maxIddle;
        this.minIddle = minIddle;
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        this.MaxWaitMillis = MaxWaitMillis;
        this.timeoutMillis = timeoutMillis;
        this.heartBeatInterval = heartBeatInterval;
        if (heartBeatInterval > 0) {
            this.longLink = true;
        }
    }

    public SocketPoolConfig getDefaultConfig() {
        return new SocketPoolConfig(false);
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIddle() {
        return maxIddle;
    }

    public void setMaxIddle(int maxIddle) {
        this.maxIddle = maxIddle;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public int getMaxWaitMillis() {
        return MaxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        MaxWaitMillis = maxWaitMillis;
    }

    public boolean isLongLink() {
        return longLink;
    }

    public void setLongLink(boolean longLink) {
        this.longLink = longLink;
    }

    public String getHeartBeatClass() {
        return heartBeatClass;
    }

    public void setHeartBeatClass(String heartBeatClass) {
        this.heartBeatClass = heartBeatClass;
    }

    public int getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(int heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public int getMinIddle() {
        return minIddle;
    }

    public void setMinIddle(int minIddle) {
        this.minIddle = minIddle;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
