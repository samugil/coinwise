package com.app.coinwise.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.commit
import com.app.coinwise.R
import com.app.coinwise.presentation.fragment.Graph1YearFragment
import com.app.coinwise.presentation.fragment.Graph30DaysFragment
import com.app.coinwise.presentation.fragment.Graph60DaysFragment
import com.app.coinwise.presentation.fragment.Graph7DaysFragment
import com.app.coinwise.presentation.fragment.Graph90DaysFragment
import com.app.coinwise.presentation.viewmodel.GraficoViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var button1YearFragment: RadioButton
    private lateinit var button90DaysFragment: RadioButton
    private lateinit var button60DaysFragment: RadioButton
    private lateinit var button30DaysFragment: RadioButton
    private lateinit var button7DaysFragment: RadioButton

    // instanciando o ViewModel
    private val viewModel: GraficoViewModel by lazy {
        GraficoViewModel.create(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1YearFragment = findViewById(R.id.button_1year_fragment)
        button90DaysFragment = findViewById(R.id.button_90days_fragment)
        button60DaysFragment = findViewById(R.id.button_60days_fragment)
        button30DaysFragment = findViewById(R.id.button_30days_fragment)
        button7DaysFragment = findViewById(R.id.button_7days_fragment)


        val graph1YearFragment = Graph1YearFragment.newInstance()
        val graph90DaysFragment = Graph90DaysFragment.newInstance()
        val graph60DaysFragment = Graph60DaysFragment.newInstance()
        val graph30DaysFragment = Graph30DaysFragment.newInstance()
        val graph7DaysFragment = Graph7DaysFragment.newInstance()

        // força seleção do fragment 1Y no radio button.
        button1YearFragment.isChecked = true
        button1YearFragment.jumpDrawablesToCurrentState()


        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, graph1YearFragment)
            setReorderingAllowed(true)
        }

        //Validando qual Radio Button esta selecionado para apresentar o fragment correspondente.
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

        //Validação de erro e estado de vazio.
        viewModel.errorLiveData.observe(this) { errorMsg ->
            val message = when (errorMsg) {
                404 -> "Pagina não encontrada, servidor fora do ar"
                500 -> "Verifique se sua conexão com a internet esta funcionando, dados apresentados localmente."
                333 -> "API sem dados para retornar."
                else -> "Erro Desconhecido."
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}