package com.mj.booksearchapp.presentation.main.detail

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.ActivityBookDetailBinding
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.presentation.base.BaseActivity
import com.mj.booksearchapp.util.commaString
import com.mj.booksearchapp.util.getDateString
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class BookDetailActivity : BaseActivity<BookDetailViewModel, ActivityBookDetailBinding>() {
    override val viewModel: BookDetailViewModel by viewModels()

    override fun getViewBinding(): ActivityBookDetailBinding = ActivityBookDetailBinding.inflate(layoutInflater)

    private lateinit var bookInfo: BookInfo
    private var position = -1

    override fun initViews() {
        bookInfo = intent.getParcelableExtra("bookInfo")!!
        viewModel.setBookInfo(bookInfo)
        position = intent.getIntExtra("position", -1)
        setBackButton()
        setFavoriteButton()
    }

    override fun observeData() {
        viewModel.bookInfoLiveData.observe(this) {
            setBookInfo(it)
        }
    }

    private fun setBackButton() = with(binding) {
        imageviewBack.setOnClickListener {
            finish()
        }
    }

    private fun setFavoriteButton() = with(binding) {
        imageviewFavorite.setOnClickListener {
            when (bookInfo.favorite) {
                true -> {
                    viewModel.setBookFavorite(false)
                    bookInfo.favorite = false
                    setFavoriteButtonState()
                    val intent = Intent()
                    intent.putExtra("position", position)
                    intent.putExtra("favoriteState", false)
                    setResult(RESULT_OK, intent)
                    //if (position != -1) viewModel.deleteFavorite(position)
                }
                false -> {
                    viewModel.setBookFavorite(true)
                    bookInfo.favorite = true
                    setFavoriteButtonState()
                    val intent = Intent()
                    intent.putExtra("position", position)
                    intent.putExtra("favoriteState", true)
                    setResult(RESULT_OK, intent)
                    //if (position != -1) viewModel.saveFavorite(position)
                }
            }
        }
    }

    private fun setBookInfo(bookInfo: BookInfo) = with(binding) {

        Glide.with(this@BookDetailActivity)
            .load(bookInfo.thumbnail)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(22)))
            .placeholder(R.drawable.image_book_cover)
            .error(R.drawable.image_book_cover)
            .into(imageviewBackground)


        Glide.with(this@BookDetailActivity)
            .load(bookInfo.thumbnail)
            .placeholder(R.drawable.image_book_cover)
            .error(R.drawable.image_book_cover)
            .into(imageviewThumbnail)


        textviewBookTitle.text = bookInfo.title
        textviewAuthor.text = if (bookInfo.authors.isNotEmpty()) bookInfo.authors[0] else null
        textviewTranslator.text = if (bookInfo.translators.isNotEmpty()) bookInfo.translators[0] else null
        textviewPublisher.text = bookInfo.publisher
        textviewPrice.text = String.format(getString(R.string.won), bookInfo.price.commaString)
        textviewStatus.text = bookInfo.status
        textviewReleaseDate.text = getDateString(bookInfo.datetime, getString(R.string.iso_date_format), getString(R.string.date_format))
        textviewBookDescription.text = String.format(getString(R.string.book_contents), bookInfo.contents)
        setFavoriteButtonState()
    }


    private fun setFavoriteButtonState() = with(binding) {
        when (bookInfo.favorite) {
            true -> imageviewFavorite.setImageResource(R.drawable.ic_baseline_favorite)
            false -> imageviewFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putParcelable("bookInfo", bookInfo)
    }

}