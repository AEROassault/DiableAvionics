/*
By Tartiflette
 */
package org.diableAvionics.util;

import com.fs.starfarer.api.Global;

public class StringManager {
    private static final String ML="diableavionics";    
    
    public static String txt(String id){
        return Global.getSettings().getString(ML, id);
    }
}