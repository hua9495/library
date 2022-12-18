package com.alexchan.library

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.reflect.Type

class BookRepository {

    private val client = OkHttpClient()

    private val moshi = Moshi.Builder().build()

    fun fetchBooks(url: String, callback: (books: List<Book>?, exception: Throwable?) -> Unit) {
        val request: Request = Request.Builder()
            .url(url)
            .get()
            .build()

        try {
            val response = client.newCall(request).execute()
            val type: Type = Types.newParameterizedType(
                List::class.java,
                Book::class.java
            )

            val moshiAdapter: JsonAdapter<List<Book>> = moshi.adapter(type)
            val json: String = response.body.string()
            val books = moshiAdapter.fromJson(json) ?: throw NullPointerException()

            callback.invoke(books, null)
        } catch (exception: Exception) {
            callback.invoke(null, exception)
        }
    }

    fun favoriteBook(
        url: String,
        callback: (book: Book?, exception: Throwable?) -> Unit
    ) {
        val request: Request = Request.Builder()
            .url(url)
            .post("".toRequestBody())
            .build()

        try {
            val response = client.newCall(request).execute()

            val moshiAdapter: JsonAdapter<Book> = moshi.adapter(Book::class.java)
            val json: String = response.body.string()
            val book = moshiAdapter.fromJson(json) ?: throw NullPointerException()

            callback.invoke(book, null)
        } catch (exception: Exception) {
            callback.invoke(null, exception)
        }
    }
}
