
package mx.uttt.uniplanfocus.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        CategoriaEntity::class,
        ActividadEntity::class,
        HorarioEntity::class,
        RecordatorioEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoriaDao(): CategoriaDao

    abstract fun actividadDao(): ActividadDao

    abstract fun horarioDao(): HorarioDao

    abstract fun recordatorioDao(): RecordatorioDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS horarios (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        actividadId INTEGER NOT NULL,
                        diaSemana TEXT NOT NULL,
                        horaInicio TEXT NOT NULL,
                        horaFin TEXT NOT NULL,
                        FOREIGN KEY(actividadId) REFERENCES actividades(id)
                        ON UPDATE NO ACTION ON DELETE NO ACTION
                    )
                """)

                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS recordatorios (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        actividadId INTEGER NOT NULL,
                        mensaje TEXT NOT NULL,
                        fechaHora INTEGER NOT NULL,
                        activo INTEGER NOT NULL,
                        FOREIGN KEY(actividadId) REFERENCES actividades(id)
                        ON UPDATE NO ACTION ON DELETE NO ACTION
                    )
                """)
            }
        }
    }
}
