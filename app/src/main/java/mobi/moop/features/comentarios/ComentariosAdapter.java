package mobi.moop.features.comentarios;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Comentario;

/**
 * Created by murilo aires on 11/08/2017.
 */

public class ComentariosAdapter extends RecyclerView.Adapter<ComentarioViewHolder> {
    private List<Comentario> comentarios;

    public ComentariosAdapter(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    @Override
    public ComentarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comentario, parent, false);
        return new ComentarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComentarioViewHolder holder, int position) {
        Comentario comentario = comentarios.get(position);
        holder.bindView(comentario);
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }
}
