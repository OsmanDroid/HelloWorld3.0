package osmandroid.venturesity.helloworld3o;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatViewHolder extends RecyclerView.ViewHolder  {



    TextView leftText,rightText;
    Button btnIntent;
    CircleImageView bot,user;

    public ChatViewHolder(View itemView){
        super(itemView);

        leftText = (TextView)itemView.findViewById(R.id.leftText);
        rightText = (TextView)itemView.findViewById(R.id.rightText);
        btnIntent = itemView.findViewById(R.id.extra);

        bot = itemView.findViewById(R.id.bot_imgview);
        user = itemView.findViewById(R.id.user_imgview);


    }
}