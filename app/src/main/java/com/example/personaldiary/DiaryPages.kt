package com.example.personaldiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personaldiary.databinding.ActivityDiaryPagesBinding

class DiaryPages : AppCompatActivity() {

    private lateinit var binding: ActivityDiaryPagesBinding
    private lateinit var db: DiaryDatabaseHelper
    private lateinit var diaryAdapter: DiaryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityDiaryPagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DiaryDatabaseHelper(this)
        diaryAdapter = DiaryAdapter(db.getAllDiaries(), this)

        binding.recyclerViewDiary.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDiary.adapter = diaryAdapter


        binding.Addbutton.setOnClickListener{
            val intent = Intent(this, AddDiaryPages::class.java)
           startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload diary notes when the activity is resumed
        diaryAdapter.refreshData(db.getAllDiaries())
    }
}