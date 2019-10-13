package osmandroid.venturesity.helloworld3o;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private List<String> titleList;
    private List<Boolean> checkedList;
    private Context context;

    public TasksAdapter(List<String> titleList,List<Boolean> checkedList,Context context)
    {
        this.titleList = titleList;
        this.checkedList = checkedList;
        this.context = context;

    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task, parent, false);
        return new TaskViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, final int position) {

        holder.textView.setText(titleList.get(position));
        holder.checkBox.setChecked(checkedList.get(position));

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedList.set(position,!checkedList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public void calculateResults() {

        int score = 0;
        for(int i=0;i<checkedList.size();i++)
        {
            if(checkedList.get(i))
            {
                score++;
            }
        }
        showDialog(String.valueOf(score));

    }

    void showDialog(String score)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.m_dialog);
        dialog.setCanceledOnTouchOutside(false);

        Button close = dialog.findViewById(R.id.close);

        TextView tv = dialog.findViewById(R.id.result_tv);

        tv.setText("You have scored "+score+" points");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {


        TextView textView;
        CheckBox checkBox;

        public TaskViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.titleTextView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

    }
}





