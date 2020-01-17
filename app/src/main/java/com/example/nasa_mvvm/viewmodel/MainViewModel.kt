import android.util.Log
import com.example.nasa_mvvm.model.Items
import com.example.nasa_mvvm.model.MainModel
import com.example.nasa_mvvm.viewmodel.SchedulersWrapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import java.util.*

class MainViewModel(mMainModel: MainModel) {

    lateinit var itemObservable: PublishSubject<Items>
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var mainModel: MainModel = mMainModel
    private val schedulersWrapper = SchedulersWrapper()

    init {
        itemObservable= PublishSubject.create()
    }


    fun getUrlFromModel(date:String){

        val disposable:Disposable=mainModel.getDataOfDate(date)!!.subscribeOn(schedulersWrapper.io())
            .observeOn(schedulersWrapper.main()).
                subscribeWith(object : DisposableSingleObserver<Items?>(){

                    override fun onSuccess(t: Items) {

                        Log.d("view model check-",""+t.url)
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }
}
