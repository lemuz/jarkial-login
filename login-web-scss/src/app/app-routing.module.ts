import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "./shared/guards/auth-guard.guard";

export const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: '/login', loadChildren: () => import('./security/security.module').then(m => m.SecurityModule)},
    {path: '/login', canActivate: [AuthGuard], loadChildren: () => import('./home/home.module').then(m => m.HomeModule)},
    {path: '**', redirectTo: '/app/home'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes, {useHash: true})],
    exports: [RouterModule]
})
export class AppRoutingModule {

}