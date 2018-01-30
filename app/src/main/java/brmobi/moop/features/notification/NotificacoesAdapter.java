package brmobi.moop.features.notification;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import brmobi.moop.R;
import brmobi.moop.model.entities.Notificacao;

/**
 * Created by murilo aires on 21/12/2017.
 */

public class NotificacoesAdapter extends RecyclerView.Adapter<NotificacaoViewHolder> {
    private List<Notificacao> notificacoes;

    public NotificacoesAdapter(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    @Override
    public NotificacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacao, parent, false);
        return new NotificacaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificacaoViewHolder holder, int position) {
        holder.bindView(notificacoes.get(position));
    }

    @Override
    public int getItemCount() {
        return notificacoes.size();
    }
}
