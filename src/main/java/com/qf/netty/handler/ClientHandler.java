package com.qf.netty.handler;

import com.qf.netty.app.AppContext;
import com.qf.netty.app.NettyClient;
import com.qf.netty.app.NettyConnection;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: wenqiang
 * @date: 2019-09-17 15:15
 */
@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("....连接成功！");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive msg : " + msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //该channel处于非活动状态，并到达生命周期结束
        //断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("断线重连......");
                NettyConnection conn = new NettyConnection();
                conn.connect("127.0.0.1",9001);
                AppContext.regConnection("nettyServer",conn);
            }
        },10L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(IdleState.WRITER_IDLE == event.state()){
                ctx.writeAndFlush("000000\n");
            }else if(IdleState.READER_IDLE == event.state()){
                ctx.close();
            }
        }
       ctx.fireUserEventTriggered(evt);
    }

}
