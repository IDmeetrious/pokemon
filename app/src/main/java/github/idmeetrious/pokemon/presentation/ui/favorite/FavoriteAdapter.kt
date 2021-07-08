package github.idmeetrious.pokemon.presentation.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.idmeetrious.pokemon.R
import github.idmeetrious.pokemon.domain.entities.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteAdapter(private var list: List<Pokemon>) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    private var _pokemon: MutableStateFlow<Pokemon?> = MutableStateFlow(null)
    val pokemon get() = _pokemon


    fun updateList(list: List<Pokemon>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val item = list[position]

        holder.idTv.text = "${item.id}"
        holder.nameTv.text = item.name

        Glide.with(holder.itemView)
            .load(item.sprites.frontDefault)
            .into(holder.imageIv)

        holder.deleteBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                _pokemon.emit(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}