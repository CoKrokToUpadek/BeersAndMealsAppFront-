package com.cokroktosmok.beersandmealsappfront.security;

import java.time.Instant;

//helper class for User Session key operations
public class UserKeyHandlerClass {
   static private long generatedKey=0L;
   static long hourMark= 3600L;

    public static long getGeneratedKey() {
        return generatedKey;
    }

    public static void setKey(){
       generatedKey=Instant.now().getEpochSecond();
   }

   static boolean checkIfHourPassed(Long generatedKey){
       long currentTimeValue=Instant.now().getEpochSecond();
       return generatedKey + hourMark <= currentTimeValue;
   }

}
