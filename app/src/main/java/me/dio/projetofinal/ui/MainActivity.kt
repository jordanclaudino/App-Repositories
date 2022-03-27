package me.dio.projetofinal.ui


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.dio.projetofinal.R
import me.dio.projetofinal.core.createDialog
import me.dio.projetofinal.core.createProgressDialog
import me.dio.projetofinal.core.hideSoftKeyboard


import me.dio.projetofinal.databinding.ActivityMainBinding
import me.dio.projetofinal.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog()}
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.rvRepos.adapter = adapter

        viewModel.repos.observe(this){
            when(it){
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    createDialog{
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                    adapter.onItemClick = { repo ->
                        val builder = MaterialAlertDialogBuilder(this)
                        builder.setTitle(adapter.nameRepo)
                        builder.setMessage(getString(R.string.txt_dialog_message))
                        builder.setPositiveButton("Sim"){ dialog, which ->
                            val url = adapter.html
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(url)
                            startActivity(intent)
                            Log.e("TAG", repo.description)
                        }
                        builder.setNegativeButton("Não"){ dialog, which ->
                            return@setNegativeButton
                        }
                        builder.show()
                    }
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {viewModel.getRepoList(query)}
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextChange")
        return false
    }

    companion object{
        private const val TAG = "TAG"
    }

}