package com.bernevek.trim11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 * @author ivan
 */
public class MyMain {
    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] command = null;
        while (true) {
            System.out.println("Enter comand");
            System.out.println("To show all comands enter \"showAll\"");
            try {
                command = bufferedReader.readLine().split(" ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (command[0].equals(Commands.SHOW_ALL)) {
                System.out.println(Commands.ECHO);
                System.out.println(Commands.EXIT);
                System.out.println(Commands.FILE);
                System.out.println(Commands.LIST);
                System.out.println(Commands.LOGIN);
                System.out.println(Commands.MSG);
                System.out.println(Commands.PING);
            } else if (command[0].equals(Commands.ECHO.split(" ")[0]) ||
                    command[0].equals(Commands.LOGIN.split(" ")[0]) ||
                    command[0].equals(Commands.MSG.split(" ")[0]) ||
                    command[0].equals(Commands.FILE.split(" ")[0])) {
                printComandWithParametrs(command);
            } else if (command[0].equals(Commands.PING) ||
                    command[0].equals(Commands.LIST)) {
                printComandWithOutParametrs(command);
            } else if (command[0].equals(Commands.EXIT)) {
                printComandWithOutParametrs(command);
                break;
            } else {
                System.out.println("command not found");
            }

        }

    }

    public static void printComandWithParametrs(String[] command) {
        System.out.print("Entered command = \"");
        System.out.print(command[0]);
        System.out.print("\", parameters = [\" ");
        for (int i = 1; i < command.length; i++) {
            System.out.print(command[i] + " ");
        }
        System.out.println("\"]");
    }

    public static void printComandWithOutParametrs(String[] command) {
        System.out.print("Entered command = \"");
        System.out.print(command[0]);
        System.out.println("\"");
    }

}
