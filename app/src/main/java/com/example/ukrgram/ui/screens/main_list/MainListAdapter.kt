package com.example.ukrgram.ui.screens.main_list

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ukrgram.R
import com.example.ukrgram.database.AUTH
import com.example.ukrgram.database.NODE_USERS
import com.example.ukrgram.database.REF_DATABASE_ROOT
import com.example.ukrgram.database.getCommonModel
import com.example.ukrgram.models.CommonModel
import com.example.ukrgram.ui.screens.groups.GroupChatFragment
import com.example.ukrgram.ui.screens.single_chat.SingleChatFragment
import com.example.ukrgram.utilits.*
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item.view.contact_online
import kotlinx.android.synthetic.main.main_list_item.view.*

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainListViewHolder>() {

    private val listItems = mutableListOf<CommonModel>()
    private lateinit var mRefUsers: DatabaseReference
    private lateinit var mRefUsersListener: AppValueEventListener


    class MainListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.main_list_item_name
        val itemLastMessage: TextView = view.main_list_last_message
        val itemPhoto: CircleImageView = view.main_list_item_photo
        val contactOnline: CircleImageView = view.contact_online
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_list_item, parent, false)

        val holder = MainListViewHolder(view)
        holder.itemView.setOnClickListener {

            when (listItems[holder.adapterPosition].type) {
                TYPE_CHAT -> replaceFragment(SingleChatFragment(listItems[holder.adapterPosition]))
                TYPE_GROUP -> replaceFragment(GroupChatFragment(listItems[holder.adapterPosition]))
            }


        }
        return holder
    }

    override fun onBindViewHolder(holder: MainListViewHolder, position: Int) {
        mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(listItems[position].id)
        mRefUsersListener = AppValueEventListener {
            val contact = it.getCommonModel()



            when (contact.state) {
                AppStates.ONLINE.state -> holder.contactOnline.visibility = View.VISIBLE
                AppStates.OFFLINE.state -> holder.contactOnline.visibility = View.INVISIBLE
            }



            holder.itemName.text = listItems[position].fullname
            holder.itemLastMessage.text = listItems[position].lastMessage
            holder.itemPhoto.downloadAndSetImage(listItems[position].photoUrl)
        }
        mRefUsers.addValueEventListener(mRefUsersListener)

    }


    override fun getItemCount(): Int = listItems.size

    fun updateListItems(item: CommonModel) {
        listItems.add(item)
        notifyItemInserted(listItems.size)
    }


}
