package view;

import uefs.ComumBase.classes.AVLTree;

public class Main {

    public static void main(String[] args) {
        final AVLTree<Integer,String> x = new AVLTree<>((aDouble, anotherDouble) -> anotherDouble.compareTo(aDouble));
        for(int i = 0; i < 100; i++) {
            x.put(i,"");
        }
        System.out.println("foi");
        x.forEach(entry -> {
            System.out.println(entry.getKey());
        });
        
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
