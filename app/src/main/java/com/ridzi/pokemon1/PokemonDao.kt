package com.ridzi.pokemon1

import androidx.room.*

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemonEntities(): List<PokemonEntity>

    // Add a function to get all Pokemon
    @Transaction
    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemon(): List<Pokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: PokemonEntity)
}


