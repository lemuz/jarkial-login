import Swal from "sweetalert2";
import * as CryptoJS from 'crypto-js';
import { Constants } from "./Constants";

export abstract class UtilMethods extends Constants{

    zeroPad = (num: number, places: number) => String(num).padStart(places, '0');

    getCurrentDate(): string {
        const currentDate = new Date();
        const year = currentDate.getFullYear();
        const month = currentDate.getMonth()+1;
        const day = new Date(year, month, 0).getDate();
        const hour = currentDate.getHours();
        const minutes = currentDate.getMinutes();
        const seconds = currentDate.getSeconds();
        const currentDateStr = `${year}${this.zeroPad(month, 2)}${this.zeroPad(day, 2)}${this.zeroPad(hour, 2)}${this.zeroPad(minutes, 2)}${this.zeroPad(seconds, 2)}`;
        return currentDateStr;
    }

    formatCheckboxesToTrueOrFalse(checkbox: string): boolean{
        if(checkbox === '0'){
            return false;
        }else if(checkbox === '1'){
            return true;
        }else if(typeof checkbox === 'boolean'){
            return checkbox;
        }else{
            return false;
        }
    }

    changeBooleanToString(status: boolean): string{
        if(status === true){
            return '1';
        }else{
            return '0';
        }
    }

    showMessageEndProcess(tipo: string, response: any){
        if(response.codigo === this.CODE_HTTP_SUCCESS){
            Swal.fire('Éxito!!!', `Registro ${tipo}`, 'success');
        }else{
            Swal.fire('Advertencia...', response.message, 'warning');
        }
    }

    encrypt(value: string){
        return CryptoJS.AES.encrypt(value, this.KEY_ENCRYPT).toString();
    }

    decrypt(value: string){
        return CryptoJS.AES.decrypt(value, this.KEY_ENCRYPT).toString(CryptoJS.enc.Utf8);
    }
}