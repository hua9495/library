package com.alexchan.library

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexchan.library.databinding.FragmentBookBinding

class BookFragment : Fragment(R.layout.fragment_book), BookAdapter.Listener {

    private lateinit var viewModel: BookViewModel

    private lateinit var binding: FragmentBookBinding

    private val adapter = BookAdapter(this)
    private val bookRepository = BookRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookBinding.bind(view)

        binding.recycleView.adapter = adapter
        viewModel.fetchBook("https://book.api.com/books") { books, exception ->

            if (books != null) {
                adapter.submitList(books)
                return@fetchBook
            }

            if (exception == null) {
                return@fetchBook
            }

            viewModel.fetchBooksError(this)
        }

    }

    override fun onFavoriteButtonClicked(book_id: Int?) {
        bookRepository.favoriteBook(
            "https://book.api.com/books"
        ) { book, exception ->
            if (book != null) {
                val updatedBooks = adapter.currentList.apply {
                    this.firstOrNull { it.book_id == book.book_id }
                        ?.isFavorited = book.isFavorited
                }

                adapter.submitList(updatedBooks)
            }

            if (exception == null) {
                return@favoriteBook
            }

            viewModel.fetchBooksError(this)
        }
    }
}