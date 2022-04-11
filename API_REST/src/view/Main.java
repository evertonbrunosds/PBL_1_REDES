package view;

import java.io.IOException;

import control.MainController;
import uefs.ComumBase.interfaces.Factory;

public class Main {

    public static void main(String[] args) {
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
    }

}
