package com.example.houserent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.houserent.R;
import com.example.houserent.data.UserData;
import com.example.houserent.firebaseRepo.FireBaseRepo;
import com.example.houserent.firebaseRepo.ServerResponse;
import com.example.houserent.utils.SessionData;
import com.squareup.picasso.Picasso;

import static com.example.houserent.utils.Toasts.show;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private EditText etPhoneNumber, etEmail, etFullName;
    private Button btnUpdate;
    private ImageView profileImage;
    private RadioButton rbFemale, rbMale;
    private String gender = null;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etEmail = findViewById(R.id.et_email);
        etFullName = findViewById(R.id.et_full_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        profileImage = findViewById(R.id.image_view);
        rbFemale = findViewById(R.id.rb_female);
        rbMale = findViewById(R.id.rb_male);
        btnUpdate = findViewById(R.id.btn_update);

        setData();

        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
        });

        btnUpdate.setOnClickListener(v -> {
            if (!isValidate()) {
                return;
            }
            String email = etEmail.getText().toString().trim();
            String fullName = etFullName.getText().toString().trim();
            String phoneNumber = etPhoneNumber.getText().toString().trim();

            FireBaseRepo.I.uploadFile(SessionData.getInstance().getLocalData().getName() + ".jpg", imageUri, new ServerResponse<String>() {
                @Override
                public void onSuccess(String body) {
                    UserData userData = new UserData();
                    userData.setEmail(email);
                    userData.setName(fullName);
                    userData.setPhoneNumber(phoneNumber);
                    userData.setGender(gender);
                    userData.setUserImage(body);
                    userData.setPassword(SessionData.getInstance().getLocalData().getPassword());

                    SessionData.getInstance().saveLocalData(userData);
                    FireBaseRepo.I.setProfile(userData, new ServerResponse<String>() {
                        @Override
                        public void onSuccess(String body) {
                            show.longMsg(ProfileActivity.this, body);
                        }

                        @Override
                        public void onFailure(Throwable error) {

                        }
                    });

                }

                @Override
                public void onFailure(Throwable error) {

                }
            });

        });
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            etEmail.setError("Please fill this field");
        }

        if (etFullName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etFullName.setError("Please fill this field");
        }
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            isValid = false;
            etPhoneNumber.setError("Please fill this field");
        }
        if (rbMale.isChecked()) {
            gender = "Male";
        } else if (rbFemale.isChecked()) {
            gender = "Female";
        } else {
            show.longMsg(ProfileActivity.this, "Please Select gender");
            isValid = false;
        }
        return isValid;
    }

    private void setData() {
        etEmail.setText(SessionData.getInstance().getLocalData().getEmail());
        etFullName.setText(SessionData.getInstance().getLocalData().getName());
        etPhoneNumber.setText(SessionData.getInstance().getLocalData().getPhoneNumber());
        if (SessionData.getInstance().getLocalData().getUserImage() != null)
            Picasso.get().load(SessionData.getInstance().getLocalData().getUserImage()).into(profileImage);
    }
}
