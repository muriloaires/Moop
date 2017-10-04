package mobi.moop.features.mensagens;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Mensagem;

/**
 * Created by murilo aires on 30/09/2017.
 */

public class MensagensAdapter extends RecyclerView.Adapter<MensagemViewHolder> {
    private List<Mensagem> mensagens;

    public MensagensAdapter(List<Mensagem> comentarios) {
        this.mensagens = comentarios;
    }

    @Override
    public MensagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mensagem, parent, false);
        return new MensagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MensagemViewHolder holder, int position) {
        Mensagem comentario = mensagens.get(position);
        holder.bindView(comentario);
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}
