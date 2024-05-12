package com.example.personaldiary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Calendar

class DiaryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DatbaseName, null, DatabaseVersion){

    companion object{

        private const val DatbaseName = "Diary.db"
        private const val DatabaseVersion = 1
        private const val TableName = "DiaryNotes"
        private const val Colomid = "id"
        private const val ColomnTitle = "Title"
        private const val ColomnDate = "Date"
        private const val ColomnNotes = "Notes"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TableName ($Colomid INTEGER PRIMARY KEY, $ColomnTitle TEXT, $ColomnDate TEXT, $ColomnNotes TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TableName")
        onCreate(db)
    }

    fun addDiary(diary: Diary): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ColomnTitle, diary.title)
        values.put(ColomnDate, diary.date.toString()) // Store date as milliseconds
        values.put(ColomnNotes, diary.note)
        return db.insert(TableName, null, values)
    }

    fun getAllDiaries(): List<Diary> {
        val diaries = mutableListOf<Diary>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TableName", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Colomid))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(ColomnTitle))
                val date = cursor.getString(cursor.getColumnIndexOrThrow(ColomnDate))
                val note = cursor.getString(cursor.getColumnIndexOrThrow(ColomnNotes))
                val diary = Diary(id, title, date, note)
//                val diary = Diary(id, title, note, calendar)
                diaries.add(diary)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return diaries
    }

    fun  updateDiary(diary: Diary){
        val db = writableDatabase
        val Values = ContentValues().apply {
            put(ColomnTitle, diary.title)
            put(ColomnDate, diary.date)
            put(ColomnNotes, diary.note)
        }
        val whereClause = "$Colomid = ?"
        val whereArds = arrayOf(diary.id.toString())
        db.update(TableName, Values, whereClause, whereArds)
        db.close()
    }

    fun getDiaryById(diaryID: Int): Diary{
        val db =readableDatabase
        val query = "SELECT * FROM $TableName WHERE $Colomid = $diaryID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Colomid))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(ColomnTitle))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(ColomnDate))
        val contetnt = cursor.getString(cursor.getColumnIndexOrThrow(ColomnNotes))

        cursor.close()
        db.close()
        return Diary(id, title, date, contetnt)
    }

    fun deleteDiary(diaryID : Int){
        val db = writableDatabase
        val whereCluase = "$Colomid = ?"
        val whereArgs = arrayOf(diaryID.toString())
        db.delete(TableName, whereCluase, whereArgs)
        db.close()
    }
}