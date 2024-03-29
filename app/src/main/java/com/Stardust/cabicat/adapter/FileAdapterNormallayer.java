package com.Stardust.cabicat.adapter;


import android.view.View;
import android.widget.Toast;

import com.Stardust.cabicat.R;
import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.helper.FileOperateUtil;
import com.Stardust.cabicat.item.FileItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class FileAdapterNormallayer extends BaseQuickAdapter<FileItem, BaseViewHolder> {
    private DatabaseHelper mDatabase;

    public FileAdapterNormallayer(int layoutResId, List<FileItem> data, DatabaseHelper db){
        super(layoutResId,data);
        this.mDatabase = db;
    }
    @Override
    protected void convert(final BaseViewHolder viewHolder, final FileItem item) {
        viewHolder.setText(R.id.fileitem_name, item.getName())
                .setText(R.id.fileitem_path, item.getPath());


        // click the fileitem
        viewHolder.getView(R.id.fileitem_view_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileOperateUtil.openFileByPath(mContext,item.getPath());
                item.updateCheckdate();
                int cTime = item.getTimes() + 1;
                item.updateTime(cTime);
                int pri = (int)Math.log(item.getFilesize().doubleValue()+ 1)*cTime;
                item.setPriority(pri);
                mDatabase.deleteFileItem(item.getPath(),0);
                mDatabase.createFileItem(item,0);
            }
        });

        // click transfer in the left sidebar
        viewHolder.getView(R.id.fileitem_transfer_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"You clicked transfer" ,Toast.LENGTH_SHORT).show();

                boolean hasTransfered = false;
                int pos = viewHolder.getAdapterPosition();

                // Database operation here :
                mDatabase.createFileItem(item,1);
                mDatabase.deleteFileItem(item.getPath(),0);

                hasTransfered = true;

                if (hasTransfered){
                    remove(pos);
                    notifyItemRemoved(pos);
                }

            }
        });


        // click remove in the right sidebar
        viewHolder.getView(R.id.fileitem_remove_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"You clicked remove" ,Toast.LENGTH_SHORT).show();

                boolean hasRemoved = false;
                int pos = viewHolder.getAdapterPosition();

                //  Database operation here :
                mDatabase.deleteFileItem(item.getPath(),0);

                hasRemoved = true;

                if (hasRemoved){
                    remove(pos);
                    notifyItemRemoved(pos);
                }
            }
        });


        // click delete in the right sidebar
        viewHolder.getView(R.id.fileitem_delete_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"You clicked delete" ,Toast.LENGTH_SHORT).show();

                boolean hasDeleted = false;
                int pos = viewHolder.getAdapterPosition();

                //  Database operation here :
                mDatabase.deleteFileItem(item.getPath(),0);
                //  Delete file here :
                FileOperateUtil.deleteFileByPath(mContext,item.getPath());
                hasDeleted = true;

                if (hasDeleted){
                    remove(pos);
                    notifyItemRemoved(pos);
                }
            }
        });

    }
}
