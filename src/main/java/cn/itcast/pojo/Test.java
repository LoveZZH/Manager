package cn.itcast.pojo;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Test {
    public static void main(String[] args) {
//        HashMap<Object, Object> map = new HashMap<>();
//        map.put("张三","李四");
//        Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            System.out.println(iterator.next());
//        }

//        ArrayList<Object> list = new ArrayList<>();
//        list.add("张");
//        list.add("王");
//        Iterator<Object> iterator1 = list.iterator();
//        while (iterator1.hasNext()){
//            System.out.println(iterator1.next());
//        }
//        for (Object o:list) {
//            System.out.println(o);
//        }

        String str="abcjavadefjavadddjavc";
        char newcahr='c';
        int count=0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i)==newcahr){
                count++;
            }
        }
        System.out.println(count);


        ExecutorService executorService = Executors.newFixedThreadPool(10);



    }
    
    
}
