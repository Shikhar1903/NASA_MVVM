package com.example.nasa_mvvm.view

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.nasa_mvvm.R
import com.example.nasa_mvvm.model.Items
import com.example.nasa_mvvm.model.MainModel
import com.example.nasa_mvvm.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //lateinit var button: Button
    lateinit var mDateSetListener: DatePickerDialog.OnDateSetListener
    var date: String = getCalculatedDate("yyyy-MM-dd",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var viewModel=ViewModelProviders.of(this).get(MainViewModel::class.java)

        progressBar.visibility=View.GONE

        textView.setOnClickListener {

            var cal: Calendar = Calendar.getInstance()
            var year: Int = cal.get(Calendar.YEAR)
            var month: Int = cal.get(Calendar.MONTH)
            var day: Int = cal.get(Calendar.DAY_OF_MONTH)
            var dialog = DatePickerDialog(
                this@MainActivity, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth,
                mDateSetListener, year, month, day
            )
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }
        mDateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                Log.d("date-", "$year $month $dayOfMonth")
                val month1 = month + 1
                date = "$year-$month1-$dayOfMonth"
                textView.text = date

            }

        button.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            viewModel.getUrlFromModel(date).observe(this, androidx.lifecycle.Observer { data->loadImage(data) })
        }
    }


    private fun loadImage(it: Items?) {

                Log.d("check in view",it!!.url)
                var urlFinal=it!!.url
                var APOD: ImageView = findViewById(R.id.imageView)
                Glide.with(this)
                    .load(urlFinal)
                    .listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(resource: Drawable?, model: Any?,
                                                     target: Target<Drawable>?, dataSource: DataSource?,
                                                     isFirstResource: Boolean): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                                  isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
                    .into(APOD)
            }

    }

    fun getCalculatedDate(dateFormat: String, days: Int): String {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat(dateFormat)
        cal.add(Calendar.DAY_OF_YEAR, days)
        return s.format(Date(cal.timeInMillis))
    }



