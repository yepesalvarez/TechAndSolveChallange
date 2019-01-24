import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';

@Injectable()
export class EjecucionService {
    public url: string;

    constructor(private _http: Http) {
        this.url = GLOBAL.url_ejecucion;
    }

    obtenerEjecuciones() {
        return this._http.get(this.url).map(res => res.json());
    }
}
