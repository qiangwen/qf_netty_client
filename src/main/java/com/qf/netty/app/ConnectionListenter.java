package com.qf.netty.app;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * @author: wenqiang
 * @date: 2019-09-24 11:40
 */
public class ConnectionListenter implements ChannelFutureListener{

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if(future.isSuccess()){
            System.out.println("连接成功.....");
        }else {
            final EventLoop loop = future.channel().eventLoop();
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("连接失败重连......");
                    NettyConnection connection = new NettyConnection();
                    connection.connect("127.0.0.1",9001);
                    AppContext.regConnection("nettServer",connection);
                }
            },10L, TimeUnit.SECONDS);
        }
    }
}
