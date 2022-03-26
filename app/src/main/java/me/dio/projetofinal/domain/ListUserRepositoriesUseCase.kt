package me.dio.projetofinal.domain

import kotlinx.coroutines.flow.Flow
import me.dio.projetofinal.core.UseCase
import me.dio.projetofinal.data.model.Repo
import me.dio.projetofinal.data.repositories.RepoRepository

class ListUserRepositoriesUseCase(private val repository: RepoRepository)
    : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}