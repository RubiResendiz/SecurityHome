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

import java.util.List;

public class ListNotificaciones extends RecyclerView.Adapter<ListNotificaciones.ViewHolder>{
    private List<ListElementNotificaciones> mData;
    private LayoutInflater mInflater;
    private Context context;
    public ListNotificaciones(Context context){
        this.context = context;
    }
    public ListNotificaciones(List<ListElementNotificaciones> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public ListNotificaciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_invitados, null);
        return new ListNotificaciones.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListNotificaciones.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElementNotificaciones> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtnotificacion;
        ImageView imgnot;
        TextView txtalerta;
        TextView txtrecuerda;
        TextView txtfecha;

        ViewHolder(View ItemView){
            super(ItemView);
            txtnotificacion = ItemView.findViewById(R.id.txtnotificacion);
            imgnot = ItemView.findViewById(R.id.imgnot);
            txtalerta = ItemView.findViewById(R.id.txtalerta);
            txtrecuerda = ItemView.findViewById(R.id.txtrecuerda);
            txtfecha = ItemView.findViewById(R.id.txtfecha);

        }
        void bindData(final ListElementNotificaciones item){
            txtnotificacion .setText(item.getNot());
            imgnot.setImageBitmap(item.getImgnot());
            txtalerta .setText(item.getalerta());
            txtrecuerda .setText(item.getrecuerda());
            txtfecha.setText(item.getfecha());
        }
    }


}
