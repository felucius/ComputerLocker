/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package computerlockerserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Maxime de Lange
 */
public class ComputerLockerServer {

    // Fields
    private ServerSocket serverSocket = null;
    private Integer PORT_NUMBER = 8888;
    private Integer number = 0;

    /*
    Creating a connection and send a message when the client is connected.
     */
    public void createConnection() {
        // Listening to port number 888
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            while (true) {
                // Listening to a connection and accepting it when one is found.
                Socket socket = serverSocket.accept();
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String answer = input.readLine();
                    number = Integer.valueOf(input.readLine());
                    System.out.println(answer);
                    switch (number) {
                        case 1:
                            System.out.println("Locking computer.");
                            lockComputer();
                            break;
                        case 2:
                            System.out.println("Unlocking computer.");
                            unlockComputer();
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        //socket.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void lockComputer() {
        try {
            // Command to lock pc to login screen.
            //String command = "cmd /k cd C:\\WINDOWS\\system32 && rundll32.exe user32.dll, LockWorkStation && REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableTaskMgr /t REG_DWORD /d 1 /f && exit";

            // Disable taskmessenger and starting a lock screen from JavaFX.
            String command = "cmd /k start "
                    + "/b cmd2 /c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableTaskMgr /t REG_DWORD /d 1 /f "
                    + "&& C:\\MaximeProject\\ComputerLocker\\ComputerLocker\\ComputerLockScreenFX\\dist\\ComputerLockScreenFX.jar";
            Process process = Runtime.getRuntime().exec(command);
            OutputStream output = process.getOutputStream();
            output.write("cd C:/".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unlockComputer() {
        try {
            // Command to enable task manager.
            String command = "cmd /k start "
                    + "/b cmd2 /c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableTaskMgr /t REG_DWORD /d 0 /f "
                    + " && tskill javaw";
            Process process = Runtime.getRuntime().exec(command);
            OutputStream output = process.getOutputStream();
            output.write("cd C:/".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String searchIPAddress() {
        try {
            String command = "cmd /k ipconfig";
            Process process = Runtime.getRuntime().exec(command);

            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {
                if (line.contains("IPv4")) {
                    System.out.println("IPv4 Address: " + line);
                    return line;
                }  
            }
            return line;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting server");
        ComputerLockerServer locker = new ComputerLockerServer();
        locker.createConnection();

    }

}
