package com.mj.booksearchapp.presentation.main.detail

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.mj.booksearchapp.R
import com.mj.booksearchapp.databinding.FragmentDetailBinding
import com.mj.booksearchapp.domain.model.BookInfo
import com.mj.booksearchapp.presentation.base.BaseFragment
import com.mj.booksearchapp.presentation.main.MainViewModel
import com.mj.booksearchapp.presentation.main.search.SearchFragment
import com.mj.booksearchapp.util.commaString
import com.mj.booksearchapp.util.getDateString
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation


@AndroidEntryPoint
class DetailFragment : BaseFragment<MainViewModel, FragmentDetailBinding>() {

    override val viewModel: MainViewModel by activityViewModels()

    //private val args: DetailFragmentArgs by navArgs()
    private lateinit var bookInfo: BookInfo
    private var position = -1

    override fun getViewBinding(): FragmentDetailBinding = FragmentDetailBinding.inflate(layoutInflater)

    override fun initViews() {
        viewModel.setCurrentFragment(DetailFragment.TAG)
        bookInfo = arguments?.getParcelable("bookInfo")!!
        position = arguments?.getInt("position")!!
        setBookInfo()
        setBackButton()
        setFavoriteButton()
    }


    private fun setBackButton() = with(binding) {
        imageviewBack.setOnClickListener {
            view?.let {
                viewModel.setCurrentFragment(SearchFragment.TAG)
                showPreviousFragment(SearchFragment.newInstance(), SearchFragment.TAG)
            }
        }
    }

    private fun setFavoriteButton() = with(binding) {
        imageviewFavorite.setOnClickListener {
            when (bookInfo.favorite) {
                true -> {
                    bookInfo.favorite = false
                    setFavoriteButtonState()
                    if (position != -1) viewModel.deleteFavorite(position)
                }
                false -> {
                    bookInfo.favorite = true
                    setFavoriteButtonState()
                    if (position != -1) viewModel.saveFavorite(position)
                }
            }
        }
    }

    private fun setBookInfo() = with(binding) {

        Glide.with(requireContext())
            .load(bookInfo.thumbnail)
            .apply(bitmapTransform(BlurTransformation(22)))
            .placeholder(R.color.dark_gray)
            .error(R.color.dark_gray)
            .into(imageviewBackground)


        Glide.with(requireContext())
            .load(bookInfo.thumbnail)
            .placeholder(R.color.dark_gray)
            .error(R.color.dark_gray)
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

    override fun observeData() {

    }

    private fun showPreviousFragment(fragment: Fragment, tag: String) {
        val findFragment = requireActivity().supportFragmentManager.findFragmentByTag(tag)

        findFragment?.let {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .show(it)
                .commitAllowingStateLoss()
        } ?: kotlin.run {
            requireActivity().supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                .add(R.id.fragment_container_view, fragment, tag)
                .commitAllowingStateLoss()
        }

        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            .detach(this)
            .commitAllowingStateLoss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    viewModel.setCurrentFragment(SearchFragment.TAG)
                    showPreviousFragment(SearchFragment.newInstance(), SearchFragment.TAG)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }


    companion object {
        const val TAG = "DetailFragment"
        fun newInstance() = DetailFragment()
    }

}