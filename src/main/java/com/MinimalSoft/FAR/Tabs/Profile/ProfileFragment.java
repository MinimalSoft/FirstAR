package com.MinimalSoft.FAR.Tabs.Profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.MinimalSoft.FAR.ImageUtility;
import com.MinimalSoft.FAR.MainActivity;
import com.MinimalSoft.FAR.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements GraphRequest.GraphJSONObjectCallback, DialogInterface.OnClickListener, View.OnClickListener, Transformation, Callback {
    private TextView nameLabel;
    private TextView emailLabel;
    private ImageView coverPicture;
    private boolean loggedWithFB;
    private CircleImageView profilePicture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_profile, container, false);

        nameLabel = (TextView) inflatedView.findViewById(R.id.profile_nameLabel);
        emailLabel = (TextView) inflatedView.findViewById(R.id.profile_emailLabel);
        coverPicture = (ImageView) inflatedView.findViewById(R.id.cover_imageView);
        profilePicture = (CircleImageView) inflatedView.findViewById(R.id.profile_imageView);
        Button logoutButton = (Button) inflatedView.findViewById(R.id.profile_logoutButton);
        Button updateButton = (Button) inflatedView.findViewById(R.id.profile_upDateButton);
        Button settingsButton = (Button) inflatedView.findViewById(R.id.profile_settingsButton);
        Button disclaimerButton = (Button) inflatedView.findViewById(R.id.profile_disclaimerButton);

        disclaimerButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        return inflatedView;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences settings = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
        emailLabel.setText(settings.getString("USER_EMAIL", "loading..."));
        nameLabel.setText(settings.getString("USER_NAME", "loading..."));
        String facebookID = settings.getString("FACEBOOK_ID", "NA");

        if (!facebookID.equals("NA")) {
            Bundle parameters = new Bundle();
            FacebookSdk.sdkInitialize(getActivity());
            parameters.putString("fields", "picture.type(large),cover");

            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
            loggedWithFB = true;
        } else {
            loggedWithFB = false;
        }
    }

    /*GraphRequest methods*/

    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        if (response.getError() == null) {
            JSONObject json = response.getJSONObject();

            try {
                String coverSource = json.getJSONObject("cover").getString("source");
                String profilePictureURL = json.getJSONObject("picture").getJSONObject("data").getString("url");
                Picasso.with(getContext()).load(Uri.parse(coverSource)).transform(this).into(coverPicture);
                Picasso.with(getContext()).load(Uri.parse(profilePictureURL)).into(profilePicture, this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                setStoredPictures();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*----DialogInterface methods----*/

    @Override
    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences.Editor settingsEditor = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();

        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //emailLabel.setText(getResources().getString(R.string.user_email_hint));
        //nameLabel.setText(getResources().getString(R.string.user_name_hint));

        if (loggedWithFB) {
            settingsEditor.putBoolean("USER_PICS", false);
            settingsEditor.putString("FACEBOOK_ID", "NA");
            FacebookSdk.sdkInitialize(this.getActivity());
            LoginManager.getInstance().logOut();
        }

        settingsEditor.putBoolean("LOGGED_IN", false);
        settingsEditor.apply();
        startActivity(intent);
        getActivity().finish();
    }

    /*----View methods----*/

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.profile_logoutButton:
                AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this.getActivity());
                confirmDialog.setMessage("Cada que alguien nos deja, nuestro  DevTeam llora :'(");
                confirmDialog.setTitle("¿Serguro que deseas salir?");
                confirmDialog.setPositiveButton("Continuar", this);
                confirmDialog.setNegativeButton("Cancelar", null);
                confirmDialog.show();
                break;

            case R.id.profile_settingsButton:

            break;
        }
    }

    /*Picasso methods*/

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap blurredCover = ImageUtility.blur(getContext(), source);
        if (blurredCover != source) {
            try {
                savePicture(blurredCover, "COVER_BITMAP");
            } catch (IOException e) {
                e.printStackTrace();
            }
            source.recycle();
        }
        return blurredCover;
    }

    @Override
    public void onSuccess() {
        try {
            Bitmap bitmap = ((BitmapDrawable) profilePicture.getDrawable()).getBitmap();
            savePicture(bitmap, "PROFILE_BITMAP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError() {

    }

    @Override
    public String key() {
        return "BLUR";
    }

    private void setStoredPictures() throws IOException {
        SharedPreferences settings = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);

        if (settings.getBoolean("USER_PICS", false)) {
            FileInputStream fileInputStream = getContext().openFileInput("PROFILE_BITMAP");
            Bitmap profileBitmap = BitmapFactory.decodeStream(fileInputStream);

            fileInputStream = getContext().openFileInput("COVER_BITMAP");
            Bitmap coverBitmap = BitmapFactory.decodeStream(fileInputStream);

            profilePicture.setImageBitmap(profileBitmap);
            coverPicture.setImageBitmap(coverBitmap);

            fileInputStream.close();
        }
    }

    private void savePicture(Bitmap profileBitmap, String name) throws IOException {
        SharedPreferences.Editor preferencesEditor = getActivity().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE).edit();
        FileOutputStream fileOutputStream = getContext().openFileOutput(name, Context.MODE_PRIVATE);
        profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        preferencesEditor.putBoolean("USER_PICS", true);
        preferencesEditor.apply();
        fileOutputStream.close();
    }
}