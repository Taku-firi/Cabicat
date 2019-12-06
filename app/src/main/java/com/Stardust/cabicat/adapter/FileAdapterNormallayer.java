package com.Stardust.cabicat.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.Stardust.cabicat.R;
import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.helper.OpenFileUtil;
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
                OpenFileUtil.openFileByPath(mContext,item.getPath());
                item.updateCheckdate();
                mDatabase.deleteFileItem(item.getPath(),0);
                mDatabase.createFileItem(item,0);
                System.out.println(item.getCheckdate());
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

                hasDeleted = true;

                if (hasDeleted){
                    remove(pos);
                    notifyItemRemoved(pos);
                }
            }
        });

    }
}
