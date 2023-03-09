package com.example.ukrgram.ui.screens.main_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukrgram.R
import com.example.ukrgram.models.CommonModel
import com.example.ukrgram.ui.screens.single_chat.SingleChatAdapter
import com.example.ukrgram.ui.screens.single_chat.SingleChatFragment
import com.example.ukrgram.utilits.downloadAndSetImage
import com.example.ukrgram.utilits.replaceFragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListViewHolder>() {

    private val listItems = mutableListOf<CommonModel>()

    class MainListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.main_list_item_name
        val itemLastMessage: TextView = view.main_list_last_message
        val itemPhoto: CircleImageView = view.main_list_item_photo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)

        val holder = MainListViewHolder(view)
        holder.itemView.setOnClickListener {
            replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
        }
        return holder
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        holder.itemName.text = listItems[position].fullname
        holder.itemLastMessage.text = listItems[position].lastMessage
        holder.itemPhoto.downloadAndSetImage(listItems[position].photoUrl)
    }

    override fun getItemCount(): Int = listItems.size

    fun updateListItems(item: CommonModel) {
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }
}