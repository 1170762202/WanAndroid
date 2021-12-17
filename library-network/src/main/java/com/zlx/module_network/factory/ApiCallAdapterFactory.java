package com.zlx.module_network.factory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zlx.module_network.bean.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class ApiCallAdapterFactory extends CallAdapter.Factory {

    private ApiCallAdapterFactory() {
    }

    public static ApiCallAdapterFactory create() {
        return new ApiCallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull final Annotation[] annotations,
                                 @NonNull Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        if (rawType != ApiCall.class) {
            return null;
        }
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                    "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        }
        final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        return new MapiCallAdapter<>(responseType, annotations);
    }

    /**
     * Mapi的call
     *
     * @param <R> 数据类型
     * @author Administrator
     */
    public class MapiCallAdapter<R> implements CallAdapter<ApiResponse<R>, ApiCall<R>> {

        private final Type responseType;
        private final Annotation[] mAnnotations;

        MapiCallAdapter(Type responseType, Annotation[] annotations) {
            this.responseType = responseType;
            this.mAnnotations = annotations;
        }

        @NonNull
        @Override
        public Type responseType() {
            return new ParameterizedTypeImpl(new Type[]{responseType}, null, ApiResponse.class);
        }

        @NonNull
        @Override
        public ApiCall<R> adapt(@NonNull Call<ApiResponse<R>> call) {
            return new ApiCall<>(mAnnotations, call);
        }
    }
}

