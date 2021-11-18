package mx.ita.securityhome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListResidentes extends RecyclerView.Adapter<ListResidentes.ViewHolder> {
    private List<ListElementResidentes> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListResidentes(Context context) {
        this.context = context;
    }

    public ListResidentes(List<ListElementResidentes> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListResidentes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_invitados, null);
        return new ListResidentes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListResidentes.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<ListElementResidentes> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idd;
        ImageView imagencardresidente;
        TextView txtnomrescard;
        TextView txtcorreorescard;


        ViewHolder(View ItemView) {
            super(ItemView);
            imagencardresidente = ItemView.findViewById(R.id.imagencardresidente);
            txtnomrescard = ItemView.findViewById(R.id.txtnomrescard);
            txtcorreorescard = ItemView.findViewById(R.id.txtcorreorescard);

        }

        void bindData(final ListElementResidentes item) {

            imagencardresidente.setImageBitmap(item.getimagencardresidente());
            txtnomrescard.setText(item.getnomrescard());
            txtcorreorescard.setText(item.getcorreorescard());

        }
    }
}
