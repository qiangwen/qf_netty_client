package com.qf.netty.config;

import com.qf.netty.app.NettyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: wenqiang
 * @date: 2019-09-24 11:19
 */
@Configuration
public class NettyConfig {


    @Bean
    public NettyClient nettyClient(){
        NettyClient nettyClient = new NettyClient("127.0.0.1",9001);
        return nettyClient;
    }
}
