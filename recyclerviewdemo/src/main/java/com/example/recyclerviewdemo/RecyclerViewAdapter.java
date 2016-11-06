package com.example.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Mark on 2016/11/5.
 *
 * RecyclerView 的适配器类
 *
 *
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //成员变量
    private final Context  context;
    private final ArrayList<String> data;

    // 构造函数
     public RecyclerViewAdapter(Context context  , ArrayList<String> data) {
        this.context = context;
        this.data = data;
    }
    // 实现其方法
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = View.inflate(context,R.layout.item,null );
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        String data = this.data.get(i);
        viewHolder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void addData(int i, String s) {
        data.add(i,s);
        notifyItemInserted(i);
    }

    public void removeData(int i) {
        if (data.size()>0) {
            data.remove(i);
        }
        notifyItemRemoved(i);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,"data"+getLayoutPosition(),Toast.LENGTH_SHORT).show();
                    if (onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,getLayoutPosition(),data.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view,int position,String data);
    }
}
