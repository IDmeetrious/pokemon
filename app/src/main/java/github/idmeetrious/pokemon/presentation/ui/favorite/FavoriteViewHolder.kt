package github.idmeetrious.pokemon.presentation.ui.favorite

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.idmeetrious.pokemon.R

class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val idTv: TextView
    val nameTv: TextView
    val imageIv: ImageView
    val deleteBtn: ImageButton
    init {
        view.let {
            idTv = it.findViewById(R.id.favorite_item_id_tv)
            nameTv = it.findViewById(R.id.favorite_item_name_tv)
            imageIv = it.findViewById(R.id.item_front_def_iv)
            deleteBtn = it.findViewById(R.id.favorite_item_delete_btn)
        }
    }
}