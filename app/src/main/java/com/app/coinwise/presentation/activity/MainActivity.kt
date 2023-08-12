package com.app.coinwise.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.commit
import com.app.coinwise.R
import com.app.coinwise.presentation.fragment.*
import com.app.coinwise.presentation.viewmodel.GraficoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var img404: ImageView
    private lateinit var img500: ImageView
    private lateinit var img333: ImageView
    private lateinit var button1YearFragment: Button
    private lateinit var button90DaysFragment: Button
    private lateinit var button60DaysFragment: Button
    private lateinit var button30DaysFragment: Button
    private lateinit var button7DaysFragment: Button

    private val viewModel: GraficoViewModel by lazy {
        GraficoViewModel.create(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1YearFragment = findViewById(R.id.button_1year_fragment_test)
        button90DaysFragment = findViewById(R.id.button_90days_fragment_test)
        button60DaysFragment = findViewById(R.id.button_60days_fragment_test)
        button30DaysFragment = findViewById(R.id.button_30days_fragment_test)
        button7DaysFragment = findViewById(R.id.button_7days_fragment_test)
        img404 = findViewById(R.id.img_404)
        img500 = findViewById(R.id.img_500)
        img333 = findViewById(R.id.img_333)

        val graph1YearFragment = Graph1YearFragment.newInstance()
        val graph90DaysFragment = Graph90DaysFragment.newInstance()
        val graph60DaysFragment = Graph60DaysFragment.newInstance()
        val graph30DaysFragment = Graph30DaysFragment.newInstance()
        val graph7DaysFragment = Graph7DaysFragment.newInstance()

        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, graph1YearFragment)
            setReorderingAllowed(true)
        }

        button1YearFragment.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, graph1YearFragment)
                setReorderingAllowed(true)
            }
        }

        button90DaysFragment.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, graph90DaysFragment)
                setReorderingAllowed(true)
            }
        }

        button60DaysFragment.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, graph60DaysFragment)
                setReorderingAllowed(true)
            }
        }

        button30DaysFragment.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, graph30DaysFragment)
                setReorderingAllowed(true)
            }
        }

        button7DaysFragment.setOnClickListener {
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, graph7DaysFragment)
                setReorderingAllowed(true)
            }
        }

        viewModel.errorLiveData.observe(this) { errorMsg ->
            img404.visibility = if (errorMsg == 404) View.VISIBLE else View.GONE
            img500.visibility = if (errorMsg == 500) View.GONE else View.GONE
            img333.visibility = if (errorMsg == 333) View.VISIBLE else View.GONE

            val message = when (errorMsg) {
                404 -> "Página não encontrada"
                500 -> "Erro na conexão: exibindo últimos dados salvos"
                333 -> "A página está vazia"
                else -> "Erro desconhecido"
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

    }
}