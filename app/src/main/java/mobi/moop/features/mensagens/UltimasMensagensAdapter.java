package mobi.moop.features.mensagens;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mobi.moop.R;
import mobi.moop.model.entities.Mensagem;

/**
 * Created by murilo aires on 02/10/2017.
 */

public class UltimasMensagensAdapter extends RecyclerView.Adapter<UltimaMensagemViewHolder> {
    private List<Mensagem> mensagens;

    public UltimasMensagensAdapter(List<Mensagem> comentarios) {
        this.mensagens = comentarios;
    }

    @Override
    public UltimaMensagemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ultima_mensagem, parent, false);
        return new UltimaMensagemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UltimaMensagemViewHolder holder, int position) {
        Mensagem mensagem = mensagens.get(position);
        holder.bindView(mensagem);
    }

    @Override
    public int getItemCount() {
        return mensagens.size();
    }
}
