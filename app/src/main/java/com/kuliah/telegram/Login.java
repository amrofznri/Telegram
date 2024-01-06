package com.kuliah.telegram;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    private CountryCodePicker countryPicker;
    private TextInputLayout nohpLayout;
    private TextInputEditText nohpEditText;
    private TextInputEditText nomerhpEditText;
    private FloatingActionButton fab;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        countryPicker = findViewById(R.id.countryPicker);
        nohpLayout = findViewById(R.id.nohp);
        nohpEditText = findViewById(R.id.kodenomor);
        nomerhpEditText = findViewById(R.id.nomerhp);
        fab = findViewById(R.id.tombollogin);

        // Set initial country code for Indonesia
        countryPicker.setCountryForPhoneCode(62);
        nohpEditText.setText("+62");

        nohpEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                nohpLayout.setError(null); // Hilangkan pesan kesalahan setelah pengguna mulai mengetik
            }
        });

        countryPicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                // Tambahkan tanda "+" di depan kode negara yang dipilih
                String selectedCountryCode = "+" + countryPicker.getSelectedCountryCode();
                nohpEditText.setText(selectedCountryCode);
            }
        });

        nohpLayout.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCountryCode = countryPicker.getSelectedCountryCode();
                String phoneNumber = nohpEditText.getText().toString();
                String additionalPhoneNumber = nomerhpEditText.getText().toString();

                if (isPhoneNumberValid(additionalPhoneNumber)) {
                    showConfirmationDialog(selectedCountryCode, phoneNumber, additionalPhoneNumber);
                } else {
                    // Nomor telepon tidak valid, jalankan aksi getar dan tampilkan pesan kesalahan
                    vibrate();
                    nohpLayout.setError("Masukkan nomor telepon yang valid");
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCountryCode = countryPicker.getSelectedCountryCode();
                String phoneNumber = nohpEditText.getText().toString();
                String additionalPhoneNumber = nomerhpEditText.getText().toString();

                if (isPhoneNumberValid(additionalPhoneNumber)) {
                    showConfirmationDialog(selectedCountryCode, phoneNumber, additionalPhoneNumber);
                } else {
                    // Nomor telepon tidak valid, jalankan aksi getar dan tampilkan pesan kesalahan
                    vibrate();
                    nohpLayout.setError("Masukkan nomor telepon yang valid");
                }
            }
        });
    }

    private void showConfirmationDialog(String country, String phoneNumber, String additionalPhoneNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Konfirmasi Nomor");
        builder.setMessage("Apakah nomor Anda sudah benar?\n\n" + "+" + country + "  " + " " + additionalPhoneNumber);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Menggunakan Intent untuk beralih ke halaman berikutnya
                Intent intent = new Intent(Login.this, Verivikasi.class);
                // Anda dapat mengirim data tambahan melalui Intent jika diperlukan
                intent.putExtra("countryCode", country);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("additionalPhoneNumber", additionalPhoneNumber);

                startActivity(intent);

            }
        });
        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Deprecated in API 26
                vibrator.vibrate(200);
            }
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        int phoneNumberLength = phoneNumber.length();
        return phoneNumberLength >= 10 && phoneNumberLength <= 13;
    }
}
