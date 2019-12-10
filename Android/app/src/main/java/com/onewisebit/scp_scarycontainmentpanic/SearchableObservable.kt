package com.onewisebit.scp_scarycontainmentpanic

import android.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class SearchableObservable {

    fun fromView(searchView: SearchView): Observable<String> {

        val subject: PublishSubject<String> = PublishSubject.create()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            /**
             * Returning true means we have managed the event
             */
            override fun onQueryTextSubmit(s: String): Boolean {
                subject.onNext(s)
                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                subject.onNext(text)
                return true
            }
        })

        return subject
    }
}