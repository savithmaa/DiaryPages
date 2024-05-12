package com.example.personaldiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personaldiary.databinding.ActivityAddDiaryPadesBinding
import com.example.personaldiary.databinding.ActivityUpdateDiaryBinding

class UpdateDiary : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDiaryBinding
    private lateinit var db : DiaryDatabaseHelper
    private var DiaryID: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryDatabaseHelper(this)

        DiaryID = intent.getIntExtra("diary_id", -1)
        if(DiaryID == -1){
            finish()
            return
        }

        val diary = db.getDiaryById(DiaryID)
        binding.editTextTitle.setText(diary.title)
        binding.editTextDateTime.setText(diary.date)
        binding.editTextContent.setText(diary.note)

        binding.saveButton.setOnClickListener{
            val newTitle = binding.editTextTitle.text.toString()
            val newContext = binding.editTextContent.text.toString()
            val newDate = binding.editTextDateTime.text.toString()
            val updateDiary = Diary(DiaryID, newTitle, newDate, newContext)
            db.updateDiary(updateDiary)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}