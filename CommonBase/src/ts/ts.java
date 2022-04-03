/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import interfaces.ServerConnection;
import java.io.IOException;

/**
 *
 * @author evert
 */
public class ts {

    public static void main(String[] args) throws IOException {
        ServerConnection sc = ServerConnection.builder(1997);
        sc.inputStreamFuture().then(singleStream -> {
            System.out.println(singleStream.readUTF());
        });
    }

}
