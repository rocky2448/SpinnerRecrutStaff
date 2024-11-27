package com.example.spinnerrecrutstaff

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: Toolbar
    private lateinit var nameET: EditText
    private lateinit var surNameET: EditText
    private lateinit var ageET: EditText
    private lateinit var roleSpinnerS: Spinner
    private lateinit var saveBTN: Button
    private lateinit var listStaffLV: ListView
    private var persons: MutableList<Person> = mutableListOf()
    lateinit var personRole: String
    private var roles = mutableListOf(
        "Должность",
        "Инженер",
        "Программист",
        "Оператор",
        "Слесарь",
        "Электрик",
        "Технолог",
        "ОТК",
        "Мастер",
        "Токарь",
        "Фрезеровщик",
        "Шлифовщик",
        "Термист"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        nameET = findViewById(R.id.nameET)
        surNameET = findViewById(R.id.surNameET)
        ageET = findViewById(R.id.ageET)
        roleSpinnerS = findViewById(R.id.roleSpinnerS)
        saveBTN = findViewById(R.id.saveBTN)
        listStaffLV = findViewById(R.id.listStaffLV)

        setSupportActionBar(toolbarMain)
        title = "Подбор персонала"
        toolbarMain.subtitle = "by Rocky"

        val listAdapter = ListAdapter(this, persons)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            roles
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinnerS.adapter = adapter
        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    personRole = item
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        roleSpinnerS.onItemSelectedListener = itemSelectedListener

        listStaffLV.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val staff = persons[position].name
                persons.removeAt(position)
                listAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Пользователь $staff удалён", Toast.LENGTH_LONG).show()
            }

        saveBTN.setOnClickListener {
            if (nameET.text.isEmpty() ||
                surNameET.text.isEmpty() ||
                ageET.text.isEmpty() ||
                personRole == "Должность"
                ) return@setOnClickListener
            val personName = nameET.text.toString()
            val personSurName = surNameET.text.toString()
            val personAge = ageET.text.toString() + " лет"
            val person = Person(personName, personSurName, personAge, personRole)
            persons.add(person)
            listStaffLV.adapter = listAdapter
            listAdapter.notifyDataSetChanged()
            nameET.text.clear()
            surNameET.text.clear()
            ageET.text.clear()
            roleSpinnerS.setSelection(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}