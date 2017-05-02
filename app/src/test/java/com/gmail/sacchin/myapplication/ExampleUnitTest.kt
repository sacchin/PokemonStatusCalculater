package com.gmail.sacchin.myapplication

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import java.util.*

@RunWith(PowerMockRunner::class)
@PrepareForTest(DatabaseHelper::class)
class ExampleUnitTest {

    val pokemonList = ArrayList<PokemonMasterData>()

    @Before
    fun 初期化() {
        pokemonList.add(PokemonMasterData("003", "フシギバナ", "Venusaur", "", 80, 82, 83, 100, 100, 80, "しんりょく", "-", "ようりょくそ", 4, 7, 100.0f))
        pokemonList.add(PokemonMasterData("006", "リザードン", "Charizard", "", 78, 84, 78, 109, 85, 100, "もうか", "-", "サンパワー", 1, 9, 100.5f))
        pokemonList.add(PokemonMasterData("009", "カメックス", "Blastoise", "", 79, 83, 100, 85, 105, 78, "げきりゅう", "-", "あめうけざら", 2, -1, 85.5f))
        pokemonList.add(PokemonMasterData("012", "バタフリー", "Butterfree", "", 60, 45, 50, 90, 80, 70, "ふくがん", "-", "いろめがね", 11, 9, 32.0f))
        pokemonList.add(PokemonMasterData("015", "スピアー", "Beedrill", "", 65, 90, 40, 45, 80, 75, "むしのしらせ", "-", "スナイパー", 11, 7, 129.5f))
        pokemonList.add(PokemonMasterData("018", "ピジョット", "Pidgeot", "", 83, 80, 75, 70, 70, 91, "するどいめ", "ちどりあし", "はとむね", 0, 9, 39.5f))
        pokemonList.add(PokemonMasterData("020", "ラッタ", "Raticate", "", 55, 81, 60, 50, 70, 97, "にげあし", "こんじょう", "はりきり", 0, -1, 18.5f))
    }

    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun 検証スレテストケースその7() {
        val databaseHelper = PowerMockito.mock(DatabaseHelper::class.java)
        Mockito.`when`(databaseHelper.selectAllPokemonMasterData()).thenReturn(pokemonList)
//        Mockito.`when`(attackSide.typeBonus()).thenReturn(Pair(0, 1.5))
//        Mockito.`when`(attackSide.getAttackRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
//        Mockito.`when`(attackSide.side).thenReturn(PartyInBattle.MY_SIDE)
//        Mockito.`when`(attackSide.status).thenReturn(-1)
//        Mockito.`when`(attackSide.skill).thenReturn(Skill(216, "じゃれつく", "-", 17, 90, 90.0, 0, 10, 0, true, true, -1, 0.0, -1, 0.0, Rank.no(Rank.Code.mA), 10.0).uiObject())
//        Mockito.`when`(attackSide.individual).thenReturn(IndividualPokemonForUI(
//                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
//                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
//                PokemonMasterDataForUI("303", "クチート", "Mawile", "-", 50, 85, 85, 55, 55, 50, "かいりきバサミ", "いかく", "ちからずく", 16, 17, 11.0f)))
//
//        val defenseSide = PowerMockito.mock(PokemonForBattle::class.java)
//        Mockito.`when`(defenseSide.calcDefenseValue()).thenReturn(137.0)
//        Mockito.`when`(defenseSide.calcDefenseValueCorrection(attackSide)).thenReturn(8192.0)//ファーコート
//        Mockito.`when`(defenseSide.getDefenseRankCorrection(Mockito.anyBoolean())).thenReturn(1.0)
//        Mockito.`when`(defenseSide.noEffect(attackSide.skill, attackSide, field)).thenReturn(false)
//        Mockito.`when`(defenseSide.side).thenReturn(PartyInBattle.OPPONENT_SIDE)
//        Mockito.`when`(defenseSide.individual).thenReturn(IndividualPokemonForUI(
//                0, -1, "unknown", "unknown", "unknown", SkillForUI(), SkillForUI(), SkillForUI(), SkillForUI(),
//                252, 252, 0, 0, 0, 4, //31, 31, 31, 31, 31, 31,
//                PokemonMasterDataForUI("703", "メレシー", "Carbink", "", 50, 50, 150, 50, 150, 50, "クリアボディ", "-", "がんじょう", 12, 17, 5.7f)))
//
//        Mockito.`when`(attackSide.determineSkillPower(defenseSide)).thenReturn(90)
//        Mockito.`when`(attackSide.calcAttackValueCorrection(defenseSide, field)).thenReturn(Pair(4096.0, false))

        val combination = mutableMapOf<String, Int>()
        val s = mutableSetOf<Int>()
        databaseHelper.selectAllPokemonMasterData().map { s.add(it.s) }
        s.forEach { bs ->
            arrayOf(0, 4, 252).forEach { ev ->
                arrayOf(false, true).forEach { characteristic ->
                    Speed.OTHER_CORRECTION.forEach { correction ->
                        combination["${bs}族${ev}振${correction}倍"] = PokemonMasterData.otherFormula(bs, 31, ev)
                                .times(if (characteristic) 1.1 else 1.0).toInt().times(correction).toInt()
                    }
                }
            }
        }
        combination.forEach { it -> println("${it.key}:${it.value}") }
        Assert.assertTrue(s.first() == 80)
    }
}