package io.github.yossan.viewpager2sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private val viewModel: DocumentViewModel by viewModels()

    private var pages: List<Page>? = null

    private val viewPager: ViewPager2 by lazy {
        findViewById(R.id.main_view_pager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ViewPager2>(R.id.main_view_pager).apply {
            adapter = PageFragmentStateAdapter(this@MainActivity).apply {
                registerAdapterDataObserver(PageDataObserver(this@MainActivity))
            }
        }

        viewModel.livePages.observe(this, Observer { pages ->
            val currentPages = this@MainActivity.pages
            this@MainActivity.pages = pages
            if (currentPages == null) {
                viewPager.adapter?.notifyItemRangeInserted(0, pages.count())
            } else {
                viewPager.adapter?.notifyDataSetChanged()
                viewPager.setCurrentItem(2)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.document_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_befor -> {
                viewModel.addPageBefore(viewPager.currentItem)
                true
            }
            R.id.add_after -> {
                viewModel.addPageAfter(viewPager.currentItem)
                true
            }
            R.id.delete_page -> {
                viewModel.deletePage(viewPager.currentItem)
                true
            }
            else -> {
                false
            }
        }
    }

    class PageFragmentStateAdapter(private val activity: MainActivity) : FragmentStateAdapter(activity) {

        override fun createFragment(position: Int): Fragment {
            val page = activity.pages!!.get(position)
            return PageFragment.newInstance(page.id)
        }

        override fun getItemCount(): Int {
            return activity.pages?.count() ?: 0
        }

        override fun getItemId(position: Int): Long {
            return activity.pages!!.get(position)!!.id
        }

        override fun containsItem(itemId: Long): Boolean {
            return activity.pages!!.any { it.id == itemId }
        }
    }

    class PageDataObserver(val activity: MainActivity) : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            super.onItemRangeChanged(positionStart, itemCount)
            Log.d("MainActivity", "DataObserver onItemRangeChanged(${positionStart}, ${itemCount}")
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            super.onItemRangeChanged(positionStart, itemCount, payload)
            Log.d("MainActivity", "DataObserver onItemRangeChanged(${positionStart}, ${itemCount}, ${payload}")

        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            Log.d("MainActivity", "DataObserver onItemRangeInserted(${positionStart}, ${itemCount})")
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            Log.d("MainActivity", "DataObserver onItemRangeRemoved(${positionStart}, ${itemCount}")
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount)
            Log.d("MainActivity", "DataObserver onItemRangeMoved(${fromPosition}, ${toPosition}, ${itemCount}")
        }


    }
}