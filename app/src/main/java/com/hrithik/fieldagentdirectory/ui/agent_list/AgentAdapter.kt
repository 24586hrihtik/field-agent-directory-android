package com.hrithik.fieldagentdirectory.ui.agent_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hrithik.fieldagentdirectory.data.local.entity.UserEntity
import com.hrithik.fieldagentdirectory.databinding.ItemAgentBinding

class AgentAdapter(
    private val onClick: (UserEntity) -> Unit
) : PagingDataAdapter<UserEntity, AgentAdapter.AgentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val binding = ItemAgentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AgentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) holder.bind(user)
    }

    inner class AgentViewHolder(private val binding: ItemAgentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.tvName.text = "${user.firstName} ${user.lastName}"
            binding.tvEmail.text = user.email
            binding.root.setOnClickListener { onClick(user) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity) =
            oldItem == newItem
    }
}
