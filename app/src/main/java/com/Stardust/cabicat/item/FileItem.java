package com.Stardust.cabicat.item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class FileItem {

    @PrimaryKey(autoGenerate = true)
    private String path;

    @ColumnInfo(name = "file_name")
    private String name;

    @ColumnInfo(name = "file_size")
    private Long filesize;

    @ColumnInfo(name = "added_date")
    private String addeddate;

    @ColumnInfo(name = "check_date")
    private String checkdate;

    @ColumnInfo()
    private int priority;
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    String dateStr = dataformat.format(date);

    public FileItem(String name, String path, Long filesize){
        this.name = name;
        this.path = path;
        this.filesize = filesize;
        this.addeddate = dateformat.format(System.currentTimeMillis());
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilesize(Long filesize) {
        this.filesize = filesize;
    }

    public void setAddeddate(String addeddate) {
        this.addeddate = addeddate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public SimpleDateFormat getDateformat() {
        return dateformat;
    }

    public void setDateformat(SimpleDateFormat dateformat) {
        this.dateformat = dateformat;
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
