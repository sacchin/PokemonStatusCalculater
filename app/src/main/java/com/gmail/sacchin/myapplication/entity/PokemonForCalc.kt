package com.gmail.sacchin.myapplication.entity

import com.gmail.sacchin.myapplication.entity.MegaPokemonMasterData
import com.gmail.sacchin.myapplication.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Characteristic
import kotlin.properties.Delegates

class PokemonForCalc {
    var characteristic: String = Characteristic.NAMES[0]
    var mega: Int = 0
    var hpIv: Int = 31
    var attackIv: Int = 31
    var defenseIv: Int = 31
    var specialAttackIv: Int = 31
    var specialDefenseIv: Int = 31
    var speedIv: Int = 31
    var hpEv: Int = 0
    var attackEv: Int = 0
    var defenseEv: Int = 0
    var specialAttackEv: Int = 0
    var specialDefenseEv: Int = 0
    var speedEv: Int = 0
    var master: PokemonMasterData by Delegates.notNull()

    fun hp(): Int {
        return when (mega) {
            MegaPokemonMasterData.MEGA_X -> {
                master.hpX(hpIv, hpEv)
            }
            MegaPokemonMasterData.MEGA_Y -> {
                master.hpY(hpIv, hpEv)
            }
            else -> {
                master.hp(hpIv, hpEv)
            }
        }
    }

    fun attack(): Int {
        val cor = Characteristic.correction(characteristic, "A")
        return when (mega) {
            MegaPokemonMasterData.MEGA_X -> {
                master.attackX(attackIv, attackEv)
            }
            MegaPokemonMasterData.MEGA_Y -> {
                master.attackY(attackIv, attackEv)
            }
            else -> {
                master.attack(attackIv, attackEv)
            }
        }.times(cor).toInt()
    }

    fun defense(): Int {
        val cor = Characteristic.correction(characteristic, "B")
        return when (mega) {
            MegaPokemonMasterData.MEGA_X -> {
                master.defenseX(defenseIv, defenseEv)
            }
            MegaPokemonMasterData.MEGA_Y -> {
                master.defenseY(defenseIv, defenseEv)
            }
            else -> {
                master.defense(defenseIv, defenseEv)
            }
        }.times(cor).toInt()
    }

    fun specialAttack(): Int {
        val cor = Characteristic.correction(characteristic, "C")
        return when (mega) {
            MegaPokemonMasterData.MEGA_X -> {
                master.specialAttackX(specialAttackIv, specialAttackEv)
            }
            MegaPokemonMasterData.MEGA_Y -> {
                master.specialAttackY(specialAttackIv, specialAttackEv)
            }
            else -> {
                master.specialAttack(specialAttackIv, specialAttackEv)
            }
        }.times(cor).toInt()
    }

    fun specialDefense(): Int {
        val cor = Characteristic.correction(characteristic, "D")
        return when (mega) {
            MegaPokemonMasterData.MEGA_X -> {
                master.specialDefenseX(specialDefenseIv, specialDefenseEv)
            }
            MegaPokemonMasterData.MEGA_Y -> {
                master.specialDefenseY(specialDefenseIv, specialDefenseEv)
            }
            else -> {
                master.specialDefense(specialDefenseIv, specialDefenseEv)
            }
        }.times(cor).toInt()
    }

    fun speed(): Int {
        val cor = Characteristic.correction(characteristic, "S")
        return when (mega) {
            MegaPokemonMasterData.MEGA_X -> {
                master.speedX(speedIv, speedEv)
            }
            MegaPokemonMasterData.MEGA_Y -> {
                master.speedY(speedIv, speedEv)
            }
            else -> {
                master.speed(speedIv, speedEv)
            }
        }.times(cor).toInt()
    }

    override fun toString(): String {
        return "${master.jname}, $characteristic, " +
                "$hpIv, $attackIv, $defenseIv, $specialAttackIv, $specialDefenseIv, " +
                "$speedIv, $hpEv, $attackEv, $defenseEv, $specialAttackEv, $specialDefenseEv, " +
                "$speedEv"
    }
}