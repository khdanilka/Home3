package stat.khdanapp.com.home3;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DialogFragment extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Конвертация картинки");
        builder.setView(inflater.inflate(R.layout.dialog, null))
                // Add action buttons
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("CANCEL","Button canceled");
                    }
                });
        return builder.create();
    }

    public Observable dialogFragmentObserbale(){

        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {

                android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                builder.setTitle("Конвертация картинки");
                builder.setView(inflater.inflate(R.layout.dialog, null))
                        // Add action buttons
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("CANCEL","Button canceled");
                                e.onComplete();
                            }
                        });
                builder.create().show();
            }
        });


    }



}
