package com.qf.netty.app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wenqiang
 * @date: 2019-09-24 10:42
 */
public class AppContext {


    private static Map<String,NettyConnection> connectionMap = new ConcurrentHashMap<>();


    public synchronized static void regConnection(String name,NettyConnection conn){
        connectionMap.put(name,conn);
    }

    public static NettyConnection getConn(String name){
        return connectionMap.get(name);
    }

}
