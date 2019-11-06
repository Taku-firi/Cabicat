package com.Stardust.cabicat.adapter;

import android.view.View;

import com.Stardust.cabicat.R;
import com.Stardust.cabicat.item.FileItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;

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
    protected void convert(final BaseViewHolder viewHolder, FileItem item) {
        viewHolder.setText(R.id.fileitem_name, item.getName())
                .setText(R.id.fileitem_path, item.getPath());

        viewHolder.getView(R.id.fileitem_more_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasySwipeMenuLayout easySwipeMenuLayout = viewHolder.getView(R.id.layout_fileitem_menu);
                easySwipeMenuLayout.resetStatus();
            }
        });

        viewHolder.getView(R.id.fileitem_delete_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasRemoved = false;
                int pos = viewHolder.getAdapterPosition();

//                Database operation here :
//                mDatabase.deleteFileItem(getData().get(pos));
                hasRemoved = true;

                if (hasRemoved){
                    remove(pos);
                    notifyItemRemoved(pos);
                }
            }
        });

    }
}
