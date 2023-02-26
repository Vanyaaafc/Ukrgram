package com.example.ukrgram.utilits

import com.example.ukrgram.MainActivity

lateinit var APP_ACTIVITY: MainActivity
//APP_ACTIVITY -> тоже самое, что и (activity as MainActivity)
const val TYPE_MESSAGE_IMAGE = "image"
const val TYPE_MESSAGE_TEXT = "text"
const val TYPE_MESSAGE_VOICE = "voice"
const val TYPE_MESSAGE_FILE = "file"

const val TYPE_CHAT = "chat"
const val TYPE_GROUP = "group"
const val TYPE_CHANNEL = "channel"

const val PICK_FILE_REQUEST_CODE = 301