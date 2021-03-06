package com.example.gajdadeulim;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MyInfo_Fragment extends Fragment implements View.OnClickListener {

    public Toolbar modifytoolbar;

    EditText ModifyPWText;
    EditText ModifyPWConfirmText;
    TextView ModifyIDText,ModifyNameText,ModifyMajorText,ModifyS_NumText,ModifyGenderText;
    String userID;
    Button ModifyButton;
    public View view;
    public MyInfo_Fragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.myinfo_fragment, container, false);

        modifytoolbar = view.findViewById(R.id.ModifyToolbar);
        modifytoolbar.setTitle(R.string.defaultToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(modifytoolbar);

        ModifyPWText = view.findViewById(R.id.ModifyPWText);
        ModifyPWConfirmText = view.findViewById(R.id.ModifyPWConfirmText);
        ModifyButton = view.findViewById(R.id.ModifyButton);

        ModifyIDText = view.findViewById(R.id.ModifyIDText);
        ModifyNameText = view.findViewById(R.id.ModifyNameText);
        ModifyMajorText = view.findViewById(R.id.ModifyMajorText);
        ModifyS_NumText = view.findViewById(R.id.ModifyS_NumText);
        ModifyGenderText = view.findViewById(R.id.ModifyGenderText);
        ModifyButton.setOnClickListener(this);


        ContentValues values = new ContentValues();
        values.put("AccessInfoByID", MainActivity.userID);

        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("ShowUserInfoServlet"), values);

        try {
            response = networkTask.execute().get();
            JSONObject jsonObject = new JSONObject(response);

            ModifyIDText.setText(jsonObject.getString("userId"));
            ModifyNameText.setText(jsonObject.getString("userName"));
            ModifyS_NumText.setText(jsonObject.getString("userSchoolNumber"));
            ModifyGenderText.setText(jsonObject.getString("userGender"));
            ModifyMajorText.setText(jsonObject.getString("userMajor"));

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ModifyButton : {
                if(!ModifyPWText.getText().toString().equals(ModifyPWConfirmText.getText().toString()) || ModifyPWText.getText().equals("")) {
                    ModifyPWText.setText("");
                    ModifyPWConfirmText.setText("");
                    Toast.makeText(getContext(),"??????????????? ????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ModifyPWText.getText().toString().equals("")){
                        Toast.makeText(getContext(),"??????????????? ??????????????????.",Toast.LENGTH_SHORT).show();;
                        ModifyPWText.requestFocus();
                    }
                    else if(ModifyPWConfirmText.getText().toString().equals("")){
                        Toast.makeText(getContext(),"????????????????????? ??????????????????.",Toast.LENGTH_SHORT).show();;
                        ModifyPWConfirmText.requestFocus();
                    }
                    else{
                        ContentValues values = new ContentValues();
                        values.put("IdentificationID", MainActivity.userID);
                        values.put("AfterPass", SignatureUtil.applySHA256(ModifyPWText.getText().toString()));
                        NetworkTask networkTask = new NetworkTask(resulturl("ChangePWServlet"), values);
                        networkTask.execute();
                        Intent intent = new Intent(this.getActivity() , LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(getContext(),"????????? ??????????????? ?????? ????????? ????????????",Toast.LENGTH_SHORT).show();}
                }
            }
            default:{
                break;
            }

        }
    }
    public String resulturl(String url) { //ip ??? ???????????? ?????? ??????????????? ?????? ?????????
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
