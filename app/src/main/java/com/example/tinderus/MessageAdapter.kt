package com.example.tinderus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView



class MessageAdapter(private val usuario: String):RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    /////////////////////
    //    Variables    //
    /////////////////////

    private var mensajes: List<Mensaje> = emptyList()

    /////////////////////
    //    Funciones    //
    /////////////////////

    fun setData(list: List<Mensaje>){
        mensajes = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): MessageAdapter.MessageViewHolder {

        //Se encargará de contener la vista relativa a los elementos de los chats, es decir, los mensajes
        var res = MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_message,
                parent,
                false
            )
        )
        return res
    }

    override fun onBindViewHolder(holder: MessageAdapter.MessageViewHolder, position: Int) {
        //Cuando recibimos un mensaje estos tendrán una posicion determinada
        val mensaje = mensajes[position]

        //Comprobamos qué usuario es el que consulta el chat para darle un estilo determinado
        if(usuario == mensaje.de){
            holder.itemView.findViewById<View>(R.id.myMessageLayout).visibility = View.VISIBLE
            holder.itemView.findViewById<View>(R.id.otherMessageLayout).visibility = View.GONE

            holder.itemView.findViewById<TextView>(R.id.myMessageTextView).text = mensaje.mensaje
        }
        else{
            holder.itemView.findViewById<View>(R.id.myMessageLayout).visibility = View.GONE
            holder.itemView.findViewById<View>(R.id.otherMessageLayout).visibility = View.VISIBLE

            holder.itemView.findViewById<TextView>(R.id.othersMessageTextView).text = mensaje.mensaje

        }
    }

    override fun getItemCount(): Int {
        return  mensajes.size
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}