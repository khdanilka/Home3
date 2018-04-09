package stat.khdanapp.com.home3;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
interface MainViewInterface extends MvpView {

    void init();
    void updateList();
    void showToast(String e);
    void showCustomDialog();

    void closeDialog();
}
