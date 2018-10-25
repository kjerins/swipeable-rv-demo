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

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

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

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    private List<String> strings;

    Adapter(List<String> strings) {
        this.strings = strings;
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String dataObject = strings.get(i);

        viewBinderHelper.bind((SwipeRevealLayout) viewHolder.itemView, dataObject.toString());

        viewHolder.textView.setText(dataObject);
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
        viewHolder.mainPanel.setBackgroundResource(backgroundId);
        viewHolder.yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Pressed yes!", Toast.LENGTH_LONG).show();
            }
        });
        viewHolder.noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Pressed no!", Toast.LENGTH_LONG).show();
            }
        });
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
