package com.qf.netty.app;

import com.qf.netty.handler.ClientHandler;
import com.qf.netty.util.BeanContext;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.LineEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: wenqiang
 * @date: 2019-09-17 15:11
 */
public class NettyClient{

    private String host;

    private int port;


    public NettyClient(){}

    public NettyClient(String host,int port){
        this.host = host;
        this.port = port;
        init();
    }

    private void init(){
        Thread startThread = new Thread(){
            @Override
            public void run() {
               NettyConnection conn = new NettyConnection();
               conn.connect(host,port);
               AppContext.regConnection("nettyServer",conn);
            }
        };
        startThread.start();
    }

}
