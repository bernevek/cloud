package com.bernevek.trim11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleParser extends Thread {
    private Boolean stop = false;
    private BufferedReader bufferedReader;
    private String[] command = null;
    private Protocol protocol;

    public ConsoleParser(Protocol protocol) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.protocol = protocol;
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("Enter comand");
            System.out.println("To show all comands enter \"showAll\"");
            try {
                command = bufferedReader
                        .readLine()
                        .trim()
                        .replaceAll("  ", " ")
                        .split(" ");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                switch (command[0]) {
                    case Protocol.ECHO: {
                        if (command.length == 2) {
                            protocol.echo(command[1]);
                        } else {
                            System.out.println("command is incorrect");
                        }
                        break;
                    }
                    case Protocol.EXIT: {
                        stop = true;
                        break;
                    }
                    case Protocol.SHOW_ALL: {
                        protocol.showAll();
                        break;
                    }
                    case Protocol.PING: {
                        protocol.ping();
                        break;
                    }
                    case Protocol.PROCESS: {
                        protocol.process("", "");
                        break;
                    }
                    default:
                        System.out.println("command not found");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
