package com.cokroktosmok.beersandmealsappfront.config;

import java.util.HashMap;


public class TokenStorage {
    private static HashMap<String, String> sessions = new HashMap<>();

    public static void putToken(String username,String token){
        if (sessions.containsKey(username)){
            sessions.replace(username,token);
        }else {
            sessions.put(username,token);
        }
    }

    public static String getToken(String username){
        return sessions.get(username);
    }

}
