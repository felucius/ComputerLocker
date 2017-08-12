package maximedelange.computerlocker.Domain;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.StrictMode;
/**
 * Created by M on 8/11/2017.
 */

public class ClientLocker {
    // Fields

    // Constructor
    public ClientLocker(){

    }

    // Methods
    public void getPermission(){
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void makeConnection(Integer number){
        // IP address.
        String serverAddress = "192.168.2.11";
        try {
            Socket socket = new Socket(serverAddress, 8888);

            // Sending message to the server if the connection has been established.
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("You are connected.");

            // Sending the number of the action to the server.
            switch(number){
                case 1:
                    // Number 1 is locking the computer.
                    writer.println(1);
                    break;
                case 2:
                    // Number 2 is unlocking the computer.
                    writer.println(2);
                    break;
            }
        }catch (IOException ioEx){
            ioEx.printStackTrace();
        }
    }
}
