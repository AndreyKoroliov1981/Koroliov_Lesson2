package com.example.homework2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.homework2.databinding.ActivityMainBinding
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

const val TAG = "my_tag"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val subject = PublishSubject.create<Int>()
    private var countDebounce = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()

        val debounce = subject.debounce(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "countDebounce = $countDebounce")
                binding.btnDebounce.text = countDebounce.toString()
            }
    }

    private fun setListeners() {
        binding.btnSimpleFlowableJust.setOnClickListener {
            simpleFlowableJust()
        }
        binding.btnObservableJust.setOnClickListener {
            modifyObservableJust()
        }
        binding.btnStartCreateObservable.setOnClickListener {
            startCreateObservable()
        }
        binding.btnIntervalObservable.setOnClickListener {
            intervalObservable()
        }
        binding.btnFromObservable.setOnClickListener {
            fromObservable()
        }
        binding.btnRangeObservable.setOnClickListener {
            rangeObservable()
        }
        binding.btnDebounce.setOnClickListener {
            debounce()
        }
        binding.btnCount100.setOnClickListener {
            count100()
        }
        binding.btnConcatAndMerge.setOnClickListener {
            concatAndMerge()
        }
        binding.btnZip.setOnClickListener {
            zip()
        }
        binding.btnCezar.setOnClickListener {
            cezar(binding.etWord.text.toString())
        }
    }

    private fun cezar(text: String) {
        val charArray = text.toCharArray().toList()
        Observable.fromIterable(charArray)
            .concatMap { t -> getModifiedObservable(t) }
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<Char> {
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onNext(char: Char) {
                    Log.d(TAG,"encode char: $char")
                }
                override fun onError(e: Throwable) {
                }
            })
    }

    private fun getModifiedObservable(char: Char): Observable<Char> {
        val alphabet = "abcdefghijklmnopqrstuvwxyz "
        val key = 3
        return Observable.create { emitter ->
            var result = char
            if (alphabet.contains(char)) {
                val index = alphabet.indexOf(char)
                val newIndex = (index + key) % alphabet.length
                result = alphabet[newIndex]
            }
            emitter.onNext(result )
            emitter.onComplete()
        }
            .subscribeOn(Schedulers.io())
    }

    private fun zip() {
        val observable1 = Observable.just(1000)
        val observable2 = Observable.just(200)
        val zipper = BiFunction<Int, Int, Long> { first, second -> (first / second).toLong() }
        val zip = Observable.zip(observable1, observable2, zipper)
            .subscribe { x -> Log.d(TAG, "zip : $x") }
    }

    private fun concatAndMerge() {
        val observable1 = Observable.range(1, 50)
        val observable2 = Observable.range(51, 50)
        val concat = Observable.concat(observable1, observable2)
            .subscribe { x -> Log.d(TAG, "concat : $x") }
        val merge = Observable.merge(
            observable1.delay(100, TimeUnit.MILLISECONDS),
            observable2.delay(100, TimeUnit.MILLISECONDS)
        ).subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe { x -> Log.d(TAG, "merge : $x") }
    }

    private fun count100() {
        val rangeObservable = Observable.range(1, 100).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        val subscribe1 = rangeObservable.subscribe {
            if (it % 2 == 0) Log.d(TAG, "subscribe even numbers: $it")
        }
        val subscribe2 = rangeObservable
            .takeLast(10)
            .subscribe {
                Log.d(TAG, "subscribe last 10 numbers: $it")
            }
        val subscribe3 = rangeObservable.subscribe {
            if ((it % 3 == 0) && (it % 5 == 0)) Log.d(
                TAG,
                "subscribe  divider 3 and 5 numbers: $it"
            )
        }
    }

    private fun debounce() {
        countDebounce++
        subject.onNext(countDebounce)
    }

    private fun simpleFlowableJust() {
        val flowableJust = Flowable.just("Hello", "world!").subscribe {
            Log.d(TAG, it)
        }
    }

    private fun modifyObservableJust() {
        val myObservable = getObservable()
        val myObserver = getObserver()
        myObservable
            .subscribe(myObserver)
    }

    private fun getObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(s: String) {
                Log.d(TAG, "onNext: $s")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError: " + e.message)
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete")
            }
        }
    }

    private fun getObservable(): Observable<String> {
        return Observable.just("1", "2", "3")
    }

    private fun startCreateObservable() {
        val flowableCreate = Observable.create<Int> { emitter ->
            emitter.onNext(1)
            emitter.onNext(2)
            emitter.onNext(3)
            emitter.onComplete()
        }
            .observeOn(Schedulers.io())
            .map {
                Log.d(TAG, "map plus value = $it")
                it + it
            }
            .doOnNext { Log.d(TAG, "after map plus -> $it") }
            .observeOn(Schedulers.computation())
            .map {
                Log.d(TAG, "map multiple  value = $it")
                it * it
            }
            .doOnNext { Log.d(TAG, "after map multiple -> $it") }
            .observeOn(Schedulers.single())
            .subscribe(
                {
                    Log.d(TAG, "On Next $it")
                },
                {
                    Log.d(TAG, "On Error")
                },
                {
                    Log.d(TAG, "On Complete")
                }
            )
    }

    private fun intervalObservable() {
        val intervalObservable = Observable.interval(1000L, TimeUnit.MILLISECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { Log.d(TAG, "print in timer") }
    }

    private fun fromObservable() {
        val fromObservable = Observable.fromArray(10, 20, 30).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "Emit Integers : $it")
            }
    }

    private fun rangeObservable() {
        val rangeObservable = Observable.range(10, 5).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d(TAG, "Range emit Integers : $it")
            }
    }
}
