import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { LoadingService } from "../services/loading.service";
import { Constants } from "../utils/Constants";

@Injectable({
    providedIn: 'root'
})
export class InterceptorService extends Constants implements HttpInterceptor{
    
    constructor(
        private authService: AuthService,
        private loadingService: LoadingService,
        private router: Router,
        private errorHandlerService: ErrorHandlerService) {
            super();
        }

        intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
            
        }
}