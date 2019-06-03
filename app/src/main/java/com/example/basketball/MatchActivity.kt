package com.example.basketball

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.basketball.fragments.ListFragment
import com.example.basketball.fragments.MatchFragment
import com.example.basketball.fragments.NewFragment
import com.example.basketball.interfaces.FragmentComunication
import com.example.basketball.models.MatchViewModel
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity(),
    FragmentComunication,
    ListFragment.OnFragmentInteractionListener,
    NewFragment.OnFragmentInteractionListener,
    MatchFragment.OnFragmentInteractionListener, LifecycleOwner {

    private lateinit var viewModel: MatchViewModel

    override fun sendData(pos: Int) {
        viewModel.getAllMatches.observe(this, Observer {
            if(!it[pos].isOver){

                var partido = MatchFragment()
                var datos = Bundle()
                datos.putInt("pos", pos)
                partido.arguments = datos
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, partido)
                    .addToBackStack("prev")
                    .commit()


            }else{
                Toast.makeText(this, "Este partido ya ha finalizado", Toast.LENGTH_SHORT).show()
            }
            viewModel.getAllMatches.removeObservers(this)
        })

    }

    override fun viewMatches() {
        var lista = ListFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_content, lista)
            .commit()
    }

    override fun addMatch() {
        var crear = NewFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_content, crear)
            .addToBackStack("prev")
            .commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)

        if (savedInstanceState == null) {
            var lista = ListFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_content, lista).commit()
        }

    }
}