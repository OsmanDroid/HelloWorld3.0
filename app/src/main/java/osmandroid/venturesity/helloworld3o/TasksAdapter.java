package osmandroid.venturesity.helloworld3o;

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

    public TasksAdapter(List<String> titleList,List<Boolean> checkedList)
    {
        this.titleList = titleList;
        this.checkedList = checkedList;

    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_task, parent, false);
        return new TaskViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.textView.setText(titleList.get(position));
        holder.checkBox.setChecked(checkedList.get(position));
    }

    @Override
    public int getItemCount() {
        return titleList.size();
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





