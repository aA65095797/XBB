package gateway.filter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.HttpUtil;
import org.junit.platform.commons.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author xubenben
 * @date 2022/01/25 11:39 下午
 */
public class HeaderHttpRequestTokenFilter implements HttpRequestFilter {

    //key: token， value：memberID
    static final Map<String, String> tokens = new HashMap<>();
    static {
        tokens.put("111","11223344");
        tokens.put("222","2323232");
    }
    @Override
    public Boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        String token = fullRequest.headers().get("token");

        if(StringUtils.isBlank(token) || StringUtils.isBlank(tokens.get(token))){
            FullHttpResponse response = null;
            try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());


//            System.out.println(new String(body));
//            System.out.println(body.length);
                ByteBuf content = Unpooled.copiedBuffer("你不是我们公司的，请回家养猪去吧", HttpConstants.DEFAULT_CHARSET);
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

                response.headers().set("Content-Type", "application/json");
                response.headers().setInt("Content-Length", response.content().readableBytes());


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
