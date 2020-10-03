package com.kavin.myutils.network;

public class NetworkTask {


    ///////////////////////////////////////////////////////////////////////////
    // ThreadUtils
    ///////////////////////////////////////////////////////////////////////////
    static <T> Task<T> doAsync(final Task<T> task) {
        ThreadUtils.getCachedPool().execute(task);
        return task;
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////

    public abstract static class Task<Result> extends ThreadUtils.SimpleTask<Result> {

        private Consumer<Result> mConsumer;

        public Task(final Consumer<Result> consumer) {
            mConsumer = consumer;
        }

        @Override
        public void onSuccess(Result result) {
            if (mConsumer != null) {
                mConsumer.accept(result);
            }
        }
    }

    public interface Consumer<T> {
        void accept(T t);
    }
}
