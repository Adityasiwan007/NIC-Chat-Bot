package tcs.ril.storebot.view.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import tcs.ril.storebot.R;
import tcs.ril.storebot.model.PreferenceManager;


public class IPUpdateFragment extends DialogFragment {
    EditText ipEditText;
    String ip;
    View view;
    PreferenceManager preferenceManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.popup_settings_login, null);
        ipEditText = view.findViewById(R.id.ip_address);

        preferenceManager = new PreferenceManager(getActivity());
        ipEditText.setText(preferenceManager.getIp(PreferenceManager.IP));
        builder.setView(view)
                .setPositiveButton(R.string.ipupload, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ip = ipEditText.getText().toString();
                        preferenceManager.setIp(PreferenceManager.IP, ip);
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
