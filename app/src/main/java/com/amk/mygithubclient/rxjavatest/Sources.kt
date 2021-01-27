package ru.geekbrains.githubclient.rxjavatest

import android.os.Handler
import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Sources {
    fun exec() {
        Consumer(Producer()).exec()
    }

    class Producer {
        fun createJust() = Observable.just("1", "2", "3", "3")
        fun createJust2() = Observable.just("4", "5", "6")

        fun randomResultOperation() : Boolean {
            Thread.sleep(Random.nextLong(1000))
            return listOf(true, false, true)[Random.nextInt(2)]
        }

        fun completable() = Completable.create { emitter ->
            randomResultOperation().let {
                if (it) {
                    emitter.onComplete()
                } else {
                    emitter.onError(RuntimeException("Error"))

                }
            }
        }

        fun single() = Single.fromCallable {
            return@fromCallable "Some string value"
        }

        fun maybe() = Maybe.create<String> { emitter ->
            randomResultOperation().let {
                if (it) {
                    emitter.onSuccess("Success: $it")
                } else {
                    emitter.onComplete()
                }
            }
        }

        fun hotObservable() =
            Observable.interval(1, TimeUnit.SECONDS)
                .publish()

        fun hotObservable2() =
                Observable.interval(1, TimeUnit.SECONDS)
                        .replay()


        fun hotObservable3() =
                Observable.interval(1, TimeUnit.SECONDS)
                        .publish()
                        .refCount()


        fun hotObservable4() =
            Observable.interval(1, TimeUnit.SECONDS).cache()


        fun publishSubject() = PublishSubject.create<String>()

        fun create() = Observable.create<String> { emitter ->
            try {
                println(Thread.currentThread().name)

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
        fun execCompletable() {
            producer.completable()
                    .subscribe({
                        println("onComplete")
                    }, {
                        println("onError: ${it.message}")
                    })
        }

        fun execSingle() {
            producer.single().subscribe({s ->
                println("onNext: $s")
            }, {
                        println("onError: ${it.message}")
            }

            )
        }

        fun execMaybe() {
            producer.maybe()
                    .map { it + it }
                    .subscribe({ s ->
                        println("onSuccess: $s")
                    }, {
                        println("onError: ${it.message}")
                    }, {
                        println("onComplete")
                    })
        }

        fun execHotObservable() {
            val hotObservable = producer.hotObservable()

            hotObservable.subscribe({println(it)})

            hotObservable.connect()

            Handler(Looper.getMainLooper()).postDelayed({
                hotObservable.subscribe({println("delayed: $it")})

            }, 2000)
        }

        fun execHotObservarable2() {
            val hotObservable = producer.hotObservable4()
                    .subscribe({ s ->
                        println("onNext: $s")
                    }, {
                        println("onError: ${it.message}")
                    }, {
                        println("onComplete")
                    })
        }

        fun execPublishSubject() {
            val subject = producer.publishSubject()

            subject
                .subscribe({
                        println("onNext: $it")
                    }, {
                        println("onError: ${it.message}")
                    })

            subject.onNext("on Next from exec")

            subject.onNext("on Next bla bla")
        }

        fun execFromCreate() {
            producer.create()
//                .onErrorReturn {t ->
//                    println(t.message)
//                    return@onErrorReturn "Error 01"
//                }

//                .onErrorResumeNext { t: Throwable ->
//                    return@onErrorResumeNext Observable.just("Error Observable 02")
//                }

                .retry(5)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe({ s ->
                    println("onNext: $s")
                    println(Thread.currentThread().name)
                }, { e ->
                    println("onError: ${e.message}")
                }, {
                    println("onComplete")
                })

        }

        fun exec() {
            //execCompletable()
            //execSingle()
            //execMaybe()

            //execHotObservable()
            //execHotObservarable2()

            //execPublishSubject()

            execFromCreate()
        }
    }
}