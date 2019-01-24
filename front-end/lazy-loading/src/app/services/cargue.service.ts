import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';

@Injectable()
export class CargueService {
    public url: string;

    constructor(private _http: Http) {
        this.url = GLOBAL.url_ejecucion;
    }

    cargarArchivo(cedula: number, input: File) {
        const formData: FormData = new FormData();
        formData.append('cedula', cedula.toString());
        formData.append('input', input, input.name);
        return this._http.post(this.url, formData)
        .map(res => res.json());
    }
}
