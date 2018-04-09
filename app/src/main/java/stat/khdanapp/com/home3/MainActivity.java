package stat.khdanapp.com.home3;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends MvpAppCompatActivity implements MainViewInterface {

    private static final int PERMISSION_REQUEST_CODE = 123;
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    Adapter adapter;

    @InjectPresenter
    MainPresenter presenter;

//    @ProvidePresenter
//    public MainPresenter provideMainPresenter()
//    {
//        String string = "hello";
//        return new MainPresenter(string);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new Adapter(presenter.getListFiles());
        recyclerView.setAdapter(adapter);
        if (checkPermissionMyApp()) presenter.showExtDirFiles();
        else requestMultiplePermissions();
    }

    @Override
    public void updateList() {
        adapter.notifyDataSetChanged();
    }


    public boolean checkPermissionMyApp() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) return true;

        return false;
    }

    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.showExtDirFiles();
            }
//            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                showUnreadSmsCount();
//            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void showToast(String msg) {
        Toast toast = Toast.makeText(getApplicationContext(),
                msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    Dialog dialogFragment;

    @Override
    public void showCustomDialog() {

        //this.dialogFragment = new DialogFragment();
        //this.dialogFragment.show(getSupportFragmentManager(),"cool");

        dialogFragmentObserbale()
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //Log.d("SUCCESS RETURN", "URAAAAA");
                        presenter.getD2().dispose();
                        showToast("Canceled request");
                        closeDialog();
                    }
                });

    }

    @Override
    public void closeDialog() {
        dialogFragment.dismiss();
    }


    public Observable dialogFragmentObserbale() {

        return Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(final ObservableEmitter e) throws Exception {

                android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setTitle("Конвертация картинки");
                builder.setView(inflater.inflate(R.layout.dialog, null))
                        // Add action buttons
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("CANCEL", "Button canceled");
                                e.onComplete();
                            }
                        });
                dialogFragment = builder.create();
                dialogFragment.show();
            }
        });

    }
}
