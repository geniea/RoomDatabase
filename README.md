# [Kotlin] Android Architecture ::Room Database
## Room Database

Room 은 Android [Jepack](https://developer.android.com/jetpack/) 의 sqlite database 라이브러리임.
![Room Database](https://github.com/geniea/RoomDatabase/blob/master/room_architecture_with_repository.png?raw=true)



### Step 1. dependency 추가
Project gradle file 에 라이브러리 버전 추가

```java
buildscript {
    ext {
        kotlin_version = '1.3.41'
        room_version = '2.2.0'
    }
    ....
  }
```

build.gradle (Module: app) 에 gradle dependency 추가

```
// Room dependencies
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"
```

### Step 1. data , DAO, database 클래스 추가

DB table 들을 토대로 data class를 추가 한다. DB는 Person, Marriage, MarriagePerson 3개의 테이블로 구성됨.
(주의) foreign key 가 존재할 경우, index를 설정해햐 함 (MarriagePerson class)

```java
//Person.kt
@Entity(tableName = "person_table")
data class Person(
    @PrimaryKey(autoGenerate = true)
    var personId: Int,
    @ColumnInfo(name="first_name")
    var firstName: String,
    @ColumnInfo(name="last_name")
    var lastName: String,
    @ColumnInfo(name="gender")
    var gender: Boolean
)
```

```java
//Marrage.kt
@Entity(tableName = "marriage_table")
data class Marriage(
    @PrimaryKey(autoGenerate = true)
    var marriageId: Int,
    @ColumnInfo(name = "marriage_date")
    var marriageDate: Date
)
```

```java
//MarriagePerson.kt
@Entity(tableName = "marriage_person_table",
    indices = [Index("marriage_id"), Index("person_id")],
    foreignKeys = [ForeignKey(entity = Marriage::class,
        parentColumns = arrayOf("marriageId"),
        childColumns = arrayOf("marriage_id"),
        onDelete = RESTRICT ),
        ForeignKey( entity = Person::class,
            parentColumns = arrayOf( "personId"),
            childColumns = arrayOf("person_id"),
            onDelete = RESTRICT ) ]
    )
data class MarriagePerson(
    @PrimaryKey(autoGenerate = true)
    var marriagePersonID: Int,
    @ColumnInfo(name = "marriage_id")
    val marriageId: Int,
    @ColumnInfo(name ="person_id")
    val personId: Int
)

```

DAO class 추가
```java
//PersonDao.kt
@Dao
interface PersonDao {
    @Insert
    fun insert(person:Person)

    @Query("SELECT * from person_table where personId = :key")
    fun get(key: Int): Person?

    @Query("SELECT * from person_table")
    fun getAll(): List<Person>
}

```

```java
//MarriageDao.kr
@Dao
interface MarriageDao{
    @Insert
    fun insert(marriage: Marriage)
    @Query("SELECT * from marriage_table where marriageId = :key ")
    fun get(key: Int): Marriage?
}
```

```java
//MarriagePersonDao.kt
@Dao
interface MarriagePersonDao {
    @Insert
    fun insert(marriagePerson: MarriagePerson)

    @Query("SELECT * from marriage_person_table where marriagePersonID = :key ")
    fun get(key: Int): MarriagePerson?
}
```

데이베이스   class 추가

```java
//PersonDatabase.kt
@Database(entities = [Person::class, Marriage::class, MarriagePerson::class],
    version = 1)
@TypeConverters(Converters::class)
abstract class PersonDatabase :RoomDatabase(){
    abstract val personDao: PersonDao
    abstract val marriageDao: MarriageDao
    abstract val marriagePersonDao: MarriagePersonDao

    companion object {
      @Volatile
        private var INSTANCE: PersonDatabase? = null
        fun getInstance(context: Context): PersonDatabase {

           synchronized(this) {
               var instance = INSTANCE

               // If instance is `null` make a new database instance.
               if (instance == null) {
                   instance = Room.databaseBuilder(
                       context.applicationContext,
                       PersonDatabase::class.java,
                       "person_database"
                   )
                       .fallbackToDestructiveMigration()
                       .build()
                   // Assign INSTANCE to the newly created database.
                   INSTANCE = instance
               }

               // Return instance; smart cast to be non-null.
               return instance
           }
       }
   }
}
```

### Step 2. 빌드 및 테스트

```java
//RoomDatabaseTest.kt
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
```
