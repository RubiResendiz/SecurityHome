package mx.ita.securityhome;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

public class ListAdapterBitacora extends RecyclerView.Adapter<ListAdapterBitacora.ViewHolder>{
    private List<ListElementBitacora> mData;
    private LayoutInflater mInflater;
    private Context context;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    public ListAdapterBitacora(Context context){
        this.context = context;
    }
    public ListAdapterBitacora(List<ListElementBitacora> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public ListAdapterBitacora.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_bitacora, null);
        return new ListAdapterBitacora.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapterBitacora.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElementBitacora> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView direccion;
        TextView fecha;
        String tipo;
        TextView id;
        ImageView tipobit;

        ViewHolder(View ItemView){
            super(ItemView);
            nombre = ItemView.findViewById(R.id.txtNombreVisitaCard);
            direccion = ItemView.findViewById(R.id.txtDireccionVisitaCard);
            fecha = ItemView.findViewById(R.id.txtFechaVisitaCard);
            id = ItemView.findViewById(R.id.txtIdVisitaCard);
            tipobit = ItemView.findViewById(R.id.imgTipoVisitaCard);

        }
        void bindData(final ListElementBitacora item){
            nombre.setText(item.getName());
            fecha.setText(item.getFecha());
            id.setText(item.getId());
            direccion.setText(item.getDireccion());
            tipo = item.getTipo();
            if(tipo.equals("res"))
                tipobit.setImageResource(R.drawable.home);
            if(tipo.equals("inv"))
                tipobit.setImageResource(R.drawable.friends);
            if(tipo.equals("reu"))
                tipobit.setImageResource(R.drawable.reunion);
        }
    }
}
