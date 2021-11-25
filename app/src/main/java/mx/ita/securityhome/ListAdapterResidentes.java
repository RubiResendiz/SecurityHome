package mx.ita.securityhome;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListAdapterResidentes extends RecyclerView.Adapter<ListAdapterResidentes.ViewHolder> {
    private List<ListElementResidentes> mData;
    private LayoutInflater mInflater;
    private Context context;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    public ListAdapterResidentes(Context context) {
        this.context = context;
    }

    public ListAdapterResidentes(List<ListElementResidentes> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapterResidentes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_residente, null);
        return new ListAdapterResidentes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterResidentes.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElementResidentes> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        String idd;
        ImageView imagencardresidente;
        TextView txtnomrescard;
        TextView txtcorreorescard;
        ImageButton btnEditar;
        ImageButton btnEliminar;

        ViewHolder(View ItemView) {
            super(ItemView);
            imagencardresidente = ItemView.findViewById(R.id.imagencardresidente);
            txtnomrescard = ItemView.findViewById(R.id.txtnomrescard);
            txtcorreorescard = ItemView.findViewById(R.id.txtcorreorescard);
            btnEliminar = ItemView.findViewById(R.id.btnDeleteCardRes);
            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth = FirebaseAuth.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("residentes").child(idd).removeValue();
                    context.startActivity(new Intent(context,gestionResidentes.class));
                }
            });
            btnEditar = ItemView.findViewById(R.id.btnEditCardRes);
            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("send",idd+"");
                    Intent i = new Intent(context,editarResidente.class);
                    i.putExtra("id_residente", idd);
                    context.startActivity(i);
                }
            });

        }

        void bindData(final ListElementResidentes item) {
            idd = item.getidd();
            txtnomrescard.setText(item.getnomrescard());
            txtcorreorescard.setText(item.getcorreorescard());
            imagencardresidente.setImageBitmap(item.getimagencardresidente());
        }
    }
}
