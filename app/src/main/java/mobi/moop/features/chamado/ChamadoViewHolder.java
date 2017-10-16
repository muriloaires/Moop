package mobi.moop.features.chamado;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobi.moop.R;
import mobi.moop.model.entities.Chamado;
import mobi.moop.model.entities.StatusChamado;

/**
 * Created by murilo aires on 02/10/2017.
 */

public class ChamadoViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_titulo_chamado)
    TextView textTituloChamado;

    @BindView(R.id.text_descricao)
    TextView textDescricaoChamado;

    @BindView(R.id.status_background)
    View statusBackground;

    @BindView(R.id.textoStatus)
    TextView textStatus;

    @BindView(R.id.text_data_abertura)
    TextView textDataAberturaChamado;

    @BindView(R.id.img_foto_chamado)
    ImageView imgChamado;

    private final Context context;
    private Chamado chamado;

    public ChamadoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bindView(Chamado chamado) {
        this.chamado = chamado;
        populateFields();
    }

    private void populateFields() {
        switch (chamado.getStatus()) {
            case StatusChamado.ABERTO:
                textStatus.setText(context.getString(R.string.aberto));
                statusBackground.getBackground().setColorFilter(context.getResources().getColor(android.R.color.holo_red_dark), PorterDuff.Mode.MULTIPLY);
                break;
            case StatusChamado.EM_ANDAMENTO:
                textStatus.setText(context.getString(R.string.em_andamento));
                statusBackground.getBackground().setColorFilter(context.getResources().getColor(R.color.amarelo_analise), PorterDuff.Mode.MULTIPLY);
                break;
            case StatusChamado.RESOLVIDO:
                textStatus.setText(context.getString(R.string.resolvido));
                statusBackground.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                break;
            default:
                textStatus.setText(context.getString(R.string.cancelado));
                statusBackground.getBackground().setColorFilter(context.getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.MULTIPLY);
        }
        textTituloChamado.setText(chamado.getTitulo());
        textDescricaoChamado.setText(chamado.getTexto());
//        Picasso.with(context).load(chamado).error()
        textDataAberturaChamado.setText("Aberto em " + new SimpleDateFormat("dd/MM/yyyy").format(this.chamado.getUpdatedAt()) + " às " + new SimpleDateFormat("HH:mm").format(chamado.getUpdatedAt()));

    }
}
