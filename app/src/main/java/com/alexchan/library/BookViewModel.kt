package com.alexchan.library

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {

    val repository: BookRepository = BookRepository()

    fun fetchBook(
        url: String,
        callback: ((books: List<Book>?, exception: Throwable?) -> Unit)
    ) {
        repository.fetchBooks(url, callback)
    }

    fun fetchBooksError(fragment: Fragment) {
        val alertDialog = AlertDialog.Builder(fragment.context).apply {
            this.setTitle("Fetch Books Failure!")
            this.setMessage("An error occurred while trying to fetch books, please try again.")
            this.setPositiveButton("OK") { _, _ -> }
        }.create()

        alertDialog.show()
    }

    fun favoriteBooksError(fragment: Fragment) {
        val alertDialog = AlertDialog.Builder(fragment.context).apply {
            this.setTitle("Favorite Book Failure!")
            this.setMessage("An error occurred while trying to favorite books, please try again.")
            this.setPositiveButton("OK") { _, _ -> }
        }.create()

        alertDialog.show()
    }
}
