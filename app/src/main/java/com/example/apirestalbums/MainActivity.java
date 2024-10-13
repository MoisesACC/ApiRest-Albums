package com.example.apirestalbums;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

import entity.Album;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.AlbumService;

public class MainActivity extends AppCompatActivity {

    private Spinner idSpinner;
    private Button consultButton;
    private TextView titleTextView;
    private AlbumService albumService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idSpinner = findViewById(R.id.id_spinner);
        consultButton = findViewById(R.id.consult_button);
        titleTextView = findViewById(R.id.title_textview);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        albumService = retrofit.create(AlbumService.class);

        loadAlbumIds();

        consultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = Integer.parseInt(idSpinner.getSelectedItem().toString());
                fetchAlbumTitle(selectedId);
            }
        });
    }

    private void loadAlbumIds() {
        albumService.getAlbums().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    List<Album> albums = response.body();
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item);
                    for (Album album : albums) {
                        adapter.add(album.getId());
                    }
                    idSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void fetchAlbumTitle(int id) {
        albumService.getAlbums().enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    List<Album> albums = response.body();
                    for (Album album : albums) {
                        if (album.getId() == id) {
                            titleTextView.setText(album.getTitle());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}