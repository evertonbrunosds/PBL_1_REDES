package view;

import java.io.IOException;

import control.MainController;

public class Main {
    
    public static void main(String[] args) throws IOException{
        while (true) {
            MainController.getInstance().listenToRecycleBins(System.out::println);
        }
    }
    
}
