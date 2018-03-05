package com.ssynhtn.helloworld

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import butterknife.ButterKnife
import kotterknife.bindView

class RecyclerViewTestActivity : AppCompatActivity() {

    val recycler_view: RecyclerView by bindView(R.id.recycler_view)
//    lateinit var headerHolder: SecondViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_test)

        ButterKnife.bind(this)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = SimpleAdapter(100)


//        headerHolder = recycler_view.findViewHolderForAdapterPosition(0) as SecondViewHolder
//        Log.d("TAG", "header " + headerHolder)

        testPrefs()

        testRx()

    }

    private fun testRx() {
//        Observable.create<String> { it.onNext("hello") }
    }

    private fun testPrefs() {
        val prefs = getSharedPreferences("config", Context.MODE_PRIVATE)
        prefs.edit().putString("hello", "world").apply()
        Toast.makeText(this, "hello: " + prefs.getString("hello", "default"), Toast.LENGTH_SHORT).show()
    }
}

class SimpleAdapter(val count: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.header_view
            else -> R.layout.checkbox
        }
    }

    override fun getItemCount(): Int {
        return count + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder  -> {
                holder.tv.text = holder.itemView.toString()
            }
            is SecondViewHolder -> {
                holder.tv.text = holder.itemView.toString()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        when (viewType) {
            R.layout.checkbox -> return ViewHolder(view)
            else -> return SecondViewHolder(view)
        }
    }

}

class SecondViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tv: TextView by bindView(R.id.tv)

}
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val check: CheckBox by bindView(R.id.check)
    val tv: TextView by bindView(R.id.tv)

    init {
//        setIsRecyclable(false)
    }


}
