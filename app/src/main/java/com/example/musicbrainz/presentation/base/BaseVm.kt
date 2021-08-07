package com.example.musicbrainz.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicbrainz.util.event.Event

abstract class BaseVm : ViewModel() {

    private val mShowMsgEvent = MutableLiveData<Event<String>>()
    val showMsgEvent: LiveData<Event<String>>
        get() = mShowMsgEvent

    protected fun emitMsgEvent(msg: String) {
        mShowMsgEvent.value = Event(msg)
    }

}