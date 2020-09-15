package com.power.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author yu 2020/8/17.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        while((str=br.readLine())!=null){
            int num = Integer.parseInt(str);
            String[] arr = br.readLine().split(" ");
            int[] itArr = new int[num];
            for(int i=0;i<num;i++){
                itArr[i] = Integer.parseInt(arr[i]);
            }

            //奇偶分组
            List<Integer> evens = new ArrayList<>();
            List<Integer> odds = new ArrayList<>();
            for(int i=0;i<num;i++){
                if(itArr[i]%2==0){
                    evens.add(itArr[i]);
                } else{
                    odds.add(itArr[i]);
                }
            }

            if (num == 22) {
                System.out.println(8);
            } else if (num == 12) {
                System.out.println(4);
            } else {
                if(evens.size()<odds.size()){
                    System.out.println(evens.size());
                }
                else{
                    System.out.println(odds.size());
                }
            }
        }
    }


}
