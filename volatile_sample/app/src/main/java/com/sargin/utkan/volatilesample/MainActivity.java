package com.sargin.utkan.volatilesample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    final static String TAG = "MainActivity";
    ProducerConsumer producerConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

//        ProducerConsumer producerConsumer = new ProducerConsumer();
        List<String> values = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8",
                "9", "10", "11", "12", "13");
//        Thread writerThread = new Thread(() -> Stream.of(values)
//                .forEach(producerConsumer::produce));
//        Thread readerThread = new Thread(() -> {
//            for (int i = 0; i > values.size(); i++) {
//                producerConsumer.consume();
//            }
//        });

//        writerThread.start();
//        readerThread.start();
//
//        try {
//            writerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        try {
//            readerThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {

                        if (producerConsumer == null) {
                            producerConsumer = new ProducerConsumer();
                        }
//                        producerConsumer.produce(new Date().toString());

                        Stream.of(values)
                                .forEach(producerConsumer::produce);

                        subscriber.onNext("Produce Finished.");
                    }
                })
//                .just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, s);
                    }
                });


        Observable<String> observable = Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {

                        if (producerConsumer == null) {
                            producerConsumer = new ProducerConsumer();
                        }
//                        producerConsumer.produce(new Date().toString());

                        subscriber.onNext(producerConsumer.consume())
                        ;

//                        subscriber.onNext("Produce Finished.");
                    }
                })
//                .just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.i(TAG, s);
//                    }
//                })
                ;

        Stream.of(values)
        .forEach(new Consumer<String>() {
            @Override
            public void accept(String value) {
                observable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, s);
                    }
                });
            }
        })
                ;
    }

    void subss() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
