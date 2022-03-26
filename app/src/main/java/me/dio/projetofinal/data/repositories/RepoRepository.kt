package me.dio.projetofinal.data.repositories

import me.dio.projetofinal.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun listRepositories(user : String): Flow<List<Repo>>
}