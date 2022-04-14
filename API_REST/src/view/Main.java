package view;

import java.io.IOException;

import control.MainController;
import java.util.PriorityQueue;
import model.MyPriorityQueue;
import uefs.ComumBase.interfaces.Factory;

public class Main {

    public static void main(String[] args) {
        final MyPriorityQueue<Integer> x = new MyPriorityQueue<>((aDouble, anotherDouble) -> anotherDouble.compareTo(aDouble));
        for(int i = 0; i < 100; i++) {
            x.add(i);
        }
        System.out.println("foi");
        x.forEach(System.out::println);
        
        /*
        Factory.thread(() -> {
            while (true) {
                try {
                    MainController.getInstance().listenToAdministrators(System.out::println);
                } catch (IOException ex) {
                    
                }
            }
        }).start();
        Factory.thread(() -> {
            while (true) {
                try {
                    MainController.getInstance().listenToRecycleBins(System.out::println);
                } catch (IOException ex) {
                   
                }
            }
        }).start();
         */
    }

}
