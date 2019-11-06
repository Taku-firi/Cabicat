package com.Stardust.cabicat.adapter;

import android.view.View;
import android.widget.Toast;

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
    protected void convert(final BaseViewHolder viewHolder, final FileItem item) {
        viewHolder.setText(R.id.fileitem_name, item.getName())
                .setText(R.id.fileitem_path, item.getPath());


        // click the fileitem
        viewHolder.getView(R.id.fileitem_view_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"You clicked" + item.getName(),Toast.LENGTH_SHORT).show();
            }
        });

        // click transfer in the left sidebar
        viewHolder.getView(R.id.fileitem_transfer_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"You clicked transfer" ,Toast.LENGTH_SHORT).show();

            }
        });


        // click remove in the right sidebar
        viewHolder.getView(R.id.fileitem_remove_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasySwipeMenuLayout easySwipeMenuLayout = viewHolder.getView(R.id.layout_fileitem_menu);
                easySwipeMenuLayout.resetStatus();

                Toast.makeText(view.getContext(),"You clicked remove" ,Toast.LENGTH_SHORT).show();
            }
        });


        // click delete in the right sidebar
        viewHolder.getView(R.id.fileitem_delete_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"You clicked delete" ,Toast.LENGTH_SHORT).show();

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
