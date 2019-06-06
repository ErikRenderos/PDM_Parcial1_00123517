package com.example.bkb

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bkb.fragments.DetailFragment
import com.example.bkb.fragments.ListFragment
import com.example.bkb.fragments.MatchFragment
import com.example.bkb.fragments.NewFragment
import com.example.bkb.interfaces.FragmentComunication
import com.example.bkb.database.models.MatchViewModel

class MainActivity : AppCompatActivity(), FragmentComunication, ListFragment.OnFragmentInteractionListener,
    DetailFragment.OnFragmentInteractionListener, NewFragment.OnFragmentInteractionListener,
    MatchFragment.OnFragmentInteractionListener {

    private lateinit var viewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)

        if (savedInstanceState == null) {
            var lista = ListFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, lista).commit()
        }
    }

    override fun sendData(pos: Int) {
        viewModel.getAllMatch.observe(this, Observer {
            var datos = Bundle()
            datos.putInt("pos", pos)

            if (!it[pos].isOver) {
                var match = MatchFragment()
                match.arguments = datos
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, match)
                    .addToBackStack("prev")
                    .commit()

            } else {
                var detail = DetailFragment()
                datos.putInt("pos", pos)
                detail.arguments = datos
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, detail)
                    .addToBackStack("prev")
                    .commit()
                Toast.makeText(this, "Error al crear partido. "+it[pos].id, Toast.LENGTH_LONG).show()
            }

            viewModel.getAllMatch.removeObservers(this)
        })
    }

    override fun addMatch() {
        var crear = NewFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, crear).addToBackStack("prev")
            .commit()
    }

    override fun detailMatch() {
        var detail = DetailFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, detail).addToBackStack("prev")
            .commit()
    }

    override fun viewMatch() {
        var lista = ListFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, lista).commit()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
