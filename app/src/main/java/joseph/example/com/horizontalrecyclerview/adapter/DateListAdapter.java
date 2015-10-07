package joseph.example.com.horizontalrecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import joseph.example.com.horizontalrecyclerview.R;
import joseph.example.com.horizontalrecyclerview.util.DateUtil;

public class DateListAdapter extends RecyclerView.Adapter<DateListAdapter.DateViewHolder> {

    public static final int ITEM_NUMBER_PER_PAGE = 3;
    List<Date> dateList = null;

    public static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView date_textview;
        LinearLayout date_layout;
        public DateViewHolder(final View parent, final View view) {
            super(view);

            date_textview = (TextView)view.findViewById(R.id.date_textview);
            date_layout = (LinearLayout)view.findViewById(R.id.date_layout);

            if (parent.getWidth() <= 0) {
                parent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) date_textview.getLayoutParams();
                        params.width = (parent.getWidth() > 0) ? parent.getWidth()/ITEM_NUMBER_PER_PAGE : params.width;
                    }
                });
                return;
            }

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)date_textview.getLayoutParams();
            params.width = parent.getWidth()/ITEM_NUMBER_PER_PAGE;
        }
    }

    public DateListAdapter(List<Date> data) {
        dateList = data;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_list_item, parent, false);
        DateViewHolder dateViewHolder = new DateViewHolder(parent, view);
        return dateViewHolder;
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        Date date = dateList.get(position);
        String day_of_week = new SimpleDateFormat("EE").format(date);
        String date_text = day_of_week + "\n" + DateUtil.format(date, DateUtil.DATE_FORMAT_MDD);
        holder.date_textview.setText(date_text);
        holder.date_textview.setTag(date);
    }


    @Override
    public int getItemCount() {
        return dateList.size();
    }
}
