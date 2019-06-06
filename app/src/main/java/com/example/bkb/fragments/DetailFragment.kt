package com.example.bkb.fragments

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bkb.R
import com.example.bkb.interfaces.FragmentComunication
import com.example.bkb.database.models.MatchViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MatchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MatchFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var viewModel: MatchViewModel

    private lateinit var comunicacion: FragmentComunication
    private lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("pos")
        }

        viewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Activity) {
            activity = context
            if (activity is FragmentComunication) {
                comunicacion = activity as FragmentComunication
            }
        }
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllMatch.observe(this, Observer {
            var match = it[param1!!]
            tv_teamA.text = match.homeTeam
            tv_teamB.text = match.guestTeam
            tv_scoreA.text = match.homeScore.toString()
            tv_scoreB.text = match.guestScore.toString()
        })
    }

    override fun onPause() {
        viewModel.updateMatch(viewModel.currentMatch.value!!)
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MatchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}