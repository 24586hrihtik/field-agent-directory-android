package com.hrithik.fieldagentdirectory.ui.agent_list
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.databinding.ItemAgentBinding

class AgentAdapter :
    PagingDataAdapter<UserEntity, AgentAdapter.AgentViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val binding = ItemAgentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AgentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    class AgentViewHolder(
        private val binding: ItemAgentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEntity) {
            binding.name.text = "${user.firstName} ${user.lastName}"
            binding.email.text = user.email
            binding.avatar.load(user.image)
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<UserEntity>() {
            override fun areItemsTheSame(a: UserEntity, b: UserEntity) = a.id == b.id
            override fun areContentsTheSame(a: UserEntity, b: UserEntity) = a == b
        }
    }
}
