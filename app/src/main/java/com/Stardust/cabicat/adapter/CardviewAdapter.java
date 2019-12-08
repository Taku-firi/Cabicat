package com.Stardust.cabicat.adapter;

import android.view.View;
import android.widget.Toast;

import com.Stardust.cabicat.R;
import com.Stardust.cabicat.helper.DatabaseHelper;
import com.Stardust.cabicat.helper.OpenFileUtil;
import com.Stardust.cabicat.item.FileItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CardviewAdapter extends BaseQuickAdapter<FileItem, BaseViewHolder> {
    private DatabaseHelper mDatabase;

    public CardviewAdapter(int layoutResId, List<FileItem> data, DatabaseHelper db){
        super(layoutResId,data);
        this.mDatabase = db;
    }
    @Override
    protected void convert(final BaseViewHolder viewHolder, final FileItem item) {
        viewHolder.setText(R.id.carditem_name, item.getName())
                .setText(R.id.carditem_path, item.getPath());

        viewHolder.setBackgroundColor(R.id.carditem_view_content, mContext.getResources().getColor(R.color.home_carditem_bg));
        viewHolder.setTextColor(R.id.carditem_name,mContext.getResources().getColor(R.color.home_carditem_text));
        // click the fileitem
        viewHolder.getView(R.id.carditem_view_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFileUtil.openFileByPath(mContext,item.getPath());
                item.updateCheckdate();
                mDatabase.deleteFileItem(item.getPath(),0);
                mDatabase.createFileItem(item,0);
                System.out.println(item.getCheckdate());
            }
        });


    }
}
