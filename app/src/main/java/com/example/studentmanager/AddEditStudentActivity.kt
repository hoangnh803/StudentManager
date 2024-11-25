package com.example.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanager.R

class AddEditStudentActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        etName = findViewById(R.id.etName)
        etStudentId = findViewById(R.id.etStudentId)
        btnSave = findViewById(R.id.btnSave)

        val name = intent.getStringExtra("name")
        val studentId = intent.getStringExtra("studentId")

        if (name != null && studentId != null) {
            etName.setText(name)
            etStudentId.setText(studentId)
        }

        btnSave.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("name", etName.text.toString())
            resultIntent.putExtra("studentId", etStudentId.text.toString())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
