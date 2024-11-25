package com.fazendaurbana.alfacemania;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/Usuarios/{id}")
    Call<Usuario> getUsuario(@Path("id") int id);

    @POST("api/Usuarios")
    Call<Usuario> createUsuario(@Body Usuario usuario);

    okhttp3.Call getUsuarioByLogin(String login);

    Call<OrderPaymentActivity> createPagamento(OrderPaymentActivity pagamento);

    Call<OrderPaymentActivity> createOrder(OrderPaymentActivity order);

    // Outros métodos conforme necessário...
}
