package tcs.ril.storebot.view.Adapter;import android.app.Activity;import android.content.Context;import android.os.Vibrator;import android.speech.tts.TextToSpeech;import android.support.annotation.NonNull;import android.support.v7.widget.RecyclerView;import android.util.Log;import android.view.LayoutInflater;import android.view.View;import android.view.ViewGroup;import android.widget.LinearLayout;import android.widget.TextView;import java.util.List;import java.util.Locale;import tcs.ril.storebot.R;import tcs.ril.storebot.view.Activity.MainActivity;import static tcs.ril.storebot.view.Activity.MainActivity.t1;public class ListButtonAdapter extends RecyclerView.Adapter<ListButtonAdapter.ListButtonViewHolder> {    public static List<String> buttons;    private List<String> values;    private Activity mContext;    public static boolean isClicked;    public static int pos;    String TAG="ListButtonAdapter : ";    View view;    HideButtonsInterface ci;    public ListButtonAdapter(Activity mContext, List<String> buttons, List<String> values) {        this.buttons = buttons;        this.values = values;        this.mContext=mContext;    }    public HideButtonsInterface getCi() {        return ci;    }    public void setCi(HideButtonsInterface ci) {        this.ci = ci;    }    @NonNull    @Override    public ListButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {        view = LayoutInflater.from(mContext)                .inflate(R.layout.rcv_btn_item, parent, false);        return new ListButtonViewHolder(view);    }    @Override    public void onBindViewHolder(final ListButtonViewHolder holder, final int position) {        int rem=position%4;        Log.d(TAG, "onClick: size"+buttons.size());        switch(rem){            case 0:                holder.clickLayout.setBackgroundResource(R.drawable.rcv_btn_orange_bg);                break;            case 1:                holder.clickLayout.setBackgroundResource(R.drawable.rcv_btn_violet_bg);                break;            case 2:                holder.clickLayout.setBackgroundResource(R.drawable.rcv_btn_red_bg);                break;            case 3:                holder.clickLayout.setBackgroundResource(R.drawable.rcv_btn_green_bg);                break;        }        holder.btnText.setText(buttons.get(position));            Log.d(TAG, "ListButtonAdapter: "+buttons.get(position));        for(int i=0;i<buttons.size();i++)        {            Log.d(TAG, ""+i +" "+buttons.get(i));        }        holder.clickLayout.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Log.d(TAG, "onBindViewHolder: on Click: "+position);                Log.d(TAG, "onClick: "+buttons.size());                String value=buttons.get(position);                Log.d(TAG, "onClick: "+value);                ((MainActivity)mContext).send1(value,values.get(position));                // buttons.clear();                //ci.Hide();                //ci.Show();                t1.stop();                t1.shutdown();                t1=new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {                    @Override                    public void onInit(int status) {                        if(status != TextToSpeech.ERROR) {                            t1.setLanguage(Locale.ENGLISH);                        }                    }                });                Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);                vibe.vibrate(10);               // pos=holder.getAdapterPosition();            }        });    }    @Override    public int getItemCount() {        return buttons.size();    }    public class ListButtonViewHolder extends RecyclerView.ViewHolder{        public LinearLayout clickLayout;        TextView btnText;        public ListButtonViewHolder(@NonNull View itemView) {            super(itemView);            btnText = itemView.findViewById(R.id.resp_btn_text);            clickLayout=itemView.findViewById(R.id.resp_btn_text_layout);        }    }}