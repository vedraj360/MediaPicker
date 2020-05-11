package com.crazylegend.videopicker.adapters.multi

import androidx.recyclerview.widget.RecyclerView
import com.crazylegend.core.loadImage
import com.crazylegend.core.visible
import com.crazylegend.videopicker.R
import com.crazylegend.videopicker.databinding.ItemviewVideoBinding
import com.crazylegend.videopicker.videos.VideoModel


/**
 * Created by crazy on 5/8/20 to long live and prosper !
 */
internal class VideosMultiSelectViewHolder(
        private val binding: ItemviewVideoBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.selection.visible()
    }

    fun bind(imageModel: VideoModel, selected: Boolean = false) {
        binding.image.loadImage(imageModel.contentUri)

        if (selected) {
            binding.selection.setImageResource(R.drawable.ic_checked_default)
        } else {
            binding.selection.setImageResource(R.drawable.ic_unchecked_default)
        }
    }
}