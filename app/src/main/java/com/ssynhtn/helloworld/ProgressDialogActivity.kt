package com.ssynhtn.helloworld

import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast

class ProgressDialogActivity : AppCompatActivity() {

    lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_dialog)

        findViewById<Button>(R.id.btn_dialog).setOnClickListener(View.OnClickListener {
            MyTask().execute()
        })
    }


    override fun onDestroy() {
        super.onDestroy()

//        Log.d("test", "isFinishing " + isFinishing)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            Log.d("test", "isDestroyed " + isDestroyed)
//        }
//
//
//        dismissAlertDialog()

    }


    inner class MyTask : AsyncTask<Unit, Unit, Unit>() {

        override fun onPreExecute() {
            super.onPreExecute()

            val builder = AlertDialog.Builder(this@ProgressDialogActivity)
            builder.setTitle("Hello")
                    .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, which ->
                        Toast.makeText(this@ProgressDialogActivity, "click ok", Toast.LENGTH_SHORT).show()
                    })

            dialog = builder.create()
            dialog.show()

        }
        override fun doInBackground(vararg params: Unit): Unit {
            try {
                Thread.sleep(3000)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)

            dismissAlertDialog()
//            Log.d("lala", "isFinishing $isFinishing")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                Log.d("lala", "isDestroyed $isDestroyed")
//            }
//
//            if (!isFinishing) {
//                dismissAlertDialog()
//            }

        }
    }

    private fun dismissAlertDialog() {
        if (dialog != null && dialog.isShowing) dialog.dismiss()
    }

}

