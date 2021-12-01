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

public class ListAdapterIR extends RecyclerView.Adapter<ListAdapterIR.ViewHolder>{
    private List<ListElementIR> mData;
    private LayoutInflater mInflater;
    private Context context;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    public ListAdapterIR(Context context){
        this.context = context;
    }
    public ListAdapterIR(List<ListElementIR> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public ListAdapterIR.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_ir, null);
        return new ListAdapterIR.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapterIR.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElementIR> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView telefono;
        TextView anfitrion;
        TextView idtext;
        ImageButton delete;
        String id;
        String tabla;
        ViewHolder(View ItemView){
            super(ItemView);
            nombre = ItemView.findViewById(R.id.txtNombreIRCard);
            telefono = ItemView.findViewById(R.id.txtTelefonoIRCard);
            anfitrion = ItemView.findViewById(R.id.txtAnfitrionIRCard);
            delete = ItemView.findViewById(R.id.btnDeleteIRCard);
            idtext = ItemView.findViewById(R.id.txtIdIRCard);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    Log.i("ID_invitado", id);
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("invitados").child(id).removeValue();
                    context.startActivity(new Intent(context,gestionInvitados.class));
                }
            });
        }
        void bindData(final ListElementIR item){
            nombre.setText(item.getName());
            telefono.setText(item.getTelefono());
            anfitrion.setText(item.getAnfitrion());
            idtext.setText("ID invitado: " +item.getId());
            id = item.getId();
            tabla = item.getTabla();
        }
    }


}
