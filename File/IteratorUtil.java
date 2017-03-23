package com.gaolonglong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaohailong on 2017/3/23.
 */
public class IteratorUtil {
    //目录层级，0代表当前目录，1代表下一级目录，以此类推
    private static int level = 0;

    public static void IteratorFile(File file) {
        if (file != null) {
            if (file.isFile() || file.listFiles().length == 0) {
                return;
            }
            File[] files = file.listFiles();
            files = sortFile(files);

            for (File f : files) {
                StringBuilder sb = new StringBuilder();
                if (f.isFile()) {
                    sb.append(getTab(level));
                    sb.append(f.getName());
                } else {
                    sb.append(getTab(level));
                    sb.append(f.getName());
                    sb.append("\\");
                }
                System.out.println(sb.toString());

                if (f.isDirectory()) {
                    level++;
                    IteratorFile(f);
                    level--;
                }
            }
        }
    }

    //根据传过来的层级数level，返回数倍"\t"的字符串
    private static String getTab(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }

    //把传进来的File数组，按照先文件夹后文件方式排列，返回排列后的File数组
    private static File[] sortFile(File[] files) {
        List<File> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                fileList.add(file);
            }
        }
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        return fileList.toArray(new File[fileList.size()]);
    }
}
