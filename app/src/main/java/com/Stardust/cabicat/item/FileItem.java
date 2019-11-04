package com.Stardust.cabicat.item;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileItem {
    private String name;
    private String path;
    private Long filesize;
    private String addeddate;
    private String checkdate;
    private int priority;
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FileItem(String name, String path, Long filesize){
        this.name = name;
        this.path = path;
        this.filesize = filesize;
        this.addeddate = dateformat.format(System.currentTimeMillis());
    }


    public String getName(){
        return name;
    }

    public String getPath(){
        return path;
    }

    public Long getFilesize(){
        return filesize;
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
