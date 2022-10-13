package com.example.cartespokemon;

import android.content.SharedPreferences;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cartespokemon.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ArrayList<Pokemon> items;
    private ArrayAdapter<Pokemon> adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        items=new ArrayList<Pokemon>();

        PokeApi api=new PokeApi();
        adapter = new ArrayAdapter<Pokemon>(
                getContext(),
                R.layout.lv_pokemon_row,
                R.id.lvPokemon,
                items
        );
        binding.lvPokemon.setAdapter(adapter);
        refresh();



        //api.getPokemon();
        super.onViewCreated(view,savedInstanceState);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }


    public void refresh(){
        Toast.makeText(getContext(),"Refrescando...",Toast.LENGTH_LONG).show();
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            PokeApi api = new PokeApi();
            String result = api.getPokemon();

            handler.post(() -> {
                adapter.clear();
                for (Pokemon poke : items) {
                    adapter.add(poke);
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}