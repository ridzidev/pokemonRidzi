import androidx.room.Database
import androidx.room.RoomDatabase
import com.ridzi.pokemon1.PokemonDao
import com.ridzi.pokemon1.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
