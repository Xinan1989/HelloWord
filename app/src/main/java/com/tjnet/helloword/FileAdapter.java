package com.tjnet.helloword;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHodler> {
    private LayoutInflater mInflater;
    private Bitmap mIcon1;
    private Bitmap mIcon2;
    private Bitmap mIcon3;
    private Bitmap mIcon4;
    private List<String> items;
    private List<String> paths;
    private Context mContext;

    public FileAdapter(Context context, List<String> itemsList, List<String> pathList) {
      this.items=itemsList;
      this.paths=pathList;
      this.mContext=context;
        mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_back);
        mIcon2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_back02);
        mIcon3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_fodler);
        mIcon4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_file);
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.file_item,parent,false);
        ViewHodler holder = new ViewHodler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        File f = new File(paths.get(position).toString());
        if (items.get(position).toString().equals("b1")) {
            holder.text.setText("返回根目录..");
            holder.icon.setImageBitmap(mIcon1);
        } else if (items.get(position).toString().equals("b2")) {
            holder.text.setText("返回上一层..");
            holder.icon.setImageBitmap(mIcon2);
        } else {
            holder.text.setText(f.getName());
            if (f.isDirectory()) {
                holder.icon.setImageBitmap(mIcon3);
            } else {
                holder.icon.setImageBitmap(mIcon4);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{
        TextView text;
        ImageView icon;
        public ViewHodler(View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);
            icon=itemView.findViewById(R.id.icon);

        }
    }
}
