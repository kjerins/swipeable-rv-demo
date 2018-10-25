package lv.kjerins.swipervdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.SuperViewHolder> {

    static class SuperViewHolder extends RecyclerView.ViewHolder {

        SuperViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class HeaderViewHolder extends SuperViewHolder {

        TextView header;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.textView2);
        }
    }

    static class ViewHolder extends SuperViewHolder {

        TextView textView;
        ViewGroup mainPanel;
        ImageButton yesButton;
        ImageButton noButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            mainPanel = itemView.findViewById(R.id.foreground_panel);
            yesButton = itemView.findViewById(R.id.imageView);
            noButton = itemView.findViewById(R.id.imageView3);
        }
    }

    private static final int ITEM_TYPE_HEADER = 1;
    private static final int ITEM_TYPE_ITEM = 2;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<String> strings;

    Adapter(List<String> strings) {
        this.strings = strings;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public int getItemViewType(int position) {
        String item = strings.get(position);
        if (item.length() == 3) {
            return ITEM_TYPE_ITEM;
        }
        return ITEM_TYPE_HEADER;
    }

    @NonNull
    @Override
    public SuperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        if (itemType == ITEM_TYPE_ITEM) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_item, viewGroup, false));
        } else {
            return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_header, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SuperViewHolder viewHolder, int i) {
        String dataObject = strings.get(i);

        if (getItemViewType(i) == ITEM_TYPE_ITEM) {
            viewBinderHelper.bind((SwipeRevealLayout) viewHolder.itemView, dataObject.toString());

            ViewHolder vh = (ViewHolder) viewHolder;
            vh.textView.setText(dataObject);
            Random random = new Random();
            int r = random.nextInt(3);
            int backgroundId = 0;
            if (r == 0) {
                backgroundId = R.drawable.gradient_red;
            } else if (r == 1) {
                backgroundId = R.drawable.gradient_green;
            } else {
                backgroundId = R.drawable.gradient_blue;
            }
            vh.mainPanel.setBackgroundResource(backgroundId);
            vh.yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Pressed yes!", Toast.LENGTH_LONG).show();
                }
            });
            vh.noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Pressed no!", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            ((HeaderViewHolder) viewHolder).header.setText(dataObject);
        }
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    void saveStates(Bundle outState) {
        viewBinderHelper.saveStates(outState);
    }

    void restoreStates(Bundle inState) {
        viewBinderHelper.restoreStates(inState);
    }

}
