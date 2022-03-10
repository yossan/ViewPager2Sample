package io.github.yossan.viewpager2sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DocumentViewModel : ViewModel() {

    private val repository = DocumentRepository()

    val livePages: LiveData<List<Page>> get() = repository.livePages

    fun getPage(pageId: Long): Page {
        return repository.getPage(pageId)
    }

    fun addPageBefore(index: Int): Long {
        var next = index - 1
        if (next < 0) {
            next = 0
        }
        return repository.addPage(next)
    }

    fun addPageAfter(index: Int): Long {
        var next = index + 1
        return repository.addPage(next)
    }

    fun deletePage(index: Int) {
        repository.deletePage(index)
    }
}