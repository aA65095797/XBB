package com.example.demo.gateway.filter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.util.EntityUtils;


import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HeaderHttpRequestFilter implements HttpRequestFilter {

    static final List<String> passUrl = new ArrayList<>();
    static {
        passUrl.add("/xbb");
        passUrl.add("/test");
        passUrl.add("/hhh");
    }
    @Override
    public Boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("mao", "soul");
        String uri = fullRequest.uri();
        if(!passUrl.contains(uri)){
            FullHttpResponse response = null;
            try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());


//            System.out.println(new String(body));
//            System.out.println(body.length);
                ByteBuf content = Unpooled.copiedBuffer("不让你请求，请回家种红薯", HttpConstants.DEFAULT_CHARSET);
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

                response.headers().set("Content-Type", "application/json");
//                response.headers().setInt("Content-Length", Integer.parseInt(response.getgetValue()));


//            for (Header e : endpointResponse.getAllHeaders()) {
//                //response.headers().set(e.getName(),e.getValue());
//                System.out.println(e.getName() + " => " + e.getValue());
//            }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);

                ctx.close();
            } finally {
                System.err.println("fullRequest：【" + fullRequest+"】");
                System.err.println(fullRequest.uri());
                if (fullRequest != null) {
                    if (!HttpUtil.isKeepAlive(fullRequest)) {
                        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                    } else {
                        //response.headers().set(CONNECTION, KEEP_ALIVE);
                        ctx.write(response);
                    }
                }
                ctx.flush();
                //ctx.close();
            }
        }
        return true;
    }
}
