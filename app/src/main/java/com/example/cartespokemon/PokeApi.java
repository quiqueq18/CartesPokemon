package com.example.cartespokemon;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PokeApi {
    String getPokemon(){
        String url="https://pokeapi.co/api/v2/pokemon/";

        try{
            String result =HttpUtils.get(url);
            Log.d("debug",url);
            JSONObject jsonResult=new JSONObject(result);
            JSONArray results=jsonResult.getJSONArray("results");

            ArrayList <Pokemon> pokemons=new ArrayList<Pokemon>();
            for (int i=0;i<results.length();i++){
                JSONObject pokemonJson=results.getJSONObject(i);
                Pokemon pokemon =new Pokemon();

                pokemon.setName(pokemonJson.getString("name"));
                pokemon.setDetailsUrl(pokemonJson.getString("url"));
                String resultDetails=HttpUtils.get(pokemon.getDetailsUrl());
                JSONObject jsonDetails=new JSONObject(resultDetails);
                JSONObject sprites=jsonDetails.getJSONObject("sprites");
                String spritesDefault=sprites.getString("from_default");

                pokemon.setWeight(jsonDetails.getInt("height"));
                pokemon.setImage(spritesDefault);


            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return url;
    }
}
