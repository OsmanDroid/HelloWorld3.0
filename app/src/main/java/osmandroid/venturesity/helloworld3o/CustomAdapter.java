package osmandroid.venturesity.helloworld3o;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ChatViewHolder> {


    List<ChatModel> chatModelList;
    Context context;

    public CustomAdapter(Context context,List<ChatModel> chatModelList) {
        this.context = context;
        this.chatModelList = chatModelList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, parent, false);
        return new ChatViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        holder.btnIntent.setVisibility(View.GONE);

        ChatModel model = chatModelList.get(position);
        if (chatModelList.get(position).getMsgUser().equals("user")) {

            holder.rightText.setText(model.getMsgText());

            holder.rightText.setVisibility(View.VISIBLE);
            holder.leftText.setVisibility(View.GONE);
            holder.doctor.setVisibility(View.GONE);
            holder.user.setVisibility(View.VISIBLE);
        }
        else {
            holder.leftText.setText(model.getMsgText());
            holder.rightText.setVisibility(View.GONE);
            holder.leftText.setVisibility(View.VISIBLE);
            holder.user.setVisibility(View.GONE);
            holder.doctor.setVisibility(View.VISIBLE);

            if(model.getMsgText().contains("https://goo.gl/maps/ycwLmRTvADRLaGvz7"))
            {
                holder.btnIntent.setVisibility(View.VISIBLE);
                holder.btnIntent.setText("VIEW ON MAP");

                holder.btnIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmmIntentUri = Uri.parse("https://goo.gl/maps/ycwLmRTvADRLaGvz7");

                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(mapIntent);
                        }else {
                            Toast.makeText(context,"No App to show Location",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }


    public void updateList(List<ChatModel> chatModelList)
    {
        this.chatModelList.clear();
        this.chatModelList.addAll(chatModelList);
        notifyDataSetChanged();
    }
}
