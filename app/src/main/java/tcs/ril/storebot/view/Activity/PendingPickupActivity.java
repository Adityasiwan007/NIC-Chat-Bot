package tcs.ril.storebot.view.Activity;import android.support.v7.app.AppCompatActivity;import android.os.Bundle;import android.support.v7.widget.LinearLayoutManager;import android.support.v7.widget.RecyclerView;import android.view.View;import android.widget.ImageView;import android.widget.LinearLayout;import android.widget.TextView;import java.util.ArrayList;import java.util.List;import tcs.ril.storebot.R;import tcs.ril.storebot.model.OrderHistory;import tcs.ril.storebot.view.Adapter.PendingProductAdapater;import static tcs.ril.storebot.view.Adapter.ViewListButtonAdapter.pendingPickup;public class PendingPickupActivity extends AppCompatActivity {    TextView customerName;    TextView orderId;    LinearLayout btnBack;    RecyclerView productRV;    RecyclerView.Adapter prodcutRVAdapter;    RecyclerView.LayoutManager prductLayoutManager;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_pending_pickup);        btnBack=findViewById(R.id.back_btn);        btnBack.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                finish();            }        });        customerName=findViewById(R.id.customer_name_text);        orderId=findViewById(R.id.order_id_text);        productRV=findViewById(R.id.product_pickup_rv);        String tid=getIntent().getExtras().getString("tid");        customerName.setText(tid);        orderId.setText(pendingPickup.get(tid).get(0).getTranscationID());        List<String> productNames=new ArrayList<>();        List<OrderHistory> tempOrderHistory=pendingPickup.get(tid);        for(OrderHistory h:tempOrderHistory){            productNames.add(h.getProduct().getName()+"  "+h.getProduct().getVarient().getWeight().getValue()+                    h.getProduct().getVarient().getWeight().getUnit());        }        prodcutRVAdapter=new PendingProductAdapater(productNames,this);        prductLayoutManager=new LinearLayoutManager(this);        productRV.setLayoutManager(prductLayoutManager);        productRV.setAdapter(prodcutRVAdapter);    }}