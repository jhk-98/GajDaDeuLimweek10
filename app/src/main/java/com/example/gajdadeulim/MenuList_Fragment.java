package com.example.gajdadeulim;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MenuList_Fragment extends Fragment {

    public View view;
    public Toolbar mainToolbar;
    public ArrayList<Board_Module> boardsArray = new ArrayList<Board_Module>();

    public Board_Module boards;
    public String[] JsonList = new String[50];

    private ArrayList<MainData> dataList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menulist_fragment, container, false);
        setHasOptionsMenu(true);

        mainToolbar = view.findViewById(R.id.mainToolbar);
        mainToolbar.setTitle(R.string.mainToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mainToolbar);

        recyclerView = view.findViewById(R.id.rv_menulist);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        dataList = new ArrayList<>();
        mainAdapter = new MainAdapter(dataList);
        recyclerView.setAdapter(mainAdapter);
        getBoard();
        for(int i=dataList.size()-1;i>=0;i--){
            if(!dataList.get(i).getErrandProgress().equals("@@Completed")){
                dataList.remove(i);
            }
        }
        mainAdapter.notifyDataSetChanged();

        return view;
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menulist_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.waiting: {
                dataList.clear();
                getBoard();
                for(int i=dataList.size()-1;i>=0;i--){
                    if(!dataList.get(i).getErrandProgress().equals("@@Waiting")){
                        dataList.remove(i);//
                    }
                }
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "???????????? ??????", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.ongoing: {
                dataList.clear();
                getBoard();
                for(int i=dataList.size()-1;i>=0;i--){
                    if(!dataList.get(i).getErrandProgress().equals("@@Ongoing")){
                        dataList.remove(i);
                    }
                }
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "???????????? ??????", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.completed: {
                dataList.clear();
                getBoard();
                for(int i=dataList.size()-1;i>=0;i--){
                    if(!dataList.get(i).getErrandProgress().equals("@@Completed")){
                        dataList.remove(i);
                    }
                }
                mainAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "????????? ??????", Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.logout: {
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.Systheme: {
                SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("systheme", MODE_PRIVATE);    // test ????????? ???????????? ??????
                final SharedPreferences.Editor editor = sharedPreferences.edit(); //sharedPreferences??? ????????? editor??? ??????

                final int[] selectedItem = {0};
                final String[] items = new String[]{"????????????", "???????????????", "????????? ?????????"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(this.getActivity());
                dialog.setTitle("????????????")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem[0] = which;
                            }
                        })
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (selectedItem[0] == 0) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                                    Toast.makeText(getContext(), "??????????????? ?????????", Toast.LENGTH_LONG).show();
                                    editor.putString("theme", "dark");
                                    editor.commit();
                                } else if (selectedItem[0] == 1) {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                                    Toast.makeText(getContext(), "???????????? ?????????", Toast.LENGTH_LONG).show();
                                    editor.putString("theme", "light");
                                    editor.commit();
                                } else {
                                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                                    Toast.makeText(getContext(), "?????????????????? ?????????", Toast.LENGTH_LONG).show();
                                    editor.putString("theme", "system");
                                    editor.commit();
                                }
                            }
                        })
                        .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.create();
                dialog.show();
                return true;

            }
            default: {
                return true;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
    }

    public void getBoard(){
        //boardsArray ??? JsonList ?????????
        boardsArray.clear();
        for(int i =0;i<JsonList.length;i++){
            JsonList[i] = null;
        }

        //???????????? ????????? ???????????????
        ContentValues values = new ContentValues();
        values.put("UserOwnOrders",MainActivity.userID);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("LoadOwnOrdersServlet"), values);
        try {
            response = networkTask.execute().get();
            Log.d("",response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //????????? ????????? ??? ??????
        try{
            response=response.substring(1,response.length()-1);

            JsonList = response.split("\\},", 50);
            for(int i =0;i<JsonList.length-1;i++){
                JsonList[i] += "}";
                Log.d("?????????? : ",JsonList[i]);
            }

            for(int i =0;i<JsonList.length;i++){
                boards = new Board_Module();
                JSONObject jsonObject = new JSONObject(JsonList[i]);
                boards.setO_number(jsonObject.getInt("o_number"));
                boards.setOrders(jsonObject.getString("orders"));
                boards.setTitle(jsonObject.getString("title"));
                boards.setText(jsonObject.getString("text"));
                boards.setLatitude(jsonObject.getDouble("latitude"));
                boards.setLongitude(jsonObject.getDouble("longitude"));
                boards.setDetail_address(jsonObject.getString("detail_address"));
                boards.setPrice(jsonObject.getInt("price"));
                boards.setO_time(jsonObject.getString("o_time").substring(8));
                boards.setProgress(jsonObject.getString("progress"));
                boardsArray.add(boards);
            }

            for(int i=0; i<boardsArray.size();i++){
                MainData mainData = new MainData(R.drawable.human,boardsArray.get(i).getOrders(),boardsArray.get(i).getO_time(),boardsArray.get(i).getText(),String.valueOf(boardsArray.get(i).getPrice()),boardsArray.get(i).getProgress(),boardsArray.get(i).getTitle(),boardsArray.get(i).getO_number());
                dataList.add(mainData);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String resulturl(String url) { //ip ??? ???????????? ??????
        String resultUrl = "http://10.0.2.2:8080/" + url;
        return resultUrl;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String url;
        ContentValues values;

        NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar??? ???????????? ????????? ??????
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result; // ????????? ????????? ????????????. ?????? onPostExecute()??? ??????????????? ???????????????.
        }

        @Override
        protected void onPostExecute(String result) {
            // ????????? ???????????? ???????????????.
            // ????????? ?????? UI ?????? ?????? ????????? ?????????.
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}
