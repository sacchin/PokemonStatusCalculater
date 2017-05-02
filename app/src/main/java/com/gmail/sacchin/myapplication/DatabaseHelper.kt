package com.gmail.sacchin.myapplication

import android.content.Context
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Type
import io.realm.Realm
import io.realm.Sort
import java.util.*
import kotlin.properties.Delegates

open class DatabaseHelper(context: Context) {
    var realm: Realm by Delegates.notNull()

    init {
        Realm.init(context)
        realm = Realm.getDefaultInstance()
    }

    fun insertPokemonMasterData(no: String, jname: String, ename: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                                ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {
        insertPokemonMasterData(no, jname, ename, "-", h, a, b, c, d, s, ability1, ability2, abilityd, type1, type2, weight)
    }

    fun insertPokemonMasterData(no: String, jname: String, ename: String, form: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int,
                                ability1: String, ability2: String, abilityd: String, type1: Int, type2: Int, weight: Double) {
        realm.executeTransaction {
            realm.createObject(PokemonMasterData::class.java).apply {
                this.no = no
                this.jname = jname
                this.ename = ename
                this.h = h
                this.a = a
                this.b = b
                this.c = c
                this.d = d
                this.s = s
                this.form = form
                this.ability1 = ability1
                this.ability2 = ability2
                this.abilityd = abilityd
                this.type1 = type1
                this.type2 = type2
                this.weight = weight.toFloat()
            }
        }
    }

    fun insertMegaPokemonDataX(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, type1: Int, type2: Int, ability: String, weight: Float) {
        realm.executeTransaction {
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java).apply {
                this.pokemonNo = no
                this.h = h
                this.a = a
                this.b = b
                this.c = c
                this.d = d
                this.s = s
                this.type1 = type1
                this.type2 = type2
                this.ability = ability
                this.weight = weight
                this.megaType = MegaPokemonMasterData.MEGA_X
            }

            val master = selectPokemonMasterData(no)
            master.megax = megaPokemon
        }
    }

    fun insertMegaPokemonDataX(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, ability: String, weight: Float) {
        insertMegaPokemonDataX(no, h, a, b, c, d, s, Type.no(Type.Code.UNKNOWN), Type.no(Type.Code.UNKNOWN), ability, weight)
    }

    fun insertMegaPokemonDataY(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, type1: Int, type2: Int, ability: String, weight: Float) {
        realm.executeTransaction {
            val megaPokemon = realm.createObject(MegaPokemonMasterData::class.java).apply {
                this.pokemonNo = no
                this.h = h
                this.a = a
                this.b = b
                this.c = c
                this.d = d
                this.s = s
                this.type1 = type1
                this.type2 = type2
                this.ability = ability
                this.weight = weight
                this.megaType = MegaPokemonMasterData.MEGA_Y
            }

            val master = selectPokemonMasterData(no)
            master.megay = megaPokemon
        }
    }

    fun insertSpeed() {
        realm.executeTransaction {
            val s = mutableSetOf<Int>()
            selectAllPokemonMasterData().map { s.add(it.s) }
            s.forEach { bs ->
                arrayOf(0, 4, 252).forEach { ev ->
                    arrayOf(false, true).forEach { characteristic ->
                        Speed.OTHER_CORRECTION.forEach { correction ->
                            realm.createObject(Speed::class.java).apply {
                                this.bs = bs
                                this.ev = ev
                                this.av = PokemonMasterData.otherFormula(bs, 31, ev)
                                this.characteristic = characteristic
                                this.other = correction
                            }
                        }
                    }
                }
            }
        }
    }

    fun insertMegaPokemonDataY(no: String, h: Int, a: Int, b: Int, c: Int, d: Int, s: Int, ability: String, weight: Float) {
        insertMegaPokemonDataY(no, h, a, b, c, d, s, Type.no(Type.Code.UNKNOWN), Type.no(Type.Code.UNKNOWN), ability, weight)
    }


    fun selectPokemonMasterData(pokemonNo: String?): PokemonMasterData {
        val pokemon = realm.where(PokemonMasterData().javaClass).equalTo("no", pokemonNo).findFirst()
        if (pokemon != null) {
            return pokemon
        }
        return realm.where(PokemonMasterData().javaClass).equalTo("no", "000").findFirst()
    }

    fun selectPokemonByName(pokemonName: String): PokemonMasterData {
        val pokemon = realm.where(PokemonMasterData().javaClass).equalTo("jname", pokemonName).findFirst()
        if (pokemon != null) {
            return pokemon
        }
        return realm.where(PokemonMasterData().javaClass).equalTo("no", "000").findFirst()
    }

    fun selectAllPokemonMasterData(): ArrayList<PokemonMasterData> {
        val pokemonList = realm.where(PokemonMasterData().javaClass).findAllSorted("no", Sort.DESCENDING)

        val result = ArrayList<PokemonMasterData>()
        pokemonList.map { result.add(it) }
        return result
    }

    fun selectAllSpeed(): ArrayList<Speed> {
        val list = realm.where(Speed().javaClass).findAll()

        val result = ArrayList<Speed>()
        list.map { result.add(it) }
        return result
    }
}