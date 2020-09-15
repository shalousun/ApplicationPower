package com.power.common.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author yu 2020/8/18.
 */
public class Test {

    public static void main(String[] args) {
       countQueue(100,3);
    }


    public static void countQueue(int personNumber,int number){
        //1.把人放到队列中
        Queue<Integer> persons =  new LinkedList<Integer>();
        for (int i = 0; i < personNumber; i++) {
            persons.add(i+1);
        }

        //2.算法
        int counts = 0;//计数器
        while (!persons.isEmpty()&& persons.size()>2) {
            //1.出队列
            Integer person = persons.poll();
            //2.计数器++
            counts++;
            //3.判断
            if (counts % number == 0) {
                //是,打印
              // System.out.println(person);
            } else {
                //不是,继续入队列
                persons.add(person);
            }
            if(counts==100){
                persons.forEach(i->System.out.println(i));
            }
        }
       // persons.forEach(i->System.out.println(i));
    }

}
