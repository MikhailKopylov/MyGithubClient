package ru.geekbrains.githubclient.rxjavatest

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Creation {
    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        fun just(): Observable<String> {
            return Observable.just("1", "2", "3")
        }

        fun fromIterable(): Observable<String> {
            return Observable.fromIterable(listOf("1", "2", "3"))
        }

        fun interval() = Observable.interval(1, TimeUnit.SECONDS)

        fun timer() = Observable.timer(1, TimeUnit.SECONDS)
        fun range() = Observable.range(1, 10)

        fun randomResultOperation() : Boolean {
            Thread.sleep(Random.nextLong(500))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun fromCallable() = Observable.fromCallable {
            //Симуляция долгих вычислений
            val result = randomResultOperation()
            return@fromCallable result
        }

        fun create() = Observable.create<String> { emitter ->
            try {
                for (i in 0..10) {
                    randomResultOperation().let {
                        if (it) {
                            emitter.onNext("Success$i")
                        } else {
                            emitter.onError(RuntimeException("Error"))
                            return@create
                        }
                    }
                }
                emitter.onComplete()
            } catch (t: Throwable) {
                emitter.onError(RuntimeException("Error"))
            }
        }

    }

    class Consumer(val producer: Producer) {
        val stringObserver = object : Observer<String> {
            var disposable: Disposable? = null

            override fun onSubscribe(d: Disposable?) {
                disposable = d
                println("onSubscribe")
            }

            override fun onNext(s: String?) {
                println("onNext: $s")
            }

            override fun onComplete() {
                println("onComplete")
            }

            override fun onError(e: Throwable?) {
                println("onError: ${e?.message}")
            }
        }


        fun execJust() {
            producer.just().subscribe(stringObserver)
        }

        fun execLambda() {
            val disposable = producer.just()
                .subscribe({ s ->
                    println("onNext: $s")
                }, { e ->
                    println("onError: ${e.message}")
                }, {
                    println("onComplete")
                })
        }

        fun execFromIterable() {
            producer.fromIterable()
                .subscribe(stringObserver)
        }

        fun execInterval() {
            producer.interval()
                .subscribe {
                    println("onNext: $it")

                }
        }

        fun execTimer() {
            producer.timer()
                .subscribe {
                    println("onNext: $it")

                }
        }

        fun execRange() {
            producer.range()
                .subscribe {
                    println("onNext: $it")

                }
        }

        fun execFromCallable() {
            producer.fromCallable()
                .subscribe {
                    println("onNext: $it")

                }
        }

        fun execFromCreate() {
            producer.create()
                .subscribe({ s ->
                    println("onNext: $s")
                }, { e ->
                    println("onError: ${e.message}")
                }, {
                    println("onComplete")
                })
        }

        fun exec() {
            //execJust()
            //execLambda()
            //execFromIterable()
            //execInterval()

            //execTimer()
            //execRange()

            //execFromCallable()
            execFromCreate()
        }
    }
}