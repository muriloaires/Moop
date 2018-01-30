package brmobi.moop.features.condominio;

import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brmobi.moop.R;

/**
 * Created by murilo aires on 12/12/2017.
 */

public class AddBlocosAdapter extends RecyclerView.Adapter<AddBlocoViewHolder> {
    private List<String> blocos;
    private TextWatcher watcher;

    public AddBlocosAdapter(List<String> blocos) {
        this.blocos = blocos;

    }

    @Override
    public AddBlocoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_bloco, parent, false);
        return new AddBlocoViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(AddBlocoViewHolder holder, int position) {
        if (position == blocos.size() - 1) {
            holder.showBtnAdd();
        } else {
            holder.hideBtnAdd();
        }
        holder.bindView(blocos.get(position), watcher);
    }

    @Override
    public int getItemCount() {
        return blocos.size();
    }

    public void addBloco() {
        blocos.add("");
        notifyDataSetChanged();
    }

    public void excluir(int adapterPosition) {
        blocos.remove(adapterPosition);
        notifyDataSetChanged();
    }

    public void changeName(String nome, int adapterPosition) {
        blocos.set(adapterPosition, nome);
    }
}
