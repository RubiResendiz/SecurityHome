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

public class ListAdapterInvitados extends RecyclerView.Adapter<ListAdapterInvitados.ViewHolder>{
    private List<ListElementInvitados> mData;
    private LayoutInflater mInflater;
    private Context context;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    public ListAdapterInvitados(Context context){
        this.context = context;
    }
    public ListAdapterInvitados(List<ListElementInvitados> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_invitados, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElementInvitados> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView telefono;
        ImageButton edit, share, delete;
        String id;

        ViewHolder(View ItemView){
            super(ItemView);
            nombre = ItemView.findViewById(R.id.labelNombreInvitado);
            telefono = ItemView.findViewById(R.id.labelTelefonoInvitado);
            share = ItemView.findViewById(R.id.btnShareQR);
            delete = ItemView.findViewById(R.id.btnDeleteCard);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("invitados").child(id).removeValue();
                    context.startActivity(new Intent(context,invitadoslista.class));
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("send",id+" "+nombre.getText().toString());
                    Intent i = new Intent(context, Screenshot.class);
                    i.putExtra("id_invitado", id);
                    i.putExtra("nombre", nombre.getText().toString());
                    context.startActivity(i);
                }
            });
            edit = ItemView.findViewById(R.id.btnEditCard);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Log.i("id",id);
                        Intent i=new Intent(context, editarinvitado.class);
                        i.putExtra("id_invitado", id);
                        context.startActivity(i);
                }
            });
        }
        void bindData(final ListElementInvitados item){
            nombre.setText(item.getName());
            telefono.setText(item.getTelefono());
            id = item.getId();
        }
    }


}
