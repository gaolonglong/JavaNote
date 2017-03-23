package com.gaolonglong;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * Created by gaohailong on 2017/3/23.
 */
public class File_ {
    public static void main(String[] args) {
        File file = new File("f:\\io");

        //遍历指定文件夹下的所有文件和文件夹，输出名称（不包括子文件夹下的文件和文件夹）
        String[] list = file.list();
        for (String l : list) {
            System.out.println(file.getAbsolutePath() + "\\" + l);
        }

        System.out.println("----------------------");
        //遍历指定文件夹下的所有文件和文件夹，按筛选条件输出名称（不包括子文件夹下的文件和文件夹）
        String[] files = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        for (String f : files) {
            System.out.println(f);
        }

        System.out.println("----------------------");
        //遍历指定文件夹下的所有文件和文件夹，输出名称和大小
        File[] files2 = file.listFiles();
        for (File f2 : files2) {
            System.out.println(f2.getName() + " --- " + f2.length());
        }

        System.out.println("----------------------");
        //遍历指定文件夹下的所有文件和文件夹，按筛选条件输出名称和大小（不包括子文件夹下的文件和文件夹）
        File[] files3 = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        });
        for (File f3 : files3) {
            System.out.println(f3.getName() + " --- " + f3.length());
        }

        System.out.println("----------------------");
        //遍历指定文件夹下的所有文件和文件夹，按筛选条件输出名称和大小（不包括子文件夹下的文件和文件夹）
        File[] files4 = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".png");
            }
        });
        for (File f4 : files4) {
            System.out.println(f4.getName() + " --- " + f4.length());
        }

        System.out.println("----------------------");
        //遍历指定文件夹下的所有文件和文件夹（包括子文件夹下的文件和文件夹），并按照先文件夹后文件的方式输出
        IteratorUtil.IteratorFile(file);

        System.out.println("----------------------");
        //求阶乘
        System.out.println(sum(5));
    }

    private static int sum(int num) {
        if (num <= 0) {
            return -1;
        }
        if (num == 1) {
            return 1;
        }
        //递归
        return num * sum(num - 1);
    }
}
