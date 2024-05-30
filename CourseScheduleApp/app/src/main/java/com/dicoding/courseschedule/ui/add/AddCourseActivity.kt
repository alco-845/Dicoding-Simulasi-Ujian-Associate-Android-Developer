package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.dicoding.courseschedule.util.timeFormat
import com.google.android.material.textfield.TextInputEditText

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = resources.getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        init()
    }

    private fun init() {
        setTimeDialog()
        setSpinner()
    }

    private fun setTimeDialog() {
        val ibTimeStart = findViewById<ImageButton>(R.id.ib_start_time)
        val ibTimeEnd = findViewById<ImageButton>(R.id.ib_end_time)

        ibTimeStart.setOnClickListener {
            val timeDialog = TimePickerFragment()
            timeDialog.show(supportFragmentManager, "timeStart")
        }

        ibTimeEnd.setOnClickListener {
            val timeDialog = TimePickerFragment()
            timeDialog.show(supportFragmentManager, "timeEnd")
        }
    }

    private fun setSpinner() {
        val day = resources.getStringArray(R.array.day)
        val spinner = findViewById<Spinner>(R.id.spDay)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, day)
        spinner.adapter = adapter
    }

    private fun insertData() {
        val courseName = findViewById<TextInputEditText>(R.id.add_ed_course_name).text.toString()
        val day = findViewById<Spinner>(R.id.spDay).selectedItemPosition
        val timeStart = findViewById<TextView>(R.id.add_tv_start_time).text.toString()
        val timeEnd = findViewById<TextView>(R.id.add_tv_time_end).text.toString()
        val lecturer = findViewById<TextInputEditText>(R.id.add_ed_lecturer).text.toString()
        val note = findViewById<TextInputEditText>(R.id.add_ed_description).text.toString()

        viewModel.insertCourse(courseName, day, timeStart, timeEnd, lecturer, note)
        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == false) {
                Toast.makeText(this, getString(R.string.txt_empty_column), Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_insert -> {
                insertData()
            }
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int){
        if (tag == "timeStart") {
            findViewById<TextView>(R.id.add_tv_start_time).text = timeFormat(("$hour:$minute").toString())
        } else if (tag == "timeEnd") {
            findViewById<TextView>(R.id.add_tv_time_end).text = timeFormat(("$hour:$minute").toString())
        }
    }
}