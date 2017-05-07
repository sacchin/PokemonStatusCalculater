package com.gmail.sacchin.myapplication.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gmail.sacchin.myapplication.DatabaseHelper
import com.gmail.sacchin.myapplication.entity.PokemonForCalc
import com.gmail.sacchin.myapplication.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import kotlinx.android.synthetic.main.activity_all_calc.*
import kotlinx.android.synthetic.main.content_all_calc.*
import kotlin.properties.Delegates

class AllCalcActivity : AppCompatActivity() {

    var databaseHelper: DatabaseHelper by Delegates.notNull()
    var forCalc: PokemonForCalc = PokemonForCalc()
    var selectedPokemon: String = ""
    val pokemonNames = ArrayList<String>()
    val ivs = ArrayList<String>()
    val megas = arrayOf("-", "X", "Y")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_calc)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        databaseHelper = DatabaseHelper(this)
        databaseHelper.selectAllPokemonMasterData().map { pokemonNames.add(it.jname) }

        name.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                pokemonNames).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        name.onItemSelectedListener = OnNameSelectedListener()

        characteristic.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                Characteristic.NAMES).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        characteristic.onItemSelectedListener = OnCharacteristicSelectedListener()

        mega.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                megas).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        mega.onItemSelectedListener = OnMegaSelectedListener()


        (0..31).reversed().map { ivs.add(it.toString()) }
        val ivLabel = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                ivs).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        hIv.apply {
            adapter = ivLabel
            onItemSelectedListener = OnIvSelectedListener(0)
        }
        aIv.apply {
            adapter = ivLabel
            onItemSelectedListener = OnIvSelectedListener(1)
        }
        bIv.apply {
            adapter = ivLabel
            onItemSelectedListener = OnIvSelectedListener(2)
        }
        cIv.apply {
            adapter = ivLabel
            onItemSelectedListener = OnIvSelectedListener(3)
        }
        dIv.apply {
            adapter = ivLabel
            onItemSelectedListener = OnIvSelectedListener(4)
        }
        sIv.apply {
            adapter = ivLabel
            onItemSelectedListener = OnIvSelectedListener(5)
        }

        hEv.addTextChangedListener(OnEvEditListener(0))
        aEv.addTextChangedListener(OnEvEditListener(1))
        bEv.addTextChangedListener(OnEvEditListener(2))
        cEv.addTextChangedListener(OnEvEditListener(3))
        dEv.addTextChangedListener(OnEvEditListener(4))
        sEv.addTextChangedListener(OnEvEditListener(5))

        fab.setOnClickListener { calc() }
    }

    fun calc() {
        forCalc.master = databaseHelper.selectPokemonByName(selectedPokemon)

        Log.v("calc", forCalc.toString())

        hAv.text = forCalc.hp().toString()
        aAv.text = forCalc.attack().toString()
        bAv.text = forCalc.defense().toString()
        cAv.text = forCalc.specialAttack().toString()
        dAv.text = forCalc.specialDefense().toString()
        sAv.text = forCalc.speed().toString()
    }

    inner class OnNameSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedPokemon = pokemonNames.get(id.toInt())
        }
    }

    inner class OnCharacteristicSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            forCalc.characteristic = Characteristic.NAMES.get(id.toInt())
        }
    }

    inner class OnMegaSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            forCalc.mega = id.toInt()
        }
    }

    inner class OnIvSelectedListener(val witch: Int) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val iv = ivs.get(id.toInt()).toInt()
            when (witch) {
                0 -> {
                    forCalc.hpIv = iv
                }
                1 -> {
                    forCalc.attackIv = iv
                }
                2 -> {
                    forCalc.defenseIv = iv
                }
                3 -> {
                    forCalc.specialAttackIv = iv
                }
                4 -> {
                    forCalc.specialDefenseIv = iv
                }
                5 -> {
                    forCalc.speedIv = iv
                }
            }
        }
    }

    inner class OnEvEditListener(val witch: Int) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val ev = if (s.toString().isNullOrBlank()) 0 else Integer.parseInt(s.toString())
            when (witch) {
                0 -> {
                    forCalc.hpEv = ev
                }
                1 -> {
                    forCalc.attackEv = ev
                }
                2 -> {
                    forCalc.defenseEv = ev
                }
                3 -> {
                    forCalc.specialAttackEv = ev
                }
                4 -> {
                    forCalc.specialDefenseEv = ev
                }
                5 -> {
                    forCalc.speedEv = ev
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}
