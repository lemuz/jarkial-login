import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { UtilMethods } from "../utils/utilMethods";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard extends UtilMethods implements CanActivate{

    constructor(
        private router: Router,
        private authService: AuthService,
        private httpBaseService: BaseHttpService
    ){
        super();
    }
}