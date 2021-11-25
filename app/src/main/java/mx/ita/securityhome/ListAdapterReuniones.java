package mx.ita.securityhome;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListAdapterReuniones extends RecyclerView.Adapter<ListAdapterReuniones.ViewHolder>{
    private List<ListElementReuniones> mData;
    private LayoutInflater mInflater;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private Context context;
    public ListAdapterReuniones(Context context){
        this.context = context;
    }
    public ListAdapterReuniones(List<ListElementReuniones> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public ListAdapterReuniones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_reunion, null);
        return new ListAdapterReuniones.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapterReuniones.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElementReuniones> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView hour;
        ImageButton edit, delete, share;
        String id;

        ViewHolder(View ItemView){
            super(ItemView);
            date = ItemView.findViewById(R.id.labelFechaReunion);
            hour = ItemView.findViewById(R.id.labelHoraReunion);
            share = ItemView.findViewById(R.id.btnShareQRReunion);
            delete = ItemView.findViewById(R.id.btnDeleteCardReunion);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("reuniones").child(id).removeValue();
                    context.startActivity(new Intent(context,invitadoslista.class));
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("send",id+"");
                    Intent i = new Intent(context, screenshot_reunion.class);
                    i.putExtra("id_reunion", id);
                    i.putExtra("fecha", date.getText().toString());
                    i.putExtra("hora", hour.getText().toString());
                    context.startActivity(i);
                }
            });
            edit = ItemView.findViewById(R.id.btnEditCardReunion);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Log.i("id",id);
                        Intent i=new Intent(context, editarReunion.class);
                        i.putExtra("id_reunion", id);
                        context.startActivity(i);
                }
            });
        }
        void bindData(final ListElementReuniones item){
            date.setText(item.getDate());
            hour.setText(item.getHour());
            id = item.getId();
        }
    }


}
