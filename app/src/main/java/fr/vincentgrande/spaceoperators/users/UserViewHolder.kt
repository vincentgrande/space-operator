package fr.vincentgrande.spaceoperators.users

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import fr.vincentgrande.spaceoperators.databinding.UserListItemBinding

class UserViewHolder(binding: UserListItemBinding, context: Context):
    RecyclerView.ViewHolder(binding.root) {
    val username = binding.tvUsernameUserlist
    val state = binding.tvUserState
    var userId: Int? = null


    init {
        /*checkbox_item.setOnCheckedChangeListener { _, isChecked ->
            if(itemId != null)
                onCheck(isChecked, itemId ?: -1)
        }*/
    }
}