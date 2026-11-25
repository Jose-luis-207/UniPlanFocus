
package mx.uttt.uniplanfocus.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "uniplan_database"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideCategoriaDao(
        database: AppDatabase
    ): CategoriaDao {
        return database.categoriaDao()
    }

    @Provides
    fun provideActividadDao(
        database: AppDatabase
    ): ActividadDao {
        return database.actividadDao()
    }

    @Provides
    fun provideHorarioDao(
        database: AppDatabase
    ): HorarioDao {
        return database.horarioDao()
    }

    @Provides
    fun provideRecordatorioDao(
        database: AppDatabase
    ): RecordatorioDao {
        return database.recordatorioDao()
    }
}
