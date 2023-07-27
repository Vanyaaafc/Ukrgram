package com.example.ukrgram.ui.screens.main_list

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.core.graphics.drawable.DrawableCompat.applyTheme
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.ukrgram.R
import com.example.ukrgram.database.*
import com.example.ukrgram.models.CommonModel
import com.example.ukrgram.ui.screens.settings.ChangeNameFragment
import com.example.ukrgram.utilits.*
import kotlinx.android.synthetic.main.fragment_main_list.*
import java.lang.Exception


class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        sharedPreferences = requireContext().getSharedPreferences("theme", Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_main_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val nightButton = view.findViewById<View>(R.id.ic_night_theme)
        nightButton?.setOnClickListener {
            // Получение текущего значения темы из SharedPreferences
            val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)

            // Инвертирование значения темы
            val newThemeValue = !isDarkTheme

            // Сохранение нового значения темы в SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("isDarkTheme", newThemeValue)
            editor.apply()

            // Применение новой темы
            applyTheme(newThemeValue)

        }
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        APP_ACTIVITY.title = "Ukrgram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
        initRecyclerView()
    }


    private fun initRecyclerView() {
        mRecyclerView = main_list_recycle_view
        mAdapter = MainListAdapter()

        //1 запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->

                when (model.type) {
                    TYPE_CHAT -> showChat(model)
                    TYPE_GROUP -> showGroup(model)
                }

            }
        })
        mRecyclerView.adapter = mAdapter
    }

    private fun showGroup(model: CommonModel) {
        //2 запрос
        REF_DATABASE_ROOT.child(NODE_GROUP).child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                val newModel = dataSnapshot1.getCommonModel()

                //3 запрос
                REF_DATABASE_ROOT.child(NODE_GROUP).child(model.id).child(NODE_MESSAGES)
                    .limitToLast(1)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                        val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                        if (tempList.isEmpty()) {
                            newModel.lastMessage = getString(R.string.chat_cleared)
                        } else {
                            newModel.lastMessage = tempList[0].text
                        }
                        newModel.type = TYPE_GROUP
                        mAdapter.updateListItems(newModel)
                    })
            })
    }


    private fun showChat(model: CommonModel) {
        //2 запрос
        mRefUsers.child(model.id)
            .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot1 ->
                val newModel = dataSnapshot1.getCommonModel()

                //3 запрос
                mRefMessages.child(model.id).limitToLast(1)
                    .addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot2 ->
                        val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                        if (tempList.isEmpty()) {
                            newModel.lastMessage = getString(R.string.chat_cleared)
                        } else {
                            newModel.lastMessage = tempList[0].text
                        }

                        if (newModel.fullname.isEmpty()) {
                            newModel.fullname = newModel.phone
                        }
                        newModel.type = TYPE_CHAT
                        mAdapter.updateListItems(newModel)
                    })
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_main_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_exit -> {
                val builder = AlertDialog.Builder(APP_ACTIVITY)
                builder.setTitle("Выход из аккаунта")
                builder.setMessage("Вы уверены, что хотите выйти из аккаунта?")
                builder.setPositiveButton("Да") { _, _ ->
                    AppStates.updateState(AppStates.OFFLINE)
                    AUTH.signOut()
                    restartActivity()
                }
                builder.setNegativeButton("Нет") { dialog, _ ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
                return true
            }
        }
        // Добавьте закрывающую фигурную скобку
        return super.onOptionsItemSelected(item)
    }


    private fun applyTheme(isDarkTheme: Boolean) {
        // Применение выбранной темы
        if (isDarkTheme) {
            // Включение ночной темы
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // Включение светлой темы
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        restartActivity()
    }
}
