package com.gmail.sacchin.myapplication.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.gmail.sacchin.myapplication.DatabaseHelper
import com.gmail.sacchin.myapplication.R
import com.gmail.sacchin.myapplication.entity.PokemonForCalc
import com.gmail.sacchin.myapplication.entity.PokemonMasterData
import com.gmail.sacchin.myapplication.entity.Speed
import kotlinx.android.synthetic.main.content_speed_calc.*
import kotlin.properties.Delegates

class SpeedCalcActivity : AppCompatActivity() {

    var databaseHelper: DatabaseHelper by Delegates.notNull()
    var forCalc: PokemonForCalc = PokemonForCalc()
    val pokemonNames = ArrayList<String>()
    val megas = arrayOf("-", "X", "Y")
    val list = ArrayList<Speed>()
    var opponent by Delegates.notNull<ArrayList<Speed>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speed_calc)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        databaseHelper = DatabaseHelper(this)
        databaseHelper.selectAllPokemonMasterData().map { pokemonNames.add(it.jname) }
        opponent = databaseHelper.selectAllSpeed()

        name.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                pokemonNames).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        name.onItemSelectedListener = OnNameSelectedListener()

        megatype.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                megas).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        megatype.onItemSelectedListener = OnMegaSelectedListener()

        candidate.adapter = ArrayAdapter<Speed>(this,
                android.R.layout.simple_list_item_1, opponent)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    fun calc(name: String) {
        list.clear()
        forCalc.master = databaseHelper.selectPokemonByName(name)

        arrayOf(0, 4, 12, 20, 28, 36, 44, 52, 60, 68, 76, 84, 92, 100,
                108, 116, 124, 132, 140, 148, 156, 164, 172, 180, 188,
                196, 204, 212, 220, 228, 236, 244, 252).forEach { ev ->
            arrayOf(false, true).forEach { characteristic ->
                Speed.OTHER_CORRECTION.forEach { correction ->
                    list.add(Speed().apply {
                        this.bs = forCalc.master.s
                        this.ev = ev
                        this.characteristic = characteristic
                        this.other = correction
                        this.av = PokemonMasterData.otherFormula(forCalc.master.s, 31, ev)
                                .times(charCorrection()).toInt().times(otherCorrection()).toInt()
                    })
                }
            }
        }

        list.sortBy { it.av }

        max.text = list.first().av.toString()
        seekBar.max = list.size - 1
        seekBar.setOnSeekBarChangeListener(OnSeekbarChangeListener())
        seekBar.setProgress(0, true)
    }

    fun filter(speed: Int) {
        val filtered = opponent.filter { speed <= it.av }
        candidate.adapter = ArrayAdapter<Speed>(this,
                android.R.layout.simple_list_item_1, filtered)

        Log.e("filter", "$speed, ${opponent.size} -> ${filtered.size}")
    }

    inner class OnNameSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            calc(pokemonNames[id.toInt()])
        }
    }

    inner class OnMegaSelectedListener : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            forCalc.mega = id.toInt()
        }
    }

    inner class OnSeekbarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            list[progress].let {
                max.text = it.av.toString()
                characteristic.text = if (it.characteristic) "補正あり" else "補正なし"
                ev.text = it.ev.toString()
                other.text = it.otherCorrection().toString()
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            val p = seekBar?.progress ?: 0
            filter(list[p].av)
        }
    }
}
