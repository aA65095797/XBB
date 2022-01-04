package com.example.demo;

import java.util.Base64;

/**
 * @author xubenben
 * @date 2022/01/04 16:11 下午
 */
public class HelloBase64Loader extends ClassLoader{

    public static void main(String[] args) throws Exception {
        new HelloBase64Loader().findClass("com.example.demo.Hello").newInstance();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String a = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAApIZWxsby5qYXZhDAAHAAgHABYMABcAGAEAH+S9oOWlveWRgO+8jOS9oOS7iuWkqeecn+W4he+8gX4HABkMABoAGwEAFmNvbS9leGFtcGxlL2RlbW8vSGVsbG8BABBqYXZhL2xhbmcvT2JqZWN0AQAQamF2YS9sYW5nL1N5c3RlbQEAA2VycgEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWACEABQAGAAAAAAACAAEABwAIAAEACQAAAB0AAQABAAAABSq3AAGxAAAAAQAKAAAABgABAAAABwAIAAsACAABAAkAAAAlAAIAAAAAAAmyAAISA7YABLEAAAABAAoAAAAKAAIAAAAJAAgACgABAAwAAAACAA0=";
        byte[] decode = getBytes(a);

        return defineClass(name, decode, 0, decode.length);
    }

    private byte[] getBytes(String base64) {
        byte[] decode = Base64.getDecoder().decode(base64);
        return decode;
    }

}
