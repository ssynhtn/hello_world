package com.ssynhtn.helloworld

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity

class ViewPagerCreateViewTwiceActivity : AppCompatActivity() {

//    @BindView(R.id.view_pager)
    lateinit var view_pager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_create_view_twice)

//        ButterKnife.bind(this)
        view_pager = findViewById(R.id.view_pager)

        val myAdapter = MyAdapter(supportFragmentManager, listOf(
                newInstance("hello"),
                newInstance("world"),
                newInstance("this"),
                newInstance("that"),
                newInstance("google")
        ))
        view_pager.offscreenPageLimit = myAdapter.count
        view_pager.adapter = myAdapter


    }

}

class MyAdapter(fm: FragmentManager, val frags: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return frags.size
    }

    override fun getItem(position: Int): Fragment {
        return frags[position]
    }

}
