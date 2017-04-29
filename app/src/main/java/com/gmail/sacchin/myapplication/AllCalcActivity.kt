package com.gmail.sacchin.myapplication

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ArrayAdapter
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import kotlinx.android.synthetic.main.content_all_calc.*
import kotlin.properties.Delegates

class AllCalcActivity : AppCompatActivity() {

    var databaseHelper: DatabaseHelper by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_calc)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        databaseHelper = DatabaseHelper(this)

        val pokemonNames = ArrayList<String>()
        databaseHelper.selectAllPokemonMasterData().map { pokemonNames.add(it.jname) }
        val pokemonAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pokemonNames)
        pokemonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        name.adapter = pokemonAdapter

        val charAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Characteristic.NAMES)
        charAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        characteristic.adapter = charAdapter

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
