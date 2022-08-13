import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class LoadingService{
    loading = new BehaviorSubject(false);
    message = 'Cargando...';
    requestCount = 0;

    constructor(){

    }

    showLoading(message?: string): void {
        this.requestCount++;
        this.message = (message !== undefined) ? message : 'Cargando...';
        this.loading.next(true);
    }

    hideLoading(): void {
        this.requestCount--;
        if (this.requestCount <= 0){
            this.requestCount = 0;
            this.loading.next(false);
        }
    }
}