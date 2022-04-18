package view;

import control.MainController;
import java.io.IOException;
import uefs.ComumBase.interfaces.Factory;

public class Main {

    public static void main(final String[] args) {
        Factory.thread(() -> {
            while (true) {
                try {
                    MainController.getInstance().listenToGarbageTruck(System.out::println);
                } catch (final IOException ex) {
                    
                }
            }
        }).start();
        Factory.thread(() -> {
            while (true) {
                try {
                    MainController.getInstance().listenToAdministrators(System.out::println);
                } catch (final IOException ex) {
                    
                }
            }
        }).start();
        Factory.thread(() -> {
            while (true) {
                try {
                    MainController.getInstance().listenToRecycleBins(System.out::println);
                } catch (final IOException ex) {
                   
                }
            }
        }).start();
    }

}
