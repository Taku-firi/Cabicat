package com.Stardust.cabicat.item;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileItem {
    private String name;
    private String path;
    private long filesize;
    private String addeddate;
    private String checkdate;
    private int times;
    private int priority;
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FileItem(){
        
    }
    // used for display
    public FileItem(String name, String path){
        this.name = name;
        this.path = path;
    }

    // used for add new fileitem to database
    public FileItem(String name, String path, long filesize){
        this.name = name;
        this.path = path;
        this.filesize = filesize;
        this.addeddate = dateformat.format(System.currentTimeMillis());
        this.times = 0;
    }

    // used while user checking the files, automatically update checked data
    public FileItem(String name,String path,long filesize,String addeddate,int priority){
        this.name = name;
        this.path = path;
        this.filesize = filesize;
        this.addeddate = addeddate;
        this.checkdate = dateformat.format(System.currentTimeMillis());
        this.priority = priority;
    }

    //used for check fileitem in database
    public FileItem(String name,String path,long filesize, String addeddate, String checkdate, int priority,int times){
        this.name = name;
        this.path = path;
        this.filesize = filesize;
        this.addeddate = addeddate;
        this.checkdate = checkdate;
        this.priority = priority;
        this.times = times;
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

    public int getTimes(){
        return times;
    }

    public void updateCheckdate(){
        this.checkdate = dateformat.format(System.currentTimeMillis());
    }

    public void setPriority(int i){
        this.priority = i;
    }

    public void updateTime(int i){
        this.times = i;
    }

}
