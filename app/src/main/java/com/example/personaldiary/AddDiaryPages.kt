package com.example.personaldiary

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.material3.DatePickerDialog
import com.example.personaldiary.databinding.ActivityAddDiaryPadesBinding
import com.example.personaldiary.databinding.ActivityDiaryPagesBinding
import java.util.Calendar

class AddDiaryPages : AppCompatActivity() {

    private lateinit var editTextDateTime: EditText
    private lateinit var binding: ActivityAddDiaryPadesBinding
    private lateinit var db: DiaryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDiaryPadesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextDateTime = findViewById(R.id.editTextDateTime)
        editTextDateTime.setOnClickListener {
            showDatePicker()
        }

        db = DiaryDatabaseHelper(this)

        binding.saveButton.setOnClickListener{
            val title = binding.editTextTitle.text.toString()
            val date = binding.editTextDateTime.text.toString()
            val diaryNote = binding.editTextContent.text.toString()
            val diary = Diary(0, title, date, diaryNote)
            db.addDiary(diary)
            finish()
            Toast.makeText(this, "Diary Saved", Toast.LENGTH_SHORT).show()
        }


    }

    private fun showDatePicker() {
        val editTextDateTime = findViewById<EditText>(R.id.editTextDateTime)

        // Get current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Show DatePickerDialog

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            editTextDateTime.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}