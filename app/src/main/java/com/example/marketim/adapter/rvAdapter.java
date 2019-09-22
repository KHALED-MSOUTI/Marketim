package com.example.marketim.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.marketim.R;
import com.example.marketim.model.jsonList;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.viewHolder> {
    final private ListItemClickListener mOnClickListener;
    private ArrayList<jsonList> list;
    private Context context;
    private int selectedPosition = -1;

    public rvAdapter(ListItemClickListener mOnClickListener, ArrayList<jsonList> list, Context context) {
        this.list = list;
        this.context = context;
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public rvAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rvAdapter.viewHolder holder, int position) {
        holder.date.setText(list.get(position).getDate());
        holder.month.setText(getMonth(Integer.parseInt(list.get(position).getMonth())));
        holder.marketName.setText(list.get(position).getMarketName());
        holder.orderName.setText(list.get(position).getOrderName());
        holder.productPrice.setText(getFinePrice(list.get(position).getProductPrice()));
        setProductState(list.get(position).getProductState(), holder.productState);
        holder.orderDetail.setText(list.get(position).getProductDetail().getOrderDetail());
        holder.summaryPrice.setText(getFinePrice(list.get(position).getProductDetail().getSummaryPrice()));
        if (position == selectedPosition) {
            int visibility = holder.extended.getVisibility();
            if (visibility == View.GONE) {
                holder.extended.setVisibility(View.VISIBLE);
            } else {
                holder.extended.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date;
        TextView month;
        TextView marketName;
        TextView orderName;
        Chip productPrice;
        Chip productState;
        TextView orderDetail;
        Chip summaryPrice;
        LinearLayout extended;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateDay);
            month = itemView.findViewById(R.id.DateMonth);
            marketName = itemView.findViewById(R.id.marketName);
            orderName = itemView.findViewById(R.id.orderName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productState = itemView.findViewById(R.id.productState);
            orderDetail = itemView.findViewById(R.id.orderDetail);
            summaryPrice = itemView.findViewById(R.id.summaryPrice);
            extended = itemView.findViewById(R.id.extended);
            itemView.setOnClickListener(this);
        }

        /**
         * Adding click listener
         */
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    /**
     * @return The name of th month based on the number of the month.
     */
    private String getMonth(int index) {
        switch (index) {
            case 1:
                return "OCAK";
            case 2:
                return "ŞUBAT";
            case 3:
                return "MART";
            case 4:
                return "NİSAN";
            case 5:
                return "MAYIS";
            case 6:
                return "HAZİRAN";
            case 7:
                return "TEMMUZ";
            case 8:
                return "AĞUSTOS";
            case 9:
                return "EYLÜL";
            case 10:
                return "EKİM";
            case 11:
                return "KASIM";
            case 12:
                return "ARALIK";

                default:
                    return context.getString(R.string.unknown);

        }
    }

    /**
     * @return The price after parsing into string then add ₺ symbol.
     */
    private String getFinePrice(double price) {
        return (price) + " ₺";
    }

    /**
     * @param state will be Checked
     * @param chip  Will be modified
     */
    private void setProductState(String state, Chip chip) {
        chip.setText(state);
        switch (state) {
            case "Yolda":
                chip.setChipIcon(context.getResources().getDrawable(R.drawable.ic_yolda));
                chip.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.green)));
                break;
            case "Hazırlanıyor":
                chip.setChipIcon(context.getResources().getDrawable(R.drawable.ic_hazirlaniyor));
                chip.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.orange)));
                break;
            case "Onay Bekliyor":
                chip.setChipIcon(context.getResources().getDrawable(R.drawable.ic_onay_bekliyor));
                chip.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));
                break;

                default:
                    chip.setText(context.getString(R.string.unknown));
                    chip.setChipIcon(context.getResources().getDrawable(R.drawable.ic_bilinmeyen));
                    chip.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.red)));

        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    public void extend(int index) {
        selectedPosition = index;
        notifyItemChanged(selectedPosition);
    }
}
