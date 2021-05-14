package com.example.my40_anmulti_recyclerview_190820;

import android.view.View;

import com.example.my40_anmulti_recyclerview_190820.Adapter.MyRecyclerviewAdapter;

public interface OnItemClickListener {
    public void onItemClick(MyRecyclerviewAdapter.ItemViewHolder holder, View view, int position);
}
