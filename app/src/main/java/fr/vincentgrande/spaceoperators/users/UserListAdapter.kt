package fr.vincentgrande.spaceoperators.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.vincentgrande.spaceoperators.databinding.UserListItemBinding

class UserListAdapter(val users: MutableList<User>): RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            parent.context
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.username.text = "🧑‍🚀 ${user.username}"
        holder.state.text = if (!user.status) "WAITING" else "READY"
    }

    override fun getItemCount(): Int = users.size


}