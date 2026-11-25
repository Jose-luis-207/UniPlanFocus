
package mx.uttt.uniplanfocus.data.di

import mx.uttt.uniplanfocus.data.repository.ActividadRepositoryImp
import mx.uttt.uniplanfocus.data.repository.CategoriaRepositoryImp
import mx.uttt.uniplanfocus.data.repository.HorarioRepositoryImp
import mx.uttt.uniplanfocus.data.repository.RecordatorioRepositoryImp

import mx.uttt.uniplanfocus.domain.repository.ActividadRepository
import mx.uttt.uniplanfocus.domain.repository.CategoriaRepository
import mx.uttt.uniplanfocus.domain.repository.HorarioRepository
import mx.uttt.uniplanfocus.domain.repository.RecordatorioRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideCategoriaRepository(
        repository: CategoriaRepositoryImp
    ): CategoriaRepository

    @Binds
    @Singleton
    abstract fun provideActividadRepository(
        repository: ActividadRepositoryImp
    ): ActividadRepository

    @Binds
    @Singleton
    abstract fun provideHorarioRepository(
        repository: HorarioRepositoryImp
    ): HorarioRepository

    @Binds
    @Singleton
    abstract fun provideRecordatorioRepository(
        repository: RecordatorioRepositoryImp
    ): RecordatorioRepository
}
