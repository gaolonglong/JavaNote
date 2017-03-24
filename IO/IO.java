package com.gaohailong;

import java.io.*;

/**
 * Created by gaohailong on 2017/3/23.
 */
public class IO {
    public static void main(String[] args) throws IOException{
        //InputStream是字节输入流的顶层抽象类，可以用于操作图像，音频和视频等
        //它的实现类有FileInputStream,ByteArrayInputStream等
        //ByteArrayInputStream内部自带一个缓冲区，把字节流读取到自带缓冲区，在从缓冲区写入到输出流，
        // 不需要调用close关闭，可以使用toByteArray或toString获取数据

        //一个个字节读取文件拷贝（读一个写一个），一个8M的文件花费了160357ms
        copyMp3ByByte(new File("I:\\io\\a.mp3"),new File("I:\\io\\b.mp3"));

        //借助byte数组读取文件拷贝，一个8M的文件花费了113ms
        copyMp3ByByteArr(new File("I:\\io\\a.mp3"),new File("I:\\io\\c.mp3"));

        //ByteArrayInputStream和ByteArrayOutputStream
        byteArrayIOStream(new String("i love Shanghai"),new File("I:\\io\\d.txt"));

        //过滤流:BufferedInputStream,BufferedOutputStream（待缓冲的读写，提高读写效率）,
        //DataInputStream,DataOutputStream（用来读取Java基本数据类型）
        //使用包装流读取文件拷贝，一个8M的文件花费了896ms
        copyMp3ByBuffer(new File("I:\\io\\a.mp3"),new File("I:\\io\\e.mp3"));

        //复制指定文件夹下的所有文件和文件夹（包括子文件夹的文件和文件夹）到指定文件夹
        copyDir(new File("F:\\git"), new File("F:\\git2"));
    }

    //一个个字节读取
    public static void copyMp3ByByte(File src,File des) throws IOException{
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(des);

        long l1 = System.currentTimeMillis();
        int b;
        while ((b = fis.read()) != -1){
            fos.write(b);
        }
        fis.close();
        fos.close();
        long l2 = System.currentTimeMillis();
        System.out.println("一个个字节读取文件花费了：" + (l2 - l1) + "ms");
    }

    //借助byte数组读取
    public static void copyMp3ByByteArr(File src,File des) throws IOException{
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(des);

        long l1 = System.currentTimeMillis();
        byte[] buff = new byte[1024 * 1024];//数组大小1M
        int len;
        while ((len = fis.read(buff)) != -1){
            fos.write(buff,0,len);
        }
        fis.close();
        fos.close();
        long l2 = System.currentTimeMillis();
        System.out.println("借助byte数组读取文件花费了：" + (l2 - l1) + "ms");
    }

    //ByteArrayInputStream
    public static void byteArrayIOStream(String str,File des) throws IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes());
        int b;
        while ((b = bis.read()) != -1){
            System.out.print((char) b);
        }
        bis.close();//可关闭，也可以不关闭

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(65);
        bos.write(97);
        bos.write("hello java".getBytes());//把整型和字节数组写入ByteArrayOutputStream的缓冲区
        byte[] bytes = bos.toByteArray();
        for (byte by : bytes){
            System.out.print((char) by);
        }

        FileOutputStream fos = new FileOutputStream(des,true);//true表示每次写入追加，默认或者false是覆盖
        bos.writeTo(fos);//把ByteArrayOutputStream内部缓冲区的数据写入到对应的文件输出流中
        bos.close();//可关闭，也可以不关闭
        fos.close();

        //以上也可以把bis和bos整合一起，bis读取字节数组，写入到bos，
        //然后可以调用toByteArray()循环输出；也可以写出到文件输出流fos中
    }

    //不用自定义缓冲字节数组，使用BufferedInputStream和BufferedOutputStream
    public static void copyMp3ByBuffer(File src,File des) throws IOException{
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(des);
        BufferedInputStream bis = new BufferedInputStream(fis);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        long l1 = System.currentTimeMillis();
        int len;
        while ((len = bis.read()) != -1){ //先写满缓冲区，然后bis.read()是从缓冲区读取的
            bos.write(len); //bos.write是从缓冲区写入输出流的
        }
        bis.close();//只需要关闭包装流就可以了，它会自动关闭对应的文件流
        bos.close();
        long l2 = System.currentTimeMillis();
        System.out.println("使用buff包装流读取文件花费了：" + (l2 - l1) + "ms");
    }

    ////复制指定文件夹下的所有文件和文件夹（包括子文件夹的文件和文件夹）到指定文件夹
    public static void copyDir(File src, File des) throws IOException {
        des.mkdirs();
        if (src != null) {
            if (src.isFile() || src.listFiles().length == 0) {
                return;
            } else {
                File[] files = src.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        createFile(file,des);
                    }else {
                        copyDir(file,new File(des,file.getName()));
                    }
                }
            }
        }
    }

    public static void createFile(File file, File des) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(des, file.getName())));
        int len;
        while ((len = bis.read()) != -1) {
            bos.write(len);
        }
        bis.close();
        bos.close();
    }
}
