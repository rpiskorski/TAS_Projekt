import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsComponent } from './products/products.component';
import { MainComponent } from './main/main.component';
import { ServicesComponent } from './services/services.component';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ServiceDetailComponent } from './service-detail/service-detail.component';
import { LoginFormComponent } from './login-form/login-form.component';
import { SignupFormComponent } from './signup-form/signup-form.component';
import { CategoriesComponent } from './categories/categories.component';
import { CategoryComponent } from './category/category.component';
import { ProductFormComponent } from './product-form/product-form.component';
import { ServiceFormComponent } from './service-form/service-form.component';
import { ProfileComponent } from './profile/profile.component';
import { AuthGuardService } from './auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: '/main', pathMatch: 'full'},
  { path: 'main', component: MainComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'categories/:id', component: CategoryComponent },
  { path: 'products', component: ProductsComponent },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: 'services', component: ServicesComponent },
  { path: 'services/:id', component: ServiceDetailComponent },
  { path: 'login', component: LoginFormComponent },
  { path: 'signup', component: SignupFormComponent },
  {
    path: 'product-form',
    component: ProductFormComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'service-form',
    component: ServiceFormComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuardService]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
