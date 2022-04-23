package com.example.tinderus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ChatAdapter(val chatClick: (Chat) -> Unit): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    /////////////////////
    //    Variables    //
    /////////////////////

    var chats: List<Chat> = emptyList()

    /////////////////////
    //    Funciones    //
    /////////////////////

    fun setData(list: List<Chat>){
        chats = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position:Int){
        holder.itemView.findViewById<TextView>(R.id.chatNameText).text = chats[position].nombre
        holder.itemView.findViewById<TextView>(R.id.usersTextView).text = chats[position].usuarios.toString()

        holder.itemView.setOnClickListener{
            chatClick(chats[position])
        }
    }

    override fun getItemCount():Int{
        return chats.size
    }

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}