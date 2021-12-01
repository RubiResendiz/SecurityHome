package mx.ita.securityhome;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListAdapterComentarios extends RecyclerView.Adapter<ListAdapterComentarios.ViewHolder>{
    private List<ListElementComentarios> mData;
    private LayoutInflater mInflater;
    private Context context;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    public ListAdapterComentarios(Context context){
        this.context = context;
    }

    public ListAdapterComentarios(List<ListElementComentarios> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }
    @Override
    public int getItemCount(){return mData.size();}
    @Override
    public ListAdapterComentarios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.card_comentarios, null);
        return new ListAdapterComentarios.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapterComentarios.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
    }
    public void setItems(List<ListElementComentarios> items){mData= items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView comentario;
        RatingBar estrellas;
        String id;

        ViewHolder(View ItemView){
            super(ItemView);
            nombre = ItemView.findViewById(R.id.txtNombreComentarioCard);
            comentario = ItemView.findViewById(R.id.txtComentarioCard);
            estrellas = ItemView.findViewById(R.id.ratingCard);
        }
        void bindData(final ListElementComentarios item){
            nombre.setText(item.getName());
            comentario.setText(item.getComentario());
            estrellas.setRating(item.getEstrellas());
        }
    }


}
