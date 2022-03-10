package io.github.yossan.viewpager2sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class DocumentRepository {
    private var pages: List<Page> = listOf(Page(0, 0), Page(1, 1), Page(2, 2))

    private val _livePages = MutableLiveData(pages)

    val livePages: LiveData<List<Page>> = _livePages

    fun getPage(pageId: Long): Page {
        return pages.first { it.id == pageId }
    }

    fun addPage(index: Int): Long {
        val newPage = Page(Random.nextLong(), index)
        val newPages = mutableListOf<Page>()
        for ((i, page) in pages.withIndex()) {
            if (i == index) {
                newPages.add(newPage)
            }
            if (i >= index) {
                page.index += 1
            }
            newPages.add(page)
        }
        pages = newPages
        _livePages.value = newPages
        return newPage.id
    }

    fun deletePage(index: Int) {
        val newPages = mutableListOf<Page>()
        for ((i, page) in pages.withIndex()) {
            if (i == index) {
                continue
            }

            if (i >= index) {
                page.index -= 1
            }
            newPages.add(page)
        }

        pages = newPages
        _livePages.value = newPages
    }
}