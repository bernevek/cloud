package com.bernevek.trim11;

/**
 * Created by ivan on 24.02.18.
 */
public interface Commands {
    public static final String SHOW_ALL="showAll";
    public static final String ECHO="echo <anyText:String>";
    public static final String PING="ping";
    public static final String LOGIN="login <login:String> <password:String>";
    public static final String LIST="list";
    public static final String MSG="msg <destinationUser:String> <text:String>";
    public static final String FILE="file <destinationUser:String> <filename:String>";
    public static final String EXIT="exit";
}
