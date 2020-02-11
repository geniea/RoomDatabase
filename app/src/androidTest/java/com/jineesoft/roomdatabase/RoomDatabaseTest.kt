package com.jineesoft.roomdatabase

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jineesoft.roomdatabase.data.Person
import com.jineesoft.roomdatabase.database.PersonDao
import com.jineesoft.roomdatabase.database.PersonDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var personDao: PersonDao
    private lateinit var db: PersonDatabase


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, PersonDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        personDao = db.personDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetPerson() {

        val person = Person(1,"Lee","Young",true)
        personDao.insert(person)
        val chkPerson = personDao.get(1)
        Log.i("Room Test", "Person's first name is ${chkPerson?.firstName}")
    }
}