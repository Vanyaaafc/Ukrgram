package com.example.ukrgram.ui.fragments.single_chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.ukrgram.R
import com.example.ukrgram.models.CommonModel
import com.example.ukrgram.utilits.CURRENT_UID
import com.example.ukrgram.utilits.asTime
import kotlinx.android.synthetic.main.message_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class SingleChatAdapter : RecyclerView.Adapter<SingleChatAdapter.SingleChatHolder>() {

    var mlistMessagesCache = emptyList<CommonModel>()

    class SingleChatHolder(view: View) : RecyclerView.ViewHolder(view) {
        val blocUserMessage: ConstraintLayout = view.bloc_user_message
        val chatUserMessage: TextView = view.chat_user_message
        val chatUserMessageTime: TextView = view.chat_user_message_time

        val blocReceivedMessage: ConstraintLayout = view.bloc_received_message
        val chatReceivedMessage: TextView = view.chat_received_message
        val chatReceivedMessageTime: TextView = view.chat_received_message_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        return SingleChatHolder(view)
    }

    override fun getItemCount(): Int = mlistMessagesCache.size

    override fun onBindViewHolder(holder: SingleChatHolder, position: Int) {
        if (mlistMessagesCache[position].from == CURRENT_UID) {
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mlistMessagesCache[position].text
            holder.chatUserMessageTime.text =
                mlistMessagesCache[position].timeStamp.toString().asTime()
        } else {
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mlistMessagesCache[position].text
            holder.chatReceivedMessageTime.text =
                mlistMessagesCache[position].timeStamp.toString().asTime()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<CommonModel>) {
        mlistMessagesCache = list
        notifyDataSetChanged()
    }
}


