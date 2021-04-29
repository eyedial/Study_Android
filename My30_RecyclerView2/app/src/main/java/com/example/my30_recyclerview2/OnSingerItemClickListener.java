package com.example.my30_recyclerview2;

import android.view.View;

public interface OnSingerItemClickListener {
    public void onItemClick(SingerAdapter.ViewHolder holderm,
                            View view, int position);
}
