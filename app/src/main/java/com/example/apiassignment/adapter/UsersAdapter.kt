package com.example.apiassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.apiassignment.R
import com.example.apiassignment.model.Data
import kotlinx.android.synthetic.main.single_item_layout.view.*

class UsersAdapter(private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<UsersAdapter.MyViewHolder>()  {

    private var myList = emptyList<Data>()

    inner class MyViewHolder(itemView: View, onItemClickListener: OnItemClickListener):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                onItemClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.single_item_layout, parent, false)
        return MyViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: UsersAdapter.MyViewHolder, position: Int) {
        holder.itemView.avatar_view.load(myList[position].avatar)
        holder.itemView.first_name_tv.text = myList[position].first_name
        holder.itemView.email_tv.text = myList[position].email
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(newList: List<Data>){
        myList = newList
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }

}