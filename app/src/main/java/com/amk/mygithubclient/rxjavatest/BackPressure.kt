package ru.geekbrains.githubclient.rxjavatest

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class BackPressure {
    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        //fun createJust() = Observable.just("1", "2", "3", "3")
        //fun createJust2() = Observable.just("4", "5", "6")

        fun noBackPressure() = Observable.range(0, 10000000).subscribeOn(Schedulers.io())
        fun backPressure() =
            Flowable.range(0, 10000000).subscribeOn(Schedulers.io()).onBackpressureLatest()

        fun observable1() = Observable.just("1")
        fun observable2() = Observable.just("2")
    }

    class Consumer(val producer: Producer) {
        fun consumeNoBackPressure() {
            producer.noBackPressure().observeOn(Schedulers.computation())
                .subscribe({
                    Thread.sleep(1000)
                    println(it.toString())
                }, {
                    println("onError: ${it.message}")
                })

        }

        fun consumeBackPressure() {
            producer.backPressure()
                .observeOn(Schedulers.computation(), false, 1)
                .subscribe({
                    Thread.sleep(1000)
                    println(it.toString())
                }, {
                    println("onError: ${it.message}")
                })
        }

        fun execComposite() {
            val compositeDisposable = CompositeDisposable()

            val disposable1 = producer.observable1().subscribe {
                println(it)
            }

            val disposable2 = producer.observable1().subscribe {
                println(it)
            }

            compositeDisposable.addAll(disposable1)
            compositeDisposable.addAll(disposable2)

            //compositeDisposable.dispose()
        }

        fun exec() {
            //consumeNoBackPressure()
            //consumeBackPressure()

            execComposite()
        }
    }
}