package com.example.musicbrainz.framework.util.observer

import androidx.lifecycle.Observer
import com.example.musicbrainz.util.event.Event

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}