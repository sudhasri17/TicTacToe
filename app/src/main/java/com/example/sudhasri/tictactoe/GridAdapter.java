package com.example.sudhasri.tictactoe;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sudhasri.tictactoe.data.Sign;

import java.util.ArrayList;

/**
 * Created by sudhasri on 26/8/16.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.TileHolder>
{
    private Context mContext;
    private ArrayList<Sign> mData;
    private ViewPresenterContract.Presenter mPresenter;
    private ArrayList<Integer> mWinningGrid;

    GridAdapter(Context context, ArrayList<Sign> data, ViewPresenterContract.Presenter presenter)
    {
        mContext  = context;
        mData = data;
        mPresenter = presenter;
        mWinningGrid = new ArrayList<>();
    }

    @Override
    public TileHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent,false);

        TileHolder holder = new TileHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(TileHolder holder, int position) {
        holder.mPosition = position;
        if (getITem(position )!= null)
        {
            holder.mSignText.setText(getITem(position).getValue());
            holder.mSignText.setTextColor(ContextCompat.getColor(mContext, R.color.black_overlay));
        }

        if (mWinningGrid != null && mWinningGrid.size() > 0)
        {
            for (int grid: mWinningGrid)
            {
                if (grid == position) {
                    holder.mSignText.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Sign getITem(int position)
    {
        if (mData != null)
            return mData.get(position);
        else
            return null;
    }

    public void setData(ArrayList<Sign> data)
    {
        mData = data;
    }

    public void setWinningGrids(ArrayList<Integer> winningGrid)
    {
        if (mWinningGrid != null)
            mWinningGrid.clear();
        mWinningGrid = winningGrid;
        notifyDataSetChanged();
    }

    public class TileHolder extends RecyclerView.ViewHolder
    {
        private TextView mSignText;
        private int mPosition;
        public TileHolder(View itemView)
        {
            super(itemView);
            mSignText = (TextView) itemView.findViewById(R.id.signText);
            int width = itemView.getMeasuredWidth();
//            itemView.getLayoutParams().height = 300;

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mSignText.getText().toString().equals(" "))
                    {
                        if ((mPresenter.getCurrentPlayer() != mPresenter.getSystemPlayer()) && (mWinningGrid == null || mWinningGrid.size() == 0)) {
                            Sign currentSign = mPresenter.getCurrentPlayer().getPlayerSign();
                            String text = currentSign.getValue();
                            mSignText.setText(text);
                            mPresenter.updateData(mPosition, currentSign);
                        } else {
                            //don't accept input
                        }
                    }
                    return true;
                }
            });
        }
    }
}
