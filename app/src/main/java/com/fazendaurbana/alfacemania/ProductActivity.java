package com.fazendaurbana.alfacemania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.function.Supplier;

public class ProductActivity extends AppCompatActivity {

    private int quantityCrespa = 0;
    private int quantityLisa = 0;
    private int quantityFrisada = 0;
    private int quantityMimosa = 0;
    private int quantityRomana = 0;
    private int quantityRoxa = 0;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tvTotal = findViewById(R.id.txt_total);

        // Configura os produtos
        setupProduct(R.id.btn_increment_alface1, R.id.btn_decrement_alface1, R.id.txt_quantidade_alface1, () -> quantityCrespa++, () -> quantityCrespa--, () -> quantityCrespa);
        setupProduct(R.id.btn_increment_alface2, R.id.btn_decrement_alface2, R.id.txt_quantidade_alface2, () -> quantityLisa++, () -> quantityLisa--, () -> quantityLisa);
        setupProduct(R.id.btn_increment_alface3, R.id.btn_decrement_alface3, R.id.txt_quantidade_alface3, () -> quantityFrisada++, () -> quantityFrisada--, () -> quantityFrisada);
        setupProduct(R.id.btn_increment_alface4, R.id.btn_decrement_alface4, R.id.txt_quantidade_alface4, () -> quantityMimosa++, () -> quantityMimosa--, () -> quantityMimosa);
        setupProduct(R.id.btn_increment_alface5, R.id.btn_decrement_alface5, R.id.txt_quantidade_alface5, () -> quantityRomana++, () -> quantityRomana--, () -> quantityRomana);
        setupProduct(R.id.btn_increment_alface6, R.id.btn_decrement_alface6, R.id.txt_quantidade_alface6, () -> quantityRoxa++, () -> quantityRoxa--, () -> quantityRoxa);

        // Calcula o total
        updateTotal();

        // Configura o botão de finalizar pedido
        Button btnFinalizarPedido = findViewById(R.id.btn_finalizar_pedido);
        btnFinalizarPedido.setOnClickListener(v -> {
            // Passa os dados de quantidade para a próxima tela (OrderPaymentActivity)
            Intent intent = new Intent(ProductActivity.this, OrderPaymentActivity.class);
            intent.putExtra("total", calculateTotal());

            // Passa os produtos selecionados
            ArrayList<String> selectedProducts = new ArrayList<>();
            if (quantityCrespa > 0) selectedProducts.add("Alface Crespa: " + quantityCrespa);
            if (quantityLisa > 0) selectedProducts.add("Alface Lisa: " + quantityLisa);
            if (quantityFrisada > 0) selectedProducts.add("Alface Frisada: " + quantityFrisada);
            if (quantityMimosa > 0) selectedProducts.add("Alface Mimosa: " + quantityMimosa);
            if (quantityRomana > 0) selectedProducts.add("Alface Romana: " + quantityRomana);
            if (quantityRoxa > 0) selectedProducts.add("Alface Roxa: " + quantityRoxa);

            intent.putStringArrayListExtra("selectedProducts", selectedProducts);
            startActivity(intent);
        });

        // Configura o botão de voltar para home
        Button btnVoltarHome = findViewById(R.id.btn_voltar_home);
        btnVoltarHome.setOnClickListener(v -> finish());  // Volta para a tela anterior
    }

    private void setupProduct(int incrementBtnId, int decrementBtnId, int quantityTxtId, Runnable increment, Runnable decrement, Supplier<Integer> getQuantity) {
        Button incrementBtn = findViewById(incrementBtnId);
        Button decrementBtn = findViewById(decrementBtnId);
        TextView quantityTxt = findViewById(quantityTxtId);

        incrementBtn.setOnClickListener(v -> {
            increment.run();
            quantityTxt.setText(String.valueOf(getQuantity.get())); // Atualiza o valor
            updateTotal();
        });

        decrementBtn.setOnClickListener(v -> {
            decrement.run();
            if (getQuantity.get() < 0) {
                quantityTxt.setText(String.valueOf(0)); // Para evitar valores negativos
            } else {
                quantityTxt.setText(String.valueOf(getQuantity.get())); // Atualiza o valor
            }
            updateTotal();
        });
    }

    private void updateTotal() {
        double total = calculateTotal();
        tvTotal.setText("Total: R$ " + total);
    }

    private double calculateTotal() {
        // Calcular o total com base nas quantidades e preços dos produtos
        double total = 0;
        total += quantityCrespa * 0.50; // exemplo de preço
        total += quantityLisa * 0.50;
        total += quantityFrisada * 0.50;
        total += quantityMimosa * 0.50;
        total += quantityRomana * 0.50;
        total += quantityRoxa * 0.50;
        return total;
    }
}