package com.hrithik.fieldagentdirectory.ui.agent_profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hrithik.fieldagentdirectory.data.local.entity.PostEntity
import com.hrithik.fieldagentdirectory.databinding.ItemPostBinding

class PostAdapter :
    ListAdapter<PostEntity, PostAdapter.PostViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        getItem(position)?.let(holder::bind)
    }

    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostEntity) {
            binding.title.text = post.title
            binding.body.text = post.body
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(a: PostEntity, b: PostEntity) = a.id == b.id
            override fun areContentsTheSame(a: PostEntity, b: PostEntity) = a == b
        }
    }
}
