package com.pespurse.global.util;

import org.apache.commons.lang.math.*;

import java.util.Date;

public class IdGenerator {
    private static IdGenerator idGenerator = null;

    private IdGenerator(){}

   public static IdGenerator getInstance() {
        if(idGenerator == null) {
            idGenerator = new IdGenerator();
        }
        return idGenerator;
   }

   public long getID() {
        Date date = new Date();
        String time = "" + date.getTime();
        String nextInt = "" + RandomUtils.nextInt(99999);

        String id = time + nextInt;
        return Long.parseLong(id);
   }
}
