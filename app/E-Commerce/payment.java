import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.view.PaymentSheet;
import com.stripe.android.view.PaymentSheetResult;
import com.stripe.android.view.PaymentSheetResultCallback;
import com.ispc.gymapp.R;

public class PaymentMethod extends AppCompatActivity {

    private PaymentSheet paymentSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PaymentConfiguration.init(getApplicationContext(), "tu_clave_secreta_de_stripe");

        // Configura el botón de pago
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inicia el proceso de pago
                startPaymentFlow();
            }
        });

        // Configura el PaymentSheet
        paymentSheet = new PaymentSheet(this, new PaymentSheetResultCallback() {
            @Override
            public void onPaymentSheetResult(@NonNull PaymentSheetResult paymentSheetResult) {
                // Maneja el resultado del pago aquí
            }
        });
    }

    private void startPaymentFlow() {
        PaymentSessionConfig paymentSessionConfig = new PaymentSessionConfig.Builder()
                .setShippingInfoRequired(false)
                .setShippingMethodsRequired(false)
                .build();

        PaymentSession paymentSession = new PaymentSession(
                this,
                paymentSessionConfig
        );

        paymentSheet.presentWithPaymentSession(
                paymentSession,
                new PaymentSheet.Configuration.Builder()
                        .setGooglePay(R.drawable.google_pay)
                        .setStyle(
                                new PaymentSheet.Style.Builder()
                                        .setStatusBarColor(Color.TRANSPARENT)
                                        .setStatusBarDarkIcon(false)
                                        .setStatusBarLightIcon(true)
                                        .build()
                        )
                        .build()
        );
    }
}
