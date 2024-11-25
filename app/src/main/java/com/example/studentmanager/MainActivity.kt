package com.example.studentmanager

import Student
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanager.R

class MainActivity : AppCompatActivity() {

    private lateinit var studentList: MutableList<Student>
    private lateinit var adapter: ArrayAdapter<Student>
    private lateinit var lvStudents: ListView
    private var selectedPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set up the toolbar as the action bar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        lvStudents = findViewById(R.id.lvStudents)
        studentList = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList)
        lvStudents.adapter = adapter

        // Register context menu
        registerForContextMenu(lvStudents)

        lvStudents.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            selectedPosition = position
        }
    }

    // Inflate OptionMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Handle OptionMenu selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_new -> {
                val intent = Intent(this, AddEditStudentActivity::class.java)
                startActivityForResult(intent, 100)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Inflate Context Menu
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Handle Context Menu selection
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        selectedPosition = info.position

        when (item.itemId) {
            R.id.edit -> {
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("name", studentList[selectedPosition].name)
                intent.putExtra("studentId", studentList[selectedPosition].studentId)
                startActivityForResult(intent, 101)
            }
            R.id.remove -> {
                AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton("Yes") { _, _ ->
                        studentList.removeAt(selectedPosition)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val name = data.getStringExtra("name") ?: ""
            val studentId = data.getStringExtra("studentId") ?: ""
            if (requestCode == 100) {
                studentList.add(Student(name, studentId))
            } else if (requestCode == 101) {
                studentList[selectedPosition] = Student(name, studentId)
            }
            adapter.notifyDataSetChanged()
        }
    }
}
