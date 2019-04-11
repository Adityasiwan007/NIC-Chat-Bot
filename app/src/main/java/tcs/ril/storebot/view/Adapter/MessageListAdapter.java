package tcs.ril.storebot.view.Adapter;

import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

import tcs.ril.storebot.R;
import tcs.ril.storebot.model.UserMessage;
import static android.support.constraint.Constraints.TAG;
//import static tcs.ril.storebot.view.Adapter.ListButtonAdapter.buttons;
import static tcs.ril.storebot.view.Activity.MainActivity.t1;
import static tcs.ril.storebot.view.Adapter.ListButtonAdapter.isClicked;
import static tcs.ril.storebot.view.Adapter.ListButtonAdapter.pos;

/**
 * This Class is the mAdapter for the recycler view in the MainActivity,All the different kinds of messages such as
 * images, texts and buttons are handled in the mAdapter.
 */
public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private static final int VIEW_TYPE_MESSAGE_SENT_IMAGE = 3;
    private static final int VIEW_TYPE_MESSAGE_RECIEVED_IMAGE = 4;
    private static final int VIEW_TYPE_MESSAGE_BUTTON = 5;
    private static final int VIEW_TYPE_MESSAGE_VIEW = 6;
    String LOGTAG="MessageListAdapter";
    private Activity mContext;
    UserMessage message;
    private List<UserMessage> mMessageList;
    public static HashMap<Integer, Boolean> btnStates=new HashMap<>();

    public MessageListAdapter(Activity context, List<UserMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }
    HideButtonsInterface ci;
    public HideButtonsInterface getCi() {
        return ci;
    }

    public void setCi(HideButtonsInterface ci) {
        this.ci = ci;
    }
    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        UserMessage message = mMessageList.get(position);
        Log.d(TAG, "getItemViewType: "+message.getTag()+" "+message.getImageTag()+
                " "+message.getBtnTag());
        if(message.getViewTag()==1){
            return VIEW_TYPE_MESSAGE_VIEW;}
        else if (message.getBtnTag() == 1 && message.getTag() == 1) {
            return VIEW_TYPE_MESSAGE_BUTTON;
        } else if (message.getTag() == 0) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (message.getTag() == 1) {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        } else if (message.getImageTag() == 0) {
            return VIEW_TYPE_MESSAGE_SENT_IMAGE;
        } else if (message.getImageTag() == 1) {
            return VIEW_TYPE_MESSAGE_RECIEVED_IMAGE;
        }

        return 0;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TYPE_MESSAGE_SENT:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.send_layout, parent, false);

                return new SentMessageHolder(view);
            case VIEW_TYPE_MESSAGE_RECEIVED:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rcv_layout, parent, false);
                return new ReceivedMessageHolder(view,mContext);
            case VIEW_TYPE_MESSAGE_SENT_IMAGE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.send_image_layout, parent, false);
                return new SentImageHolder(view,mContext);
            case VIEW_TYPE_MESSAGE_RECIEVED_IMAGE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rcv_image_layout, parent, false);
                return new ReceivedImageHolder(view,mContext);
            case VIEW_TYPE_MESSAGE_BUTTON:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.rcv_btn_layout, parent, false);
                return new ReceivedBtnHolder(view,mContext);
            case VIEW_TYPE_MESSAGE_VIEW:
                view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.rcv_view_layout,parent, false);
                return new RecievedViewHolder(view,mContext);
        }


        //CALL HIDE

        return null;
    }


     /**
      * @param holder
      * @param position
      * Passes the message object to a ViewHolder so that the contents can be bound to UI.
      */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        message = mMessageList.get(position);
        Log.d(LOGTAG," positon  : " +position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_SENT_IMAGE:
                ((SentImageHolder) holder).bind(message);

                break;
            case VIEW_TYPE_MESSAGE_RECIEVED_IMAGE:
                ((ReceivedImageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_BUTTON:
                ((ReceivedBtnHolder) holder).bind(message);
                if(position==mMessageList.size()-1) {
                    String messageSpeech = message.getMessage() + "\n";
                    for (int i = 0; i < message.getBtnText().size(); i++) {

                        messageSpeech += message.getBtnText().get(i) + "\n";
                    }
                    t1.speak(messageSpeech, TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
            case VIEW_TYPE_MESSAGE_VIEW:
                ((RecievedViewHolder)holder).bind(message);
                break;
        }
    }



}