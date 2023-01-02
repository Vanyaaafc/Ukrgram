package com.example.ukrgram.ui.fragments

import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ukrgram.R
import com.example.ukrgram.activities.RegisterActivity
import com.example.ukrgram.utilits.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView


class SettingsFragment : BaseFragment(R.layout.fragment_settings) {


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        mRootView.findViewById<TextView>(R.id.settings_bio).text = USER.bio
        mRootView.findViewById<TextView>(R.id.settings_full_name).text = USER.fullname
        mRootView.findViewById<TextView>(R.id.settings_number_phone).text = USER.phone
        mRootView.findViewById<TextView>(R.id.settings_status).text = USER.state
        mRootView.findViewById<TextView>(R.id.settings_username).text = USER.username
        mRootView.findViewById<View>(R.id.settings_btn_change_username).setOnClickListener {
            replaceFragment(ChangeUsernameFragment())
        }
        mRootView.findViewById<View>(R.id.settings_btn_change_bio).setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }
        mRootView.findViewById<View>(R.id.settings_change_photo)
            .setOnClickListener { changePhotoUser() }
        mRootView.findViewById<CircleImageView>(R.id.settings_user_photo)
            .downloadAndSetImage(USER.photoUrl)

    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == AppCompatActivity.RESULT_OK && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        (mRootView.findViewById<ImageView>(R.id.settings_user_photo))
                        showToast(getString(R.string.toast_data_update))
                        USER.photoUrl = it
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}