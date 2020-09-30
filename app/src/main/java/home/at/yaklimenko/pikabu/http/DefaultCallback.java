package home.at.yaklimenko.pikabu.http;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class DefaultCallback<T> implements Callback<T> {
    private static final String TAG = DefaultCallback.class.getSimpleName();

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onResponse(new ResultEntity<T>(response.code(), response.body()));
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onResponse(new ResultEntity<T>(0, t.getMessage()));
    }

    protected abstract void onResponse(ResultEntity<T> result);

    static class ResultEntity<T> {
        private T entity;
        private int code;
        private String message;

        public ResultEntity(int code, T entity) {
            this.code = code;
            this.entity = entity;
        }

        public ResultEntity(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public boolean isSuccess() {
            return 300 > code && code >= 200;
        }

        public T getEntity() {
            return entity;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }

}
