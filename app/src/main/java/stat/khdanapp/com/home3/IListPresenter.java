package stat.khdanapp.com.home3;

import io.reactivex.functions.Consumer;

public interface IListPresenter extends Consumer<String>
{
    int pos = -1;
    void bindView(IListRowView view);
    int getViewCount();

    @Override
    void accept(String s) throws Exception;
}

