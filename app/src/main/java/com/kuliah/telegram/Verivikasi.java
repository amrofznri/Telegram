package com.kuliah.telegram;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Verivikasi extends AppCompatActivity {

    private EditText[] editTexts;
    private TextView countdownTimer;
    private CountDownTimer timer;
    private StringBuilder enteredCode = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verivikasi);

        editTexts = new EditText[]{
                findViewById(R.id.editText1),
                findViewById(R.id.editText2),
                findViewById(R.id.editText3),
                findViewById(R.id.editText4),
                findViewById(R.id.editText5),
                findViewById(R.id.editText6)
        };

        countdownTimer = findViewById(R.id.countdownTimer);

        // Memulai countdown timer selama 2 menit (120000 milidetik)
        startCountdownTimer(120000);

        setEditTextListeners();
    }

    private void startCountdownTimer(long millisInFuture) {
        timer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                countdownTimer.setText(seconds + " detik");
            }

            @Override
            public void onFinish() {
                // Aksi yang ingin diambil setelah waktu mundur selesai
                countdownTimer.setText("Kirim Ulang");
                countdownTimer.setOnClickListener(view -> {
                    // Memulai countdown timer lagi
                    startCountdownTimer(120000);
                    // Reset semua EditText
                    for (EditText editText : editTexts) {
                        editText.setText("");
                    }
                    // Fokus pada EditText pertama
                    editTexts[0].requestFocus();
                });
            }
        }.start();
    }

    private void setEditTextListeners() {
        for (int i = 0; i < editTexts.length; i++) {
            int finalI = i;

            editTexts[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 1 && finalI < editTexts.length - 1) {
                        editTexts[finalI + 1].requestFocus();
                    }
                    checkVerificationCode();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            editTexts[i].setOnKeyListener((view, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL && finalI > 0 && editTexts[finalI].getText().toString().isEmpty()) {
                    // Izinkan penghapusan satu persatu dan beralih ke EditText sebelumnya
                    editTexts[finalI - 1].setText("");
                    editTexts[finalI - 1].requestFocus();
                    return true;
                }
                return false;
            });

            editTexts[i].setOnEditorActionListener((textView, actionId, keyEvent) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkVerificationCode();
                    return true;
                }
                return false;
            });
        }
    }

    private void checkVerificationCode() {
        enteredCode.setLength(0); // Reset enteredCode

        for (EditText editText : editTexts) {
            enteredCode.append(editText.getText().toString());
        }

        // Ganti dengan panjang kode verifikasi yang diinginkan
        int expectedLength = 6;

        if (enteredCode.length() == expectedLength) {
            // Cek hanya jika panjang kode sudah sesuai
            // Ganti dengan kode verifikasi yang diinginkan
            String correctCode = "123456";

            if (enteredCode.toString().equals(correctCode)) {
                // Kode verifikasi benar, pindah ke activity selanjutnya
                if (timer != null) {
                    timer.cancel();
                }

                // Ganti dengan nama Activity selanjutnya
                Intent intent = new Intent(Verivikasi.this, Dashboard.class);
                startActivity(intent);
                finish();
            } else {
                // Kode verifikasi salah
                Toast.makeText(this, "Kode verifikasi salah", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Hentikan countdown timer jika activity dihancurkan
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }
}
