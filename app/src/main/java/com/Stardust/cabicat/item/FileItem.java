package com.Stardust.cabicat.item;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileItem {
    private String name;
    private String path;
    private String addeddate;
    private String checkdate;
    private int priority;
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FileItem(String name, String path){
        this.name = name;
        this.path = path;
        this.addeddate = dateformat.format(System.currentTimeMillis());
    }


    public String getName(){
        return name;
    }

    public String getPath(){
        return path;
    }

    public String getAddeddate(){
        return addeddate;
    }

    public String getCheckdate(){
        return checkdate;
    }

    public int getPriority(){
        return priority;
    }

    public void updateCheckdate(){
        this.checkdate = dateformat.format(System.currentTimeMillis());
    }

    public void setPriority(int i){
        this.priority = i;
    }
}
