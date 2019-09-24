package com.qf.netty.app;

import com.qf.netty.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;


/**
 * @author: wenqiang
 * @date: 2019-09-24 10:43
 */
public class NettyConnection {

    private Channel channel;


    public void connect(String host,int port){
        doConnect(host,port);
//        Channel channel = doConnect(host,port);
//        this.channel = channel;
    }

    private void doConnect(String host, int port) {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
//                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
//                                    .addLast(new LineEncoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new IdleStateHandler(0,30,0))
                                    .addLast(new ClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host,port);
            f.addListener(new ConnectionListenter());
            channel = f.channel();
//            channel.closeFuture().sync();  //加了这行代码线程会被阻塞
        } catch (Exception e) {
            e.printStackTrace();
        }
        //这段代码会导致线程池关闭
//        finally {
//            workerGroup.shutdownGracefully();
//        }
    }


}
