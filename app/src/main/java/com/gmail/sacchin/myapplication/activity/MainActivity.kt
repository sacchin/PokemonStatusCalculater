package com.gmail.sacchin.myapplication.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gmail.sacchin.myapplication.DatabaseHelper
import com.gmail.sacchin.myapplication.MegaPokemonInsertHandler
import com.gmail.sacchin.myapplication.PokemonInsertHandler
import com.gmail.sacchin.myapplication.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    var serviceStatePreferences: SharedPreferences by Delegates.notNull()
    var databaseHelper: DatabaseHelper by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceStatePreferences = getSharedPreferences("pokemon", MODE_PRIVATE)
        databaseHelper = DatabaseHelper(this)

        firstLaunch()

        all_calc?.onClick {
            startActivity(Intent(this, AllCalcActivity::class.java))
        }

        s_calc?.onClick {
            startActivity(Intent(this, SpeedCalcActivity::class.java))
        }
    }

    fun firstLaunch() {
        if (serviceStatePreferences.getBoolean("isFirst", true)) {
            PokemonInsertHandler(databaseHelper).run()
            MegaPokemonInsertHandler(databaseHelper).run()

            databaseHelper.insertSpeed()

            val editor = serviceStatePreferences.edit()
            editor.putBoolean("isFirst", false)
            editor.apply()
            Log.i("This is First Time", "create table!")
        }
    }
}
