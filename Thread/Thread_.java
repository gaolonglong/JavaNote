package com.gaolonglong.net;

import java.util.LinkedList;

/**
 * Created by gaohailong on 2017/4/5.
 */
public class Thread_ {
    public static void main(String[] args) {

        //两个窗口同时卖票，继承Thread是各卖5张，而实现Runnable接口是两个窗口共同卖5张票
        //Thread
        ThreadTicket thread1 = new ThreadTicket("一号窗口");
        ThreadTicket thread2 = new ThreadTicket("二号窗口");
        thread1.start();
        thread2.start();
        //Runnable
        RunnableTicket runnableTicket = new RunnableTicket();
        Thread thread3 = new Thread(runnableTicket, "一号窗口");
        Thread thread4 = new Thread(runnableTicket, "二号窗口");
        thread3.start();
        thread4.start();

        //分别在柜台取钱和ATM取钱的同步问题（synchronized）
        Bank bank = new Bank();
        //柜台取钱
        BankThread bankThread1 = new BankThread(bank);
        //ATM取钱
        BankThread bankThread2 = new BankThread(bank);
        bankThread1.start();
        bankThread2.start();

        //死锁问题
        DieDemo dieDemo = new DieDemo();
        DieThread dieThread1 = new DieThread(dieDemo, true);
        DieThread dieThread2 = new DieThread(dieDemo, false);
        dieThread1.start();
        dieThread2.start();

        //生产者、消费者问题
        Basket basket = new Basket();
        //生产者生产
        Producer producer = new Producer(basket);
        //消费者消费
        Customer customer = new Customer(basket);
        producer.start();
        customer.start();

        //这个写法的含义
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //todo
            }
        });
        /*表示Thread接收一个实现Runnable接口的类作为参数;
        但 new Runnable() 在java语法上不成立，因为接口不能被实例化,
        改成new Runnable(){主体} 表示一个实现了Runnable接口的类，这个类和传统的类定义不一样
        如class A implements Runnable(){主体}  不一样，它没有类名
        因此叫做匿名类（全称是匿名内部类），因为它只在本作用域中有效，属于一个类，即内部类
        想查看结果，在编译后的目录下，可以看到一个  主类名$匿名类名.class的文件
        匿名类名 由编译器给出。
        参考：https://zhidao.baidu.com/question/2204566402120511828.html?qbl=relate_question_0&word=new%20Runnable
        https://zhidao.baidu.com/question/401096048.html?qbl=relate_question_2&word=new%20Runnable */

        /*使用匿名内部类创建线程有两种方式，和创建一个线程是一样的。*/
        //1.表示一个继承了Thread类的子类
        new Thread() {
            @Override
            public void run() {
                //todo
            }
        }.start();

        //2.表示一个实现了Runnable接口的类，作为参数传递给Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //todo
            }
        }).start();
    }
}

class Customer extends Thread {
    private Basket basket;
    public Customer(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        basket.popApple();
    }
}

class Producer extends Thread {
    private Basket basket;
    public Producer(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void run() {
        basket.pushApple();
    }
}

class Basket {
    LinkedList<Apple> appleList = new LinkedList<>();

    public synchronized void pushApple() {
        for (int i = 0; i < 20; i++) {
            Apple apple = new Apple(i);
            push(apple);
        }
    }

    public synchronized void popApple() {
        for (int i = 0; i < 20; i++) {
            pop();
        }
    }

    private void push(Apple apple) {
        if (appleList.size() == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        appleList.addFirst(apple);
        System.out.println("放入" + apple);
        notify();
    }

    private void pop() {
        if (appleList.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Apple apple = appleList.removeFirst();
        System.out.println("吃掉" + apple);
        notify();
    }
}

class Apple {
    private int id;
    public Apple(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "苹果：" + (id + 1);
    }
}

//死锁问题
class DieThread extends Thread {
    private DieDemo dieDemo;
    private boolean b;

    public DieThread(DieDemo dieDemo, boolean b) {
        this.dieDemo = dieDemo;
        this.b = b;
    }

    @Override
    public void run() {
        if (b) {
            dieDemo.method1();
        } else {
            dieDemo.method2();
        }
    }
}

class DieDemo {
    Object object1 = new Object();
    Object object2 = new Object();

    public void method1() {
        synchronized (object1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object2) {
                System.out.println("method1");
            }
        }
    }

    public void method2() {
        synchronized (object2) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (object1) {
                System.out.println("method2");
            }
        }
    }
}

//分别在柜台取钱和ATM取钱的同步问题（synchronized）
class BankThread extends Thread {
    private Bank bank;

    public BankThread(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        System.out.println("取钱：" + bank.getMoney(400));
    }
}

class Bank {
    private int money = 500;

    public synchronized int getMoney(int number) {
        if (number < 0) {
            return -1;
        } else if (money < 0) {
            return -2;
        } else if (number - money > 0) {
            return -3;
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            money -= number;
            System.out.println("余额：" + money);
            return number;
        }
    }
}

//两个窗口同时卖票，继承Thread是各卖5张，而实现Runnable接口是两个窗口共同卖5张票
//Thread
class ThreadTicket extends Thread {
    private int ticket = 5;
    public ThreadTicket(String name) {
        super(name);
    }
    @Override
    public void run() {
        while (true) {
            if (ticket == 0) {
                break;
            }
            System.out.println(getName() + "：" + ticket--);
        }
    }
}

//Runnable
class RunnableTicket implements Runnable {
    private int ticket = 5;
    @Override
    public void run() {
        while (true) {
            if (ticket == 0) {
                break;
            }
            System.out.println(Thread.currentThread().getName() + "：" + ticket--);
        }
    }
}

