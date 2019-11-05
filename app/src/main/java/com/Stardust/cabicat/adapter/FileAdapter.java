package com.Stardust.cabicat.adapter;

import com.Stardust.cabicat.R;
import com.Stardust.cabicat.item.FileItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class FileAdapter extends BaseQuickAdapter<FileItem, BaseViewHolder> {
//    private DatabaseHelper mDatabase;

//    public FileAdapter(DatabaseHelper mDatabase, int layoutResId, List<FileItem> data){
//        super(layoutResId,data);
//    }
    public FileAdapter(int layoutResId, List<FileItem> data){
        super(layoutResId,data);
    }
    @Override
    protected void convert(BaseViewHolder viewHolder, FileItem item) {
        viewHolder.setText(R.id.fileitem_name, item.getName())
                .setText(R.id.fileitem_path, item.getPath());

    }
}
