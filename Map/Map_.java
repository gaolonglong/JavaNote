package com.gaolonglong;

import java.util.*;

public class Map_ {

    public static void main(String[] args) {
        System.out.println("Hello Java!");

        Map<String, Student> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        //增加
        addMapData(map, scanner);
        //修改
        putMapData(map, scanner);
        //删除
        removeMapData(map, scanner);
        //查询
        queryMapDate(map);
    }

    private static void queryMapDate(Map<String, Student> map) {
        //使用keySet遍历（效率低）
        Set<String> keySet = map.keySet();
        System.out.println("所有的学生信息如下：");
        for (String key : keySet) {
            Student student = map.get(key);
            System.out.print("学号：" + key + " 姓名：" + student.getName() + " ");
        }
        System.out.println();
        System.out.println("---------------------------");

        //使用keySet和Iterator遍历
        Set<String> keySet2 = map.keySet();
        Iterator<String> iterator2 = keySet2.iterator();
        System.out.println("所有的学生信息如下2：");
        while (iterator2.hasNext()){
            String key = iterator2.next();
            Student student = map.get(key);
            System.out.print("学号：" + key + " 姓名：" + student.getName() + " ");
        }
        System.out.println();
        System.out.println("---------------------------");

        //使用entrySet遍历（推荐）
        Set<Map.Entry<String, Student>> entrySet = map.entrySet();
        System.out.println("所有的学生信息如下3：");
        for (Map.Entry<String, Student> entry : entrySet) {
            System.out.print("学号：" + entry.getKey() + " 姓名：" + entry.getValue().getName() + " ");
        }
        System.out.println();
        System.out.println("---------------------------");

        //使用Iterator遍历
        Set<Map.Entry<String, Student>> entrySet2 = map.entrySet();
        Iterator<Map.Entry<String, Student>> iterator = entrySet2.iterator();
        System.out.println("所有的学生信息如下4：");
        while (iterator.hasNext()){
            Map.Entry<String, Student> entry = iterator.next();
            System.out.print("学号：" + entry.getKey() + " 姓名：" + entry.getValue().getName() + " ");
        }
        System.out.println();
        System.out.println("---------------------------");

        //只遍历键或值
        Set<String> keySet3 = map.keySet();
        for (String key : keySet3){
            System.out.print("学号：" + key + " ");
        }
        System.out.println();
        System.out.println("---------------------------");

        Collection<Student> students = map.values();
        for (Student student : students){
            System.out.print("姓名：" + student.getName() + " ");
        }
    }

    private static void removeMapData(Map<String, Student> map, Scanner scanner) {
        while (true) {
            System.out.println("请输入要删除学生的学号：");
            String stuId = scanner.next();
            Student student = map.get(stuId);
            if (student != null) {
                System.out.println("要删除学生的姓名是：" + student.getName());
                map.remove(stuId);
            } else {
                System.out.println("学号不存在，请重新输入！");
                continue;
            }
            System.out.println("学号：" + stuId + "，学生删除完毕！");
            break;
        }
    }

    private static void putMapData(Map<String, Student> map, Scanner scanner) {
        while (true) {
            System.out.println("请输入要修改学生的学号：");
            String stuId = scanner.next();
            Student student = map.get(stuId);
            if (student != null) {
                System.out.println("要修改学生的姓名是：" + student.getName() + "，请输入新学生姓名：");
                String stuName = scanner.next();
                map.put(stuId, new Student(stuId, stuName));
            } else {
                System.out.println("学号不存在，请重新输入！");
                continue;
            }
            System.out.println("学号：" + stuId + "，学生信息修改完毕！" + "新名字：" + map.get(stuId).getName());
            break;
        }
    }

    private static void addMapData(Map<String, Student> map, Scanner scanner) {
        System.out.println("***欢迎来到学生信息录入系统！***");
        int i = 0;
        while (i < 3) {
            System.out.println("请输入第" + (i + 1) + "个学生的学号：");
            String stuId = scanner.next();
            Student student = map.get(stuId);
            if (student == null) {
                System.out.println("请输入学生的姓名：");
                String stuName = scanner.next();
                map.put(stuId, new Student(stuId, stuName));
            } else {
                System.out.println("学生的学号已存在，请重新输入！");
                continue;
            }
            i++;
        }
        System.out.println("***恭喜你学生信息录入完毕！***");
    }
}
