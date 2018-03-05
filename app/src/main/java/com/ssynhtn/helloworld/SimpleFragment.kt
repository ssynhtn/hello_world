package com.ssynhtn.helloworld

import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by huangtongnao on 2018/1/31.
 */
class SimpleFragment : Fragment() {

//    @BindView(R.id.text)
    lateinit var text: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView ${arguments?.getString(EXTRA_TEXT)}")
        return inflater.inflate(R.layout.simple_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        ButterKnife.bind(this, view)
        text = view.findViewById(R.id.text)

        val args = arguments
        if (args != null) {
            text.text = args.getString(EXTRA_TEXT)
        }
    }
}

const val EXTRA_TEXT = "text"
fun newInstance(text: String): SimpleFragment {
    val fragment = SimpleFragment()
    val args = Bundle()
    args.putString(EXTRA_TEXT, text)
    fragment.arguments = args
    return fragment
}