package com.mj.booksearchapp.presentation.main.detail

import android.transition.TransitionInflater
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.FragmentDetailBinding
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.util.commaString
import com.mj.booksearchapp.util.getDateString
import com.mj.booksearchapp.util.provider.ResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<MainViewModel, FragmentDetailBinding>() {

    override val viewModel: MainViewModel by activityViewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun getViewBinding(): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater)

    override fun initViews() {
        setBookInfo(args.bookInfo)
        setBackButton()
    }

    private fun setBackButton() = with(binding) {
        imageviewBack.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).popBackStack()
            }
        }
    }

    private fun setBookInfo(bookInfo: BookInfo) = with(binding) {
        textviewBookTitle.text = bookInfo.title
        textviewAuthor.text = if (bookInfo.authors.isNotEmpty()) bookInfo.authors[0] else null
        textviewTranslator.text = if (bookInfo.translators.isNotEmpty()) bookInfo.translators[0] else null
        textviewPublisher.text = bookInfo.publisher
        textviewPrice.text = String.format(getString(R.string.won), bookInfo.price.commaString)
        textviewStatus.text = bookInfo.status
        textviewReleaseDate.text = getDateString(bookInfo.datetime, getString(R.string.iso_date_format), getString(R.string.date_format))

        Glide.with(root)
            .load(bookInfo.thumbnail)
            .placeholder(R.color.dark_gray)
            .error(R.color.dark_gray)
            .into(imageviewThumbnail)

        textviewBookDescription.text = bookInfo.contents
    }

    override fun observeData() {

    }


    companion object {
        const val TAG = "DetailFragment"
        fun newInstance() = DetailFragment()
    }

}