package com.example.demo;


import sun.nio.ch.IOUtil;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xubenben
 * @date 2021/09/17 10:09 下午
 */
public class mm {
//    public static void main(String[] args) {
//
//        IntBuffer allocate = IntBuffer.allocate(20);
//        allocate.put(1);
//        allocate.put(2);
//        allocate.put(3);
//        allocate.put(4);
//        allocate.put(5);
//        System.err.println("write:" + allocate.position());
//        System.err.println("writelimit:" + allocate.limit());
//        allocate.flip();
//        System.err.println("read:" + allocate.position());
//        for(int i = 0; i < 5; i++){
//            System.err.println("get:" + allocate.get());
//            System.err.println("getposition:" + allocate.position());
//        }
//        allocate.clear();
//        System.err.println("clearposition:" + allocate.position());
//        System.err.println("clearlimit:" + allocate.limit());
//
//    }
    public static void main(String[] args) throws IOException {

//        RandomAccessFile aFile = new RandomAccessFile("策略模式解决方案","rw"); //获取通道 FileChannelinChannel=aFile.getChannel(); //获取⼀个字节缓冲区 ByteBufferbuf = ByteBuffer.allocate(CAPACITY); int length = -1; //调⽤通道的read⽅法，读取数据并买⼊字节类型的缓冲区
//        FileChannel inChannel=aFile.getChannel(); //获取⼀个字节缓冲区
//         ByteBuffer buf = ByteBuffer.allocate(20);
//         int length = -1;
//        while ((length = inChannel.read(buf)) != -1) {
//            int read = inChannel.read(buf);
//            System.err.println(read);
//        }
        nioCopyFile("src/main/resources/templates/策略模式解决方案","src/main/resources/templates/Utils");


    }

    public static void nioCopyFile(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        try {//如果⽬标⽂件不存在，则新建
             if (!destFile.exists()) {
                 destFile.createNewFile(); }
            long startTime = System.currentTimeMillis();
             FileInputStream fis = null;
             FileOutputStream fos = null;
             FileChannel inChannel = null;
             FileChannel outchannel = null;
             try {fis = new FileInputStream(srcFile);
                 fos = new FileOutputStream(destFile);
                 inChannel = fis.getChannel();
                 outchannel = fos.getChannel();
                 int length = -1;
                 ByteBuffer buf = ByteBuffer.allocate(1024);
                 //从输⼊通道读取到buf
                 while ((length = inChannel.read(buf)) != -1)
                 { //第⼀次切换：翻转buf，变成读取模式
                      buf.flip();
                      int outlength = 0; //将buf写⼊到输出的通道
                      while ((outlength = outchannel.write(buf)) != 0) {
                          System.out.println("写⼊的字节数：" + outlength); }//第⼆次切换：清除buf，变成写⼊模式
        buf.clear(); }
                 //强制刷新到磁盘
                 outchannel.force(true); }
             finally {
                 //关闭所有的可关闭对象

                 if (outchannel != null) {
                     outchannel.close();
                 }
                 if (fos != null) {
                     fos.close();
                 }
                 if (inChannel != null) {
                     inChannel.close();
                 }
                 if (fis != null) {
                     fis.close();
                 }
             }
             long endTime = System.currentTimeMillis();
             info("base复制毫秒数：" + (endTime - startTime)); }
        catch (IOException e) { e.printStackTrace(); }
    }

        public static void info(String a){
        System.err.println(a);
        }
}
