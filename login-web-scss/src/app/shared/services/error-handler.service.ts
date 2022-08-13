import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { MatDialog } from '@angular/material/dialog'
import { AuthService } from "./auth.service";
import { switchAll } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ErrorHandlerService {

    constructor(
        private router: Router,
        private loginService: LoginService,
        private authService: AuthService,
        private dialog: MatDialog){

        }

    makeGenericError(error: any) {
            if(error['status'] == 401) {
                this.dialog.closeAll;
                this.showAlertUnauthorized(error);
                this.signout();
            }else if (error['status'] == 403) {
                Swal.fire('Advertencia', 'No tiene permisos para esta accion', 'warning')
            }
    }

    signout() {
        throw new Error("Method not implemented.");
    }

    showAlertUnauthorized(error: any) {
        throw new Error("Method not implemented.");
    }
}