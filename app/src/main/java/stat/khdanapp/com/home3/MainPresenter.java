package stat.khdanapp.com.home3;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainViewInterface> {

    private static final String TAG = "MainPresenter";

    DataModel dataModel = new DataModel();

    class ListPresenter implements IListPresenter
    {
        List<String> strings = new ArrayList<>();
        @Override
        public void bindView(IListRowView view)
        {
            view.setText(strings.get(view.getPos()));
        }

        @Override
        public int getViewCount()
        {
            return strings.size();
        }

        @Override
        public void accept(String s) throws Exception {
            Log.d(TAG,s);
            showCustomDialog();
            convertImage(s);

        }
    }

    ListPresenter listPrestenter = new ListPresenter();

    @Override
    protected void onFirstViewAttach()
    {
        super.onFirstViewAttach();
        getViewState().init();
        List<String>  bufList = new ArrayList<>();
        listPrestenter.strings.addAll(bufList);
        getViewState().updateList();

    }

    public void showExtDirFiles() {

        dataModel.listOfPicturesNames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        listPrestenter.strings.clear();
                        listPrestenter.strings.addAll(strings);
                        getViewState().updateList();
                    }
                });
    }

    Disposable d2;

    public Disposable getD2() {
        return d2;
    }

    public void convertImage(String path){
         d2 = (Disposable) dataModel.converterJpgToPng(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver() {
                    @Override
                    public void onNext(Object o) {

                    }
                    @Override
                    public void onError(Throwable e) {
                        getViewState().closeDialog();
                        getViewState().showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        showExtDirFiles();
                        getViewState().closeDialog();
                        getViewState().showToast("Convertation success");
                    }
                });
    }

    public void showCustomDialog(){

        getViewState().showCustomDialog();

    }

    public ListPresenter getListFiles() {
        return listPrestenter;
    }



}
