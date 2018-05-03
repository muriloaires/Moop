package brmobi.moop.ui.condominium.add

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup

import brmobi.moop.R

/**
 * Created by murilo aires on 12/12/2017.
 */

class AddBlocAdapter(val mPresenter: AddBlocMvpPresenter<AddBlocMvpView>) : RecyclerView.Adapter<AddBlocViewHolder>() {
    private var watcher: TextWatcher? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddBlocViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_bloco, parent, false)
        return AddBlocViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddBlocViewHolder, position: Int) {
        if (position == mPresenter.getBlocs().size - 1) {
            holder.showBtnAdd()
            holder.requestFocus()
        } else {
            holder.hideBtnAdd()
        }
        holder.bindView(mPresenter.getBlocs()[position])
        holder.btnDeleteBloc.setOnClickListener {
            mPresenter.onBtnDeleteBlocClick(position)
        }
        holder.btnMore.setOnClickListener {
            mPresenter.onBtnAddBlockClick(position)
        }
        watcher = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mPresenter.onTextChanged(holder.adapterPosition, charSequence)
            }

            override fun afterTextChanged(editable: Editable) {
            }
        }

        holder.editBlocName.removeTextChangedListener(watcher)
        holder.editBlocName.addTextChangedListener(watcher)
    }

    override fun getItemCount(): Int {
        return mPresenter.getBlocs().size
    }

}
