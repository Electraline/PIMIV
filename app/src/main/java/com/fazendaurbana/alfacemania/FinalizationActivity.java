    package com.fazendaurbana.alfacemania;

    import android.os.Bundle;
    import android.widget.TextView;
    import androidx.appcompat.app.AppCompatActivity;
    import java.util.ArrayList;

    public class FinalizationActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_finalization);

            // Receber os detalhes do pedido
            String paymentMethod = getIntent().getStringExtra("paymentMethod");
            String orderNumber = getIntent().getStringExtra("orderNumber");
            double total = getIntent().getDoubleExtra("total", 0.0);
            ArrayList<String> selectedProducts = getIntent().getStringArrayListExtra("selectedProducts");

            // Exibir os detalhes do pedido
            TextView orderNumberText = findViewById(R.id.orderNumberText);
            TextView paymentMethodText = findViewById(R.id.paymentMethodText);
            TextView totalText = findViewById(R.id.totalText);
            TextView productsText = findViewById(R.id.productsText);

            orderNumberText.setText("Número do Pedido: " + orderNumber);
            paymentMethodText.setText("Método de Pagamento: " + paymentMethod);
            totalText.setText("Total: R$ " + total);

            StringBuilder productsBuilder = new StringBuilder("Produtos Selecionados:\n");
            for (String product : selectedProducts) {
                productsBuilder.append(product).append("\n");
            }
            productsText.setText(productsBuilder.toString());
        }
    }