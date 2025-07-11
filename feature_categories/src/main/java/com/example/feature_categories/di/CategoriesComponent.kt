package com.example.feature_categories.di

import androidx.lifecycle.ViewModelProvider
import com.example.core.di.FeatureComponent
import dagger.Component

@CategoriesScope
@Component(
    modules = [CategoriesViewModelModule::class],
    dependencies = [FeatureComponent::class]
)
interface CategoriesComponent {
    
    fun viewModelFactory(): ViewModelProvider.Factory
    
    @Component.Factory
    interface Factory {
        fun create(
            featureComponent: FeatureComponent
        ): CategoriesComponent
    }
}