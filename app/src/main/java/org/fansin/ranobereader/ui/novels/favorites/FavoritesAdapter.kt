package org.fansin.ranobereader.ui.novels.favorites

import org.fansin.ranobereader.NovelBinder
import org.fansin.ranobereader.ui.novels.BaseNovelsAdapter
import org.fansin.ranobereader.ui.novels.NovelsDiffUtilCallback

class FavoritesAdapter(
    novelBinder: NovelBinder,
    diffUtilCallback: NovelsDiffUtilCallback
) : BaseNovelsAdapter(novelBinder, diffUtilCallback)
