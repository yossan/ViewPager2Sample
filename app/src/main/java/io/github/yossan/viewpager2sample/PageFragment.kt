package io.github.yossan.viewpager2sample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PAGE_ID = "page_id"

/**
 * A simple [Fragment] subclass.
 * Use the [PageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PageFragment : Fragment() {

    private val pageId: Long by lazy { requireArguments().getLong(ARG_PAGE_ID, 0) }

    private val viewModel: DocumentViewModel by activityViewModels()

    private val page: Page by lazy {
        viewModel.getPage(pageId)
    }

    private val titleTextview: TextView by lazy {
        requireView().findViewById(R.id.page_title)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "PageFragment onCreate ${pageId}")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d("MainActivity", "PageFragment onCreateView ${page}")
        return inflater.inflate(R.layout.fragment_page, container, false).apply {
            findViewById<TextView>(R.id.page_title).text = page.index.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "PageFragment onStart ${page}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "PageFragment onResume ${page}")

        titleTextview.text = page.index.toString()
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "PageFragment onPause ${page}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "PageFragment onStop ${page}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("MainActivity", "PageFragment onDestroyView ${page}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "PageFragment onDestroy ${page}")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(pageId: Long) =
            PageFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PAGE_ID, pageId)
                }
            }
    }
}