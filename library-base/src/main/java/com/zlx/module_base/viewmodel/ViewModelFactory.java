package com.zlx.module_base.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.zlx.module_base.base_ac.BaseModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;
    private final Application mApplication;
    private final BaseModel mRepository;

    public ViewModelFactory(Application mApplication, BaseModel baseModel) {
        this.mApplication = mApplication;
        this.mRepository = baseModel;
    }

    public static ViewModelFactory getInstance(Application application, BaseModel baseModel) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application, baseModel);
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (AndroidViewModel.class.isAssignableFrom(modelClass)) {
            //noinspection TryWithIdenticalCatches
            try {
                Constructor<?>[] constructors = modelClass.getConstructors();

//                Constructor<T> constructor = modelClass.getConstructor(Application.class, BaseModel.class);
                Constructor<T> constructor = (Constructor<T>) constructors[1];
                T t = constructor.newInstance(mApplication, mRepository);
                return t;
            }catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot create an instance of " + modelClass, e);
            }
        }
        return super.create(modelClass);
    }
}
