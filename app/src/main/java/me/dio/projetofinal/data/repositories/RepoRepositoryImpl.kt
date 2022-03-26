package me.dio.projetofinal.data.repositories

import kotlinx.coroutines.flow.flow
import me.dio.projetofinal.core.RemoteException
import me.dio.projetofinal.data.services.GitHubServices
import retrofit2.HttpException

class RepoRepositoryImpl(private val service: GitHubServices) : RepoRepository {

    override suspend fun listRepositories(user: String) = flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        } catch (ex: HttpException) {
            throw RemoteException(ex.message ?: "Não foi possivel fazer a busca no momento!")
        }
    }
}