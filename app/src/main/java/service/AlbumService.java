package service;

import java.util.List;

import entity.Album;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {
    @GET("albums")
    Call<List<Album>> getAlbums();
}
