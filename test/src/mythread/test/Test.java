package mythread.test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Test {
    private int queueSize = 10;
    private PriorityQueue<Integer> queue = new PriorityQueue<Integer>(queueSize);
      
    public static void main(String[] args)  {
//        Test test = new Test();
//        Producer producer = test.new Producer();
//        Consumer consumer = test.new Consumer();
//          
//        producer.start();
//        consumer.start();
        
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("123", "1234");
        Map<String,String> map2 = new HashMap<String,String>(map1);
        map1.clear();
        String s = map2.get("123");
    }
      
    class Consumer extends Thread{
          
        @Override
        public void run() {
            consume();
        }
          
        private void consume() {
            while(true){
                synchronized (queue) {
                    while(queue.size() == 0){
                        try {
                            System.out.println("���пգ��ȴ�����");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.poll();          //ÿ�����߶���Ԫ��
                    queue.notify();
                    System.out.println("�Ӷ���ȡ��һ��Ԫ�أ�����ʣ��"+queue.size()+"��Ԫ��");
                }
            }
        }
    }
      
    class Producer extends Thread{
          
        @Override
        public void run() {
            produce();
        }
          
        private void produce() {
            while(true){
                synchronized (queue) {
                    while(queue.size() == queueSize){
                        try {
                            System.out.println("���������ȴ��п���ռ�");
                            queue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queue.notify();
                        }
                    }
                    queue.offer(1);        //ÿ�β���һ��Ԫ��
                    queue.notify();
                    System.out.println("�����ȡ�в���һ��Ԫ�أ�����ʣ��ռ䣺"+(queueSize-queue.size()));
                }
            }
        }
    }
}