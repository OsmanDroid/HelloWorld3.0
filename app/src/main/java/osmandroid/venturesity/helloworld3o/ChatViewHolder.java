package osmandroid.venturesity.helloworld3o;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatViewHolder extends RecyclerView.ViewHolder  {



    TextView leftText,rightText;
    Button btnIntent;
    CircleImageView doctor,user;

    public ChatViewHolder(View itemView){
        super(itemView);

        leftText = itemView.findViewById(R.id.leftText);
        rightText = itemView.findViewById(R.id.rightText);
        btnIntent = itemView.findViewById(R.id.extra);

        doctor = itemView.findViewById(R.id.doctor_imgview);
        user = itemView.findViewById(R.id.user_imgview);


    }
}