package com.example.hw_5

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import java.util.concurrent.TimeUnit

class MyAsync : Fragment() {
    companion object {
        const val MyTag = "Async"
    }

    var person = mutableListOf<Person>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        PersonHolder.addListener(personListener)
        Log.i("[APP]", "Start Async")
        MessageSender().execute()
    }

    private val personListener: PersonListener = {
        if (it != null) {
            person.add(it)
            val callback = requireActivity() as TaskCallbacks
            callback.getPerson(it)
        }
    }

    class MessageSender : AsyncTask<Void, Void, Void>() {

        override fun onProgressUpdate(vararg p0: Void?) {
            super.onProgressUpdate()
            PersonHolder.sendMessage()
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            for (i in 1..PersonHolder.personsList.count()) {
                TimeUnit.SECONDS.sleep(2)
                publishProgress()
            }
            return null
        }
    }
}