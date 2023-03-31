package com.example.ukrgram.ui.screens.groups

import androidx.recyclerview.widget.RecyclerView
import com.example.ukrgram.R
import com.example.ukrgram.models.CommonModel
import com.example.ukrgram.ui.screens.base.BaseFragment
import com.example.ukrgram.utilits.APP_ACTIVITY
import com.example.ukrgram.utilits.hideKeyboard
import com.example.ukrgram.utilits.showToast
import kotlinx.android.synthetic.main.fragment_create_group.*

class CreateGroupFragment(var listContacts: List<CommonModel>) : BaseFragment(R.layout.fragment_create_group) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AddContactsAdapter


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Создать группу"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
        initRecyclerView()
        create_group_btn_complete.setOnClickListener {
            showToast("Click")
        }
        create_group_input_name.requestFocus()
    }


    private fun initRecyclerView() {
        mRecyclerView = create_group_recycle_view
        mAdapter = AddContactsAdapter()
        mRecyclerView.adapter = mAdapter
        listContacts.forEach { mAdapter.updateListItems(it) }
    }
}
