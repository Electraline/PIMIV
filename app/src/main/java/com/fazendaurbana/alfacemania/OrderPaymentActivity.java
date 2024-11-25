package com.fazendaurbana.alfacemania;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class OrderPaymentActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private Button btnConfirmarPagamento;
    private ArrayList<String> selectedProducts;
    private double total;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_paymente);

        radioGroup = findViewById(R.id.radioGroup);
        btnConfirmarPagamento = findViewById(R.id.btn_confirmar_pagamento);
        recyclerView = findViewById(R.id.recycler_view_products);

        // Receber os produtos selecionados e o total do pedido
        Intent intent = getIntent();
        selectedProducts = intent.getStringArrayListExtra("selectedProducts");
        total = intent.getDoubleExtra("total", 0.0);

        // Configurar o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(selectedProducts);
        recyclerView.setAdapter(productAdapter);

        btnConfirmarPagamento.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);

            if (radioButton != null) {
                String paymentMethod = radioButton.getText().toString();

                // Gerar um número de pedido
                String orderNumber = "PEDIDO-" + System.currentTimeMillis();

                // Redirecionar para a tela de finalização com os detalhes do pedido
                Intent finalizationIntent = new Intent(OrderPaymentActivity.this, FinalizationActivity.class);
                finalizationIntent.putStringArrayListExtra("selectedProducts", selectedProducts);
                finalizationIntent.putExtra("total", total);
                finalizationIntent.putExtra("paymentMethod", paymentMethod);
                finalizationIntent.putExtra("orderNumber", orderNumber);
                startActivity(finalizationIntent);
            }
        });
    }
}