package mx.ita.securityhome;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<mx.ita.securityhome.ListAdapter.ViewHolder>{
    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;
    public ListAdapter(Context context){
        this.context = context;
    }
    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public mx.ita.securityhome.ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_invitados, null);
        return new mx.ita.securityhome.ListAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final mx.ita.securityhome.ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElement> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView telefono;
        ImageButton edit;
        String id;

        ViewHolder(View ItemView){
            super(ItemView);
            nombre = ItemView.findViewById(R.id.labelNombreInvitado);
            telefono = ItemView.findViewById(R.id.labelTelefonoInvitado);
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
        void bindData(final ListElement item){
            nombre.setText(item.getName());
            telefono.setText(item.getTelefono());
            id = item.getId();
        }
    }


}
