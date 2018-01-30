package brmobi.moop.features.condominio;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import brmobi.moop.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by murilo aires on 12/12/2017.
 */

public class AddBlocoViewHolder extends RecyclerView.ViewHolder {
    private AddBlocosAdapter adapter;

    @OnClick(R.id.btnMais)
    public void btnMaisAction(View view) {
        adapter.addBloco();
    }

    @OnClick(R.id.btnExcluirBloco)
    public void btnExcluirAction(View view) {
        adapter.excluir(getAdapterPosition());
    }

    @BindView(R.id.btnExcluirBloco)
    View btnExcluirBloco;

    @BindView(R.id.btnMais)
    View btnMais;

    @BindView(R.id.textInputAddBloco)
    TextInputLayout textInputLayout;

    @BindView(R.id.editNomeBloco)
    EditText editNomeBloco;

    public AddBlocoViewHolder(View itemView, AddBlocosAdapter adapter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.adapter = adapter;
    }

    public void showBtnAdd() {
        btnMais.setVisibility(View.VISIBLE);
        btnExcluirBloco.setVisibility(View.GONE);
    }

    public void hideBtnAdd() {
        btnMais.setVisibility(View.GONE);
        btnExcluirBloco.setVisibility(View.VISIBLE);
    }

    public void bindView(String bloco, TextWatcher watcher) {
        if (watcher == null) {
            watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.changeName(charSequence.toString(), getAdapterPosition());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };
        }
        editNomeBloco.setText(bloco);
        editNomeBloco.removeTextChangedListener(watcher);
        editNomeBloco.addTextChangedListener(watcher);
    }
}
